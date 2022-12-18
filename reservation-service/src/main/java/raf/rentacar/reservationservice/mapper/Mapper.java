package raf.rentacar.reservationservice.mapper;

import org.springframework.stereotype.Component;
import raf.rentacar.reservationservice.domain.Company;
import raf.rentacar.reservationservice.domain.Vehicle;
import raf.rentacar.reservationservice.dto.CompanyDto;
import raf.rentacar.reservationservice.dto.VehicleDto;

@Component
public class Mapper {

    public CompanyDto companyToCompanyDto(Company company){
        return new CompanyDto(company.getManagerId(),company.getName(),company.getCity(),company.getDescription());
    }
    public Company companyDtoToCompany(CompanyDto companyDto){
        return new Company(companyDto.getManagerId(),companyDto.getName(),companyDto.getCity(),companyDto.getDescription());
    }
    public VehicleDto vehicleToVehicleDto(Vehicle vehicle){
        return new VehicleDto(vehicle.getId(),vehicle.getType(),vehicle.getName(),vehicle.getPrice());
    }
    public Vehicle vehicleDtoToVehicle(VehicleDto vehicleDto){
        return new Vehicle(vehicleDto.getType(),vehicleDto.getName(),vehicleDto.getPrice());
    }
}
