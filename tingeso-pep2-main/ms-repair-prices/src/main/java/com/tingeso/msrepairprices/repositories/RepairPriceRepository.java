package com.tingeso.msrepairprices.repositories;

import com.tingeso.msrepairprices.entities.RepairPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairPriceRepository extends JpaRepository<RepairPrice,Long> {
    public RepairPrice findByType(String type);
}
