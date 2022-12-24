package raf.rentacar.reservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.rentacar.reservationservice.domain.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findCompanyByCompanyId(Long companyId);

    Optional<Reservation> findByVehicleId(Long vehicleId);

    List<Reservation> findAllByCompanyId(Long companyId);

    List<Reservation> findAllByVehicleId(Long vehicleId);

    List<Reservation> findAllByCanceled(boolean canceled);
}
