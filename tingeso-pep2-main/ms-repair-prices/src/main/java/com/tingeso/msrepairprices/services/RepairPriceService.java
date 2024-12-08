package com.tingeso.msrepairprices.services;

import com.tingeso.msrepairprices.entities.RepairPrice;
import com.tingeso.msrepairprices.repositories.RepairPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairPriceService {
    @Autowired
    RepairPriceRepository repairPriceRepository;

    public ArrayList<RepairPrice> getRepairPrices() {return (ArrayList<RepairPrice>) repairPriceRepository.findAll();}

    public RepairPrice saveRepairPrice(RepairPrice repairPrice) {
        return repairPriceRepository.save(repairPrice);
    }

    public RepairPrice getRepairPriceByID(Long id) {
        return repairPriceRepository.findById(id).orElse(null);
    }

    public RepairPrice getRepairPriceByType(String type) {
        return repairPriceRepository.findByType(type);
    }

    public RepairPrice updateRepairPrice(RepairPrice repairPrice) {
        return repairPriceRepository.save(repairPrice);
    }

    public boolean deleteRepairPrice(Long id) throws Exception {
        try{
            repairPriceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
