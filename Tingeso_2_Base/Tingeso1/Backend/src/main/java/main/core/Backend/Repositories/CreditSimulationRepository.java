package main.core.Backend.Repositories;

import main.core.Backend.Entities.CreditSimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditSimulationRepository extends JpaRepository<CreditSimulation, Long> {
}
