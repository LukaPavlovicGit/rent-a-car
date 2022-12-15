package raf.rentacar.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.rentacar.notificationservice.domain.Email;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    Optional<Email> findNotificationByType(String type);
}
