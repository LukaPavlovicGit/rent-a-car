package raf.rentacar.reservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.rentacar.reservationservice.domain.Company;
import raf.rentacar.reservationservice.domain.Vehicle;

import java.util.List;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAllByCompany(Company company);
}
