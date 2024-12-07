package ms5.main.Controllers;

import ms5.main.Services.FollowUpService;
import ms5.main.dtos.CreditRequestStatusUpdateDTO;
import ms5.main.dtos.StatusUpdateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/followup")
public class FollowUpController {
    @Autowired
    FollowUpService followUpService;

    @PostMapping("/{id}/status")
    public ResponseEntity<StatusUpdateResponseDTO> updateCreditRequestStatus(
            @PathVariable Long id,
            @RequestBody CreditRequestStatusUpdateDTO dto
    ) {
        try {
            // Llamar al servicio para calcular el nuevo estado basado en la acción
            String newStatus = followUpService.calculateNewStatus(dto.getAction());

            // Construir el DTO de respuesta
            StatusUpdateResponseDTO response = new StatusUpdateResponseDTO();
            response.setCreditRequestId(id);
            response.setNewStatus(newStatus);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
