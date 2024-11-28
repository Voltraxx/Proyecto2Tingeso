package ms1.main.Repositories;

import ms1.main.Entities.CreditSimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditSimulationRepository extends JpaRepository<CreditSimulation, Long> {
}