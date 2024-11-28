package main.core.Backend.Repositories;

import main.core.Backend.Entities.CreditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRequestRepository extends JpaRepository<CreditRequest, Long> {
}
