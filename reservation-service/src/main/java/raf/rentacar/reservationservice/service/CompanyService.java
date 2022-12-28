package raf.rentacar.reservationservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.rentacar.reservationservice.domain.Company;
import raf.rentacar.reservationservice.domain.Vehicle;
import raf.rentacar.reservationservice.dto.CompanyDto;
import raf.rentacar.reservationservice.dto.VehicleDto;
import raf.rentacar.reservationservice.exception.NotFoundException;
import raf.rentacar.reservationservice.mapper.Mapper;
import raf.rentacar.reservationservice.repository.CompanyRepository;
import raf.rentacar.reservationservice.secutiry.tokenService.TokenService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyService {

    private CompanyRepository companyRepository;
    private TokenService tokenService;
    private Mapper mapper;

    public CompanyService(CompanyRepository companyRepository, TokenService tokenService, Mapper mapper) {
        this.companyRepository = companyRepository;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public Page<CompanyDto> getCompanies(Pageable pageable){
        return companyRepository.findAll(pageable).map(mapper::companyToCompanyDto);
    }

    public CompanyDto getCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Company with id: %d not found!", id)));
        return mapper.companyToCompanyDto(company);
    }

    public CompanyDto createCompany(String authorization, CompanyDto companyDto){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        Company company = mapper.companyDtoToCompany(companyDto);
        company.setManagerId(managerId);
        companyRepository.save(company);
        return mapper.companyToCompanyDto(company);
    }

    public CompanyDto updateCompany(String authorization, CompanyDto companyDto){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        Company company = companyRepository.findCompanyByManagerId(managerId)
                .orElseThrow(() -> new NotFoundException(String.format("The company whose manager has id: %d is not found!", managerId)));

        if(companyDto.getName() != null && !companyDto.getName().trim().isEmpty())
            company.setName(companyDto.getName());
        if(companyDto.getCity() != null && !companyDto.getCity().trim().isEmpty())
            company.setCity(companyDto.getCity());
        if(companyDto.getDescription() != null && !companyDto.getDescription().trim().isEmpty())
            company.setName(companyDto.getDescription());

        companyRepository.save(company);
        return mapper.companyToCompanyDto(company);
    }

    public CompanyDto deleteCompany(String authorization){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long managerId = claims.get("id", Integer.class).longValue();
        Company company = companyRepository.findCompanyByManagerId(managerId)
                .orElseThrow(() -> new NotFoundException(String.format("The company whose manager has id: %d is not found!", managerId)));
        companyRepository.delete(company);
        return mapper.companyToCompanyDto(company);
    }

}
