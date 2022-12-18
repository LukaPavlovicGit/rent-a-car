package raf.rentacar.reservationservice.mapper;

import org.springframework.stereotype.Component;
import raf.rentacar.reservationservice.domain.Company;
import raf.rentacar.reservationservice.dto.CompanyDto;

@Component
public class Mapper {

    public CompanyDto companyToCompanyDto(Company company){
        return new CompanyDto(company.getManagerId(),company.getName(),company.getCity(),company.getDescription());
    }
    public Company companyDtoToCompany(CompanyDto companyDto){
        return new Company(companyDto.getManagerId(),companyDto.getName(),companyDto.getCity(),companyDto.getDescription());
    }
}
