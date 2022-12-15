package raf.rentacar.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.rentacar.userservice.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findUserByUsernameAndPassword(String username, String password);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}
