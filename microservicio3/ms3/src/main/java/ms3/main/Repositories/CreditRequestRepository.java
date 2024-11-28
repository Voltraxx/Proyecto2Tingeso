package ms3.main.Repositories;

import ms3.main.Entities.CreditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRequestRepository extends JpaRepository<CreditRequest, Long> {
}