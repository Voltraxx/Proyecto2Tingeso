package com.tingeso.msrepairs.services;

import com.tingeso.msrepairs.entities.RepairDetail;
import com.tingeso.msrepairs.repositories.RepairDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairDetailService {
    @Autowired
    RepairDetailRepository repairDetailRepository;

    public ArrayList<RepairDetail> getRepairDetails(){
        return (ArrayList<RepairDetail>) repairDetailRepository.findAll();
    }

    public RepairDetail saveRepairDetail(RepairDetail repairDetail) { return repairDetailRepository.save(repairDetail); }

    public RepairDetail getRepairDetailByID(Long id) {
        return repairDetailRepository.findById(id).orElse(null);
    }

    public List<RepairDetail> getRepairDetailsByIdRepair(Long idRepair) { return repairDetailRepository.findByIdRepair(idRepair); }

    public List<RepairDetail> getRepairDetailsByType(String type) {
        return repairDetailRepository.findByType(type);
    }

    public List<RepairDetail> getRepairDetailsByTypeAndCarType(String type,String carType) { return repairDetailRepository.findByTypeAndCarType(type,carType); }

    public List<RepairDetail> getRepairDetailsByTypeMonthAndYear(String type, String monthYear) {
        List<RepairDetail> repairDetailsType = getRepairDetailsByType(type);
        List<RepairDetail> filteredRepairDetails = new ArrayList<>();

        for (RepairDetail detail : repairDetailsType) {
            String[] dateParts = detail.getFechaIngreso().split("/");
            String detailMonthYear = dateParts[1] + "/" + dateParts[2];
            if (detailMonthYear.equals(monthYear)) {
                filteredRepairDetails.add(detail);
            }
        }
        return filteredRepairDetails;
    }

    public RepairDetail updateRepairDetail(RepairDetail repairDetail) { return repairDetailRepository.save(repairDetail); }

    public boolean deleteRepairDetail(Long id) throws Exception {
        try{
            repairDetailRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
