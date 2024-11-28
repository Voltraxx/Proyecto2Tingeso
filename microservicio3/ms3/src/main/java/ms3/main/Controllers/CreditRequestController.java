package ms3.main.Controllers;

import ms3.main.Entities.CreditRequest;
import ms3.main.Services.CreditRequestService;
import ms3.main.Services.CreditRequestService.LoanConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/creditrequests")
public class CreditRequestController {

    @Autowired
    CreditRequestService creditRequestService;

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


}