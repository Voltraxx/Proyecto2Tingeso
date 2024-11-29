package ms6.main.Controllers;


import ms6.main.Services.TotalCostService;
import ms6.main.dtos.CreditRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/totalCost")
public class TotalCostController {

    @Autowired
    private TotalCostService totalCostService;

    @PostMapping("/calculate")
    public ResponseEntity<Map<String, Object>> calculateTotalCost(@RequestBody CreditRequestDTO creditRequestDTO) {
        try {
            // Extraer datos del DTO
            float loanValue = creditRequestDTO.getLoanValue();
            float monthlyQuota = creditRequestDTO.getMonthlyQuota();
            int term = creditRequestDTO.getTerm();

            // Realizar el cálculo (parámetros adicionales pueden ser enviados desde el request body)
            double seguroDesgravamen = 0.0; // Valor de ejemplo
            double seguroIncendio = 0.0;    // Valor de ejemplo
            double comisionAdministracion = 0.0; // Valor de ejemplo

            Map<String, Object> result = totalCostService.calculateTotalCost(
                    creditRequestDTO, seguroDesgravamen, seguroIncendio, comisionAdministracion);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
