package com.tingeso.msrepairs.repositories;

import com.tingeso.msrepairs.entities.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<Repair,Long> {
    public List<Repair> findByIdCar(Long idCar);
}
