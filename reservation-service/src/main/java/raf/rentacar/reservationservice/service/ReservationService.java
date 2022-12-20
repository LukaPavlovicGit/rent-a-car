package raf.rentacar.reservationservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import raf.rentacar.reservationservice.domain.Company;
import raf.rentacar.reservationservice.domain.Reservation;
import raf.rentacar.reservationservice.domain.Vehicle;
import raf.rentacar.reservationservice.dto.DiscountDto;
import raf.rentacar.reservationservice.dto.MessageTransferDto;
import raf.rentacar.reservationservice.dto.ReservationDto;
import raf.rentacar.reservationservice.exception.InvalidArguments;
import raf.rentacar.reservationservice.exception.NotFoundException;
import raf.rentacar.reservationservice.exception.OperationNotAllowed;
import raf.rentacar.reservationservice.exception.UnauthorizedOperation;
import raf.rentacar.reservationservice.mapper.Mapper;
import raf.rentacar.reservationservice.messageHelper.MessageHelper;
import raf.rentacar.reservationservice.repository.CompanyRepository;
import raf.rentacar.reservationservice.repository.ReservationRepository;
import raf.rentacar.reservationservice.repository.VehicleRepository;
import raf.rentacar.reservationservice.secutiry.tokenService.TokenService;
import io.github.resilience4j.retry.Retry;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

    private ReservationRepository reservationRepository;
    private VehicleRepository vehicleRepository;
    private CompanyRepository companyRepository;
    private TokenService tokenService;
    private Mapper mapper;
    private RestTemplate userServiceRestTemplate;
    private Retry userServiceRetry;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;

    public ReservationService(ReservationRepository reservationRepository, VehicleRepository vehicleRepository, CompanyRepository companyRepository, TokenService tokenService,
                              Mapper mapper, RestTemplate userServiceRestTemplate, Retry userServiceRetry, JmsTemplate jmsTemplate, MessageHelper messageHelper) {
        this.reservationRepository = reservationRepository;
        this.vehicleRepository = vehicleRepository;
        this.companyRepository = companyRepository;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.userServiceRestTemplate = userServiceRestTemplate;
        this.userServiceRetry = userServiceRetry;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
    }

    //admin
    public Page<ReservationDto> getReservations(Pageable pageable){
        return reservationRepository.findAll(pageable).map(mapper::reservationToReservationDto);
    }
    //manager
    public Page<ReservationDto> getReservationsByCompany(String authorization){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        Company company = companyRepository.findCompanyByManagerId(managerId)
                .orElseThrow(() -> new NotFoundException(String.format("The company whose manager has id: %d is not found!", managerId)));
        List<ReservationDto> reservationDtos = reservationRepository.findAllByCompanyId(company.getId()).stream().map(mapper::reservationToReservationDto).collect(Collectors.toList());
        return new PageImpl<>(reservationDtos);
    }
    //admin manager
    public ReservationDto getReservation(String authorization, Long id){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        String role = claims.get("role", String.class);
        Company company = null;
        Reservation reservation = null;
        if(role.equals("ROLE_MANAGER")){
            company = companyRepository.findCompanyByManagerId(managerId)
                    .orElseThrow(() -> new NotFoundException(String.format("The company whose manager has id: %d is not found!", managerId)));
            reservation = reservationRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format("The reservation with id: %d is not found!", id)));
            if(!company.getId().equals(reservation.getCompanyId()))
                throw new UnauthorizedOperation(String.format("The manager with id: %d is not authorized to fetch reservation with id: %d!", managerId, id));
        }
        return mapper.reservationToReservationDto(reservation);
    }
    //client
    public ReservationDto createReservation(String authorization, ReservationDto reservationDto){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long clientId = claims.get("id", Integer.class).longValue();
        String email = claims.get("email", String.class);
        String firstname = claims.get("firstname", String.class);
        String lastname = claims.get("lastname", String.class);

        Vehicle vehicle = vehicleRepository.findById(reservationDto.getVehicleId())
                .orElseThrow(() -> new NotFoundException(String.format("The vehicle with id: %d is not found!", reservationDto.getVehicleId())));

        if(!vehicle.isAvailable())
            throw new OperationNotAllowed(String.format("The car with id: %d is not available!", vehicle.getId()));

        Date now = Date.valueOf(LocalDate.now());
        Date start = reservationDto.getStart();
        Date end = reservationDto.getEnd();

        if(start.before(now) || start.after(end))
            throw new InvalidArguments("Invalid dates!");

        long startInMs = start.getTime();
        long endInMs = end.getTime();
        long timeDiff = Math.abs(endInMs - startInMs);
        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

        ResponseEntity<DiscountDto>
              discountDtoResponseEntity = Retry.decorateSupplier(
                      userServiceRetry,
                      () -> userServiceRestTemplate.exchange(
                              "/users/discount/"+clientId,
                              HttpMethod.GET,
                              null,
                              DiscountDto.class)
              ).get();

        DiscountDto discountDto = discountDtoResponseEntity.getBody();
        System.out.println("RESERVATION CREATION - discount value: "+discountDto.getDiscount());

        vehicle.setAvailable(false);
        vehicleRepository.save(vehicle);

        BigDecimal percentage = new BigDecimal(discountDto.getDiscount()).setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal hundred = new BigDecimal(100).setScale(2,RoundingMode.HALF_DOWN);
        Double discount = percentage.divide(hundred).doubleValue();
        Double oneDayPrice = vehicle.getPrice() - discount;
        Double totalPrice = daysDiff * oneDayPrice;

        Reservation reservation = mapper.reservationDtoToReservation(reservationDto);
        reservation.setUserId(clientId);
        reservation.setUserMail(email);
        reservation.setCompanyId(vehicle.getCompany().getId());
        reservation.setTotalPrice(totalPrice);
        reservationRepository.save(reservation);

        userServiceRestTemplate.exchange(
                "/users/increment-days/"+clientId,
                HttpMethod.POST,
                null,
                ResponseEntity.class,
                (int) daysDiff
        );
        MessageTransferDto messageTransferDto = new MessageTransferDto(
            firstname,
            lastname,
            vehicle.getName(),
            email,
            true
        );
        jmsTemplate.convertAndSend("reservation_queue", messageHelper.createTextMessage(messageTransferDto));

        return mapper.reservationToReservationDto(reservation);
    }

    //client
    public ReservationDto cancelReservation(String authorization, Long id){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long clientId = claims.get("id", Integer.class).longValue();
        String email = claims.get("email", String.class);
        String firstname = claims.get("firstname", String.class);
        String lastname = claims.get("lastname", String.class);

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("The reservation with id: %d is not found!", id)));

        Date now = Date.valueOf(LocalDate.now());
        Date start = reservation.getStart();
        Date end = reservation.getEnd();

        if(start.before(now))
            throw new OperationNotAllowed("The reservation is not possible to cancel!");

        Vehicle vehicle = vehicleRepository.findById(reservation.getVehicleId())
                .orElseThrow(() -> new NotFoundException(String.format("The vehicle with id: %d is not found!", reservation.getVehicleId())));

        reservation.setCanceled(true);
        vehicle.setAvailable(true);
        reservationRepository.save(reservation);
        vehicleRepository.save(vehicle);

        long startInMs = start.getTime();
        long endInMs = end.getTime();
        long timeDiff = Math.abs(endInMs - startInMs);
        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

        userServiceRestTemplate.exchange(
                "/users/decrement-days/"+clientId,
                HttpMethod.POST,
                null,
                ResponseEntity.class,
                (int) daysDiff
        );
        MessageTransferDto messageTransferDto = new MessageTransferDto(
                firstname,
                lastname,
                vehicle.getName(),
                email,
                1
        );
        jmsTemplate.convertAndSend("reservation_cancellation_queue", messageHelper.createTextMessage(messageTransferDto));

        return mapper.reservationToReservationDto(reservation);
    }

    //manager client
    public ReservationDto updateReservation(String authorization, Long id, ReservationDto reservationDto){
        return null;
    }
    //manager client
    public ReservationDto deleteReservation(String authorization, Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("The reservation with id: %d is not found!", id)));
        reservationRepository.deleteById(id);
        return mapper.reservationToReservationDto(reservation);
    }
}
