package ms3.main.Controllers;

import ms3.main.Clients.EvaluationFeignClient;
import ms3.main.Clients.UserFeignClient;
import ms3.main.Entities.CreditRequest;
import ms3.main.Services.CreditRequestService;
import ms3.main.Services.CreditRequestService.LoanConditions;
import ms3.main.dtos.EvaluationDTO;
import ms3.main.dtos.CreditRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import ms3.main.dtos.UserDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/creditrequests")
public class CreditRequestController {

    @Autowired
    CreditRequestService creditRequestService;

    @Autowired
    UserFeignClient userFeignClient; // Inyectar UserFeignClient

    @Autowired
    EvaluationFeignClient evaluationFeignClient;

    // Listar todas las solicitudes de crédito
    @GetMapping("/")
    public ResponseEntity<List<CreditRequest>> listCreditRequests() {
        List<CreditRequest> creditRequests = creditRequestService.getCreditRequests();
        return ResponseEntity.ok(creditRequests);
    }

    // Obtener solicitud de crédito por ID
    @GetMapping("/{id}")
    public ResponseEntity<CreditRequest> getCreditRequestById(@PathVariable Long id) {
        CreditRequest creditRequest = creditRequestService.getCreditRequestById(id);
        return ResponseEntity.ok(creditRequest);
    }

    // Crear una nueva solicitud de crédito
    @PostMapping("/")
    public ResponseEntity<?> saveCreditRequest(@RequestBody CreditRequest creditRequest) {
        try {
            CreditRequest newCreditRequest = creditRequestService.saveCreditRequest(creditRequest);
            return ResponseEntity.ok(newCreditRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> updateCreditRequest(@RequestBody CreditRequest creditRequest) {
        try {
            CreditRequest creditRequestUpdated = creditRequestService.updateCreditRequest(creditRequest);
            return ResponseEntity.ok(creditRequestUpdated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Eliminar una solicitud de crédito por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditRequestById(@PathVariable Long id) throws Exception {
        creditRequestService.deleteCreditRequest(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener condiciones de préstamo según el tipo de préstamo
    @GetMapping("/loan-conditions")
    public ResponseEntity<LoanConditions> getLoanConditions(@RequestParam String loanType) {
        LoanConditions loanConditions = creditRequestService.getLoanConditions(loanType);
        if (loanConditions != null) {
            return ResponseEntity.ok(loanConditions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/documents")
    public ResponseEntity<String> uploadDocuments(
            @PathVariable Long id,
            @RequestParam(name = "document1", required = false) MultipartFile document1,
            @RequestParam(name = "document2", required = false) MultipartFile document2,
            @RequestParam(name = "document3", required = false) MultipartFile document3,
            @RequestParam(name = "document4", required = false) MultipartFile document4) {

        System.out.println("Documentos recibidos:");
        if (document1 != null) System.out.println("document1 size: " + document1.getSize());
        if (document2 != null) System.out.println("document2 size: " + document2.getSize());
        if (document3 != null) System.out.println("document3 size: " + document3.getSize());
        if (document4 != null) System.out.println("document4 size: " + document4.getSize());

        try {
            creditRequestService.uploadDocuments(id, document1, document2, document3, document4);
            return ResponseEntity.ok("Documentos subidos correctamente.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al guardar los documentos: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/document/{docNumber}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id, @PathVariable int docNumber) {

        try {
            byte[] document = creditRequestService.getDocumentByNumber(id, docNumber);

            if (document == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(document);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{id}/calculateTotalCost")
    public ResponseEntity<Map<String, Object>> calculateTotalCost(
            @PathVariable Long id,
            @RequestBody Map<String, Double> values) {

        // Obtener la solicitud de crédito por ID
        CreditRequest creditRequest = creditRequestService.getCreditRequestById(id);
        if (creditRequest == null) {
            return ResponseEntity.notFound().build();
        }

        // Crear el DTO para enviar al microservicio de TotalCost
        CreditRequestDTO requestBody = new CreditRequestDTO();
        requestBody.setLoanValue(creditRequest.getLoanValue());
        requestBody.setMonthlyQuota(creditRequest.getMonthlyQuota());
        requestBody.setTerm(creditRequest.getTerm());
        requestBody.setSeguroDesgravamen(values.getOrDefault("seguroDesgravamen", 0.0));
        requestBody.setSeguroIncendio(values.getOrDefault("seguroIncendio", 0.0));
        requestBody.setComisionAdministracion(values.getOrDefault("comisionAdministracion", 0.0));

        // Llamar al microservicio de TotalCost mediante FeignClient
        Map<String, Object> result = creditRequestService.calculateTotalCost(requestBody);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/evaluate/{rule}")
    public ResponseEntity<Boolean> evaluateCreditRequest(@PathVariable Long id, @PathVariable String rule) {
        CreditRequest creditRequest = creditRequestService.getCreditRequestById(id);
        if (creditRequest == null) {
            return ResponseEntity.notFound().build();
        }

        UserDTO userDTO = userFeignClient.getUserById(creditRequest.getUserId());
        EvaluationDTO dto = new EvaluationDTO();
        dto.setCreditRequestId(id);
        dto.setMonthlyQuota(creditRequest.getMonthlyQuota());
        dto.setLoanValue(creditRequest.getLoanValue());
        dto.setTerm(creditRequest.getTerm());
        dto.setUserIncome(userDTO.getIncome());
        dto.setUserDebts(userDTO.getDebts());
        dto.setUserBalance(userDTO.getBalance());
        dto.setUserAge(userDTO.getAge());
        dto.setUserAccountAge(userDTO.getAccountAge());
        dto.setUserWithdrawals(userDTO.getWithdrawals());
        dto.setUserDeposits(userDTO.getDeposits());

        return ResponseEntity.ok(evaluationFeignClient.evaluateRule(dto, rule));
    }


}