package raf.rentacar.reservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.rentacar.reservationservice.domain.Company;

import java.util.Optional;
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findCompanyByManagerId(Long managerId);

}
