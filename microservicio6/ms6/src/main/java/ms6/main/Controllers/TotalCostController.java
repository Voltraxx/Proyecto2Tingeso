package ms6.main.Controllers;


import ms6.main.Services.TotalCostService;
import ms6.main.dtos.CreditRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            Map<String, Object> result = totalCostService.calculateTotalCost(
                    creditRequestDTO,
                    creditRequestDTO.getSeguroDesgravamen(),
                    creditRequestDTO.getSeguroIncendio(),
                    creditRequestDTO.getComisionAdministracion()
            );

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace(); // Loguear errores para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

}
