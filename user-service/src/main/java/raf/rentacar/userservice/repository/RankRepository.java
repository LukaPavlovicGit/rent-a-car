package raf.rentacar.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.rentacar.userservice.domain.Rank;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {
}
