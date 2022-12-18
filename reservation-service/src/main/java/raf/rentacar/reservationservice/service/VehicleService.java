package raf.rentacar.reservationservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.rentacar.reservationservice.domain.Company;
import raf.rentacar.reservationservice.domain.Vehicle;
import raf.rentacar.reservationservice.dto.VehicleDto;
import raf.rentacar.reservationservice.exception.NotFoundException;
import raf.rentacar.reservationservice.exception.UnauthorizedOperation;
import raf.rentacar.reservationservice.mapper.Mapper;
import raf.rentacar.reservationservice.repository.CompanyRepository;
import raf.rentacar.reservationservice.repository.VehicleRepository;
import raf.rentacar.reservationservice.secutiry.tokenService.TokenService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private CompanyRepository companyRepository;
    private TokenService tokenService;
    private Mapper mapper;

    public VehicleService(VehicleRepository vehicleRepository, CompanyRepository companyRepository, TokenService tokenService, Mapper mapper) {
        this.vehicleRepository = vehicleRepository;
        this.companyRepository = companyRepository;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public Page<VehicleDto> getVehicles(Pageable pageable) {
        return vehicleRepository.findAll(pageable).map(mapper::vehicleToVehicleDto);
    }

    public Page<VehicleDto> getVehiclesByCompany(String authorization, Pageable pageable) {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        Company company = companyRepository.findCompanyByManagerId(managerId)
                .orElseThrow(() -> new NotFoundException(String.format("The company whose manager has id: %d is not found!", managerId)));

        List<VehicleDto> vehicleDtos = vehicleRepository.findAllByCompany(company).stream().map(mapper::vehicleToVehicleDto).collect(Collectors.toList());
        return new PageImpl<>(vehicleDtos);
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
        //String type, String name, Double price
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
