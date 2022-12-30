package raf.rentacar.reservationservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.rentacar.reservationservice.domain.Company;
import raf.rentacar.reservationservice.domain.Vehicle;
import raf.rentacar.reservationservice.dto.AvailableVehiclesFilterDto;
import raf.rentacar.reservationservice.dto.VehicleDto;
import raf.rentacar.reservationservice.exception.NotFoundException;
import raf.rentacar.reservationservice.exception.UnauthorizedOperation;
import raf.rentacar.reservationservice.mapper.Mapper;
import raf.rentacar.reservationservice.repository.CompanyRepository;
import raf.rentacar.reservationservice.repository.ReservationRepository;
import raf.rentacar.reservationservice.repository.VehicleRepository;
import raf.rentacar.reservationservice.secutiry.tokenService.TokenService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private CompanyRepository companyRepository;
    private ReservationService reservationService;
    private TokenService tokenService;
    private Mapper mapper;
    private final ReservationRepository reservationRepository;

    public VehicleService(VehicleRepository vehicleRepository, CompanyRepository companyRepository, ReservationService reservationService,
                          TokenService tokenService, Mapper mapper, ReservationRepository reservationRepository) {
        this.vehicleRepository = vehicleRepository;
        this.companyRepository = companyRepository;
        this.reservationService = reservationService;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.reservationRepository = reservationRepository;
    }

    public Page<VehicleDto> getVehicles(Pageable pageable) {
        return vehicleRepository.findAll(pageable).map(mapper::vehicleToVehicleDto);
    }
    public Page<VehicleDto> getVehicles(String authorization) {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        Company company = companyRepository.findCompanyByManagerId(managerId)
                .orElseThrow(() -> new NotFoundException(String.format("The company whose manager has id: %d is not found!", managerId)));

        List<Vehicle> list = new ArrayList<>(company.getVehicles());
        return new PageImpl<>(list.stream().map(mapper::vehicleToVehicleDto).collect(Collectors.toList()));
    }
    public Page<VehicleDto> getVehiclesByCompany(String authorization, Pageable pageable) {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        Company company = companyRepository.findCompanyByManagerId(managerId)
                .orElseThrow(() -> new NotFoundException(String.format("The company whose manager has id: %d is not found!", managerId)));

        List<Vehicle> list = new ArrayList<>(company.getVehicles());
        return new PageImpl<>(list.stream().map(mapper::vehicleToVehicleDto).collect(Collectors.toList()));
//        List<VehicleDto> vehicleDtos = vehicleRepository.findAllByCompany(company).stream().map(mapper::vehicleToVehicleDto).collect(Collectors.toList());
//        return new PageImpl<>(vehicleDtos);
    }

    public VehicleDto getVehicle(String authorization, Long id) {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        String role = claims.get("role", String.class);
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("The vehicle with id: %d is not found!", id)));

        if(role.equals("ROLE_MANAGER")){
            if(vehicle.getCompany().getManagerId() != managerId)
                throw new UnauthorizedOperation(String.format("The manager with id: %d is not authorized to fetch car with id: %d!", managerId, id));
        }

        return mapper.vehicleToVehicleDto(vehicle);
    }
    public Page<VehicleDto> getAvailableVehicles(AvailableVehiclesFilterDto filterDto) {
        List<Vehicle> ans = new ArrayList<>();
        for(Vehicle vehicle : vehicleRepository.findAll()){
            if(filterDto.getCompanyName() != null && !filterDto.getCompanyName().equals("") && !vehicle.getCompany().getName().equals(filterDto.getCompanyName()))
                continue;
            if(filterDto.getCity() != null && !filterDto.getCity().equals("") && !vehicle.getCompany().getCity().equals(filterDto.getCity()))
                continue;
            if(!reservationService.isAvailableVehicle(vehicle.getId(), filterDto.getStart(), filterDto.getEnd()))
                continue;
            ans.add(vehicle);
        }
        return new PageImpl<>(ans.stream().map(mapper::vehicleToVehicleDto).collect(Collectors.toList()));
    }

    public VehicleDto createVehicle(String authorization, VehicleDto vehicleDto) {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        Company company = companyRepository.findCompanyByManagerId(managerId)
                .orElseThrow(() -> new NotFoundException(String.format("The company whose manager has id: %d is not found!", managerId)));
        Vehicle vehicle = mapper.vehicleDtoToVehicle(vehicleDto);
        vehicle.setCompany(company);
        vehicleRepository.save(vehicle);
        return mapper.vehicleToVehicleDto(vehicle);
    }

    public VehicleDto updateVehicle(String authorization, Long id, VehicleDto vehicleDto){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        Vehicle vehicle = vehicleRepository.findById(id).
                orElseThrow(() -> new NotFoundException(String.format("The vehicle with id: %d is not found!", id)));
        if(vehicle.getCompany().getManagerId() != managerId)
            throw new UnauthorizedOperation(String.format("The manager with id: %d is not authorized to fetch car with id: %d!", managerId, id));
        
        if(vehicleDto.getType() != null && !vehicleDto.getType().trim().isEmpty())
            vehicle.setType(vehicleDto.getType());
        if(vehicleDto.getName() != null && !vehicleDto.getName().trim().isEmpty())
            vehicle.setName(vehicleDto.getName());
        if(vehicleDto.getPrice() != null && vehicleDto.getPrice() > 0)
            vehicle.setPrice(vehicleDto.getPrice());

        return mapper.vehicleToVehicleDto(vehicle);
    }

    public VehicleDto deleteVehicle(String authorization, Long id){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        Vehicle vehicle = vehicleRepository.findById(id).
                orElseThrow(() -> new NotFoundException(String.format("The vehicle with id: %d is not found!", id)));
        if(vehicle.getCompany().getManagerId() != managerId)
            throw new UnauthorizedOperation(String.format("The manager with id: %d is not authorized to fetch car with id: %d!", managerId, id));
        vehicleRepository.delete(vehicle);
        return mapper.vehicleToVehicleDto(vehicle);
    }

}
