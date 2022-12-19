package raf.rentacar.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.rentacar.notificationservice.domain.Email;
import raf.rentacar.notificationservice.domain.SentEmail;

import java.util.List;


@Repository
public interface SentEmailRepository extends JpaRepository<SentEmail, Long> {
    List<SentEmail> findAllByDestinationEmail(String destinationEmail);
}
