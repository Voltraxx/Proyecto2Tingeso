package com.tingeso.msrepairs.repositories;

import com.tingeso.msrepairs.entities.RepairDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairDetailRepository extends JpaRepository<RepairDetail,Long> {
    public List<RepairDetail> findByIdRepair(Long idRepair);
    public List<RepairDetail> findByType(String type);
    public List<RepairDetail> findByTypeAndCarType(String type,String carType);
}
