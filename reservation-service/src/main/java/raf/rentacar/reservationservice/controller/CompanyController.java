package raf.rentacar.reservationservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.rentacar.reservationservice.dto.CompanyDto;
import raf.rentacar.reservationservice.secutiry.CheckSecurity;
import raf.rentacar.reservationservice.service.CompanyService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<Page<CompanyDto>> getCompanies(@RequestHeader("Authorization") String authorization,
                                                         @ApiIgnore Pageable pageable) {
        return new ResponseEntity<>(companyService.getCompanies(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<CompanyDto> getCompany(@RequestHeader("Authorization") String authorization,
                                                       @PathVariable("id") Long id) {
        return new ResponseEntity<>(companyService.getCompany(id), HttpStatus.OK);
    }
    @PostMapping
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<CompanyDto> createCompany(@RequestHeader("Authorization") String authorization,
                                                    @RequestBody CompanyDto companyDto) {
        return new ResponseEntity<>(companyService.createCompany(authorization, companyDto), HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<CompanyDto> updateCompany(@RequestHeader("Authorization") String authorization,
                                                    @RequestBody CompanyDto companyDto) {
        return new ResponseEntity<>(companyService.updateCompany(authorization, companyDto), HttpStatus.OK);
    }

    @DeleteMapping
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<CompanyDto> deleteCompany(@RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<>(companyService.deleteCompany(authorization), HttpStatus.OK);
    }
}
