package raf.rentacar.reservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.rentacar.reservationservice.domain.Company;
import raf.rentacar.reservationservice.domain.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByCompany(Company company);

    List<Review> findAllByUserId(Long userId);
}
