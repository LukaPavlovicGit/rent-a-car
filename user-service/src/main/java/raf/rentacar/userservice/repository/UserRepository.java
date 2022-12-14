package raf.rentacar.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.rentacar.userservice.domain.Role;

public interface UserRepository extends JpaRepository<Role, Long>{
}
