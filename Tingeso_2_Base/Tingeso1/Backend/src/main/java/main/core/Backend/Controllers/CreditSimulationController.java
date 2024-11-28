package main.core.Backend.Controllers;

import main.core.Backend.Services.CreditSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/creditsimulation")
public class CreditSimulationController {

    @Autowired
    CreditSimulationService creditSimulationService;

    @GetMapping("/calculateCredit")
    public ResponseEntity<Double> calculateCredit(
            @RequestParam("loan") float loan,
            @RequestParam("interest") float interest,
            @RequestParam("payment_quantity") int paymentQuantity) {

        double result = creditSimulationService.getCredit(loan, interest, paymentQuantity);
        return ResponseEntity.ok(result);
    }
}
