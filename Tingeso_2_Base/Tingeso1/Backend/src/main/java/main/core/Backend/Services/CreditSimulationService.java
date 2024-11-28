package main.core.Backend.Services;

import org.springframework.stereotype.Service;

@Service
public class CreditSimulationService {
    public double getCredit(float loan, float interest, int payment_quantity) {
        if (interest == 0) {
            // Si el interés es 0, el pago mensual es simplemente el préstamo dividido entre los pagos
            return loan / payment_quantity;
        }

        double interest_data = Math.pow(1 + interest, payment_quantity);
        return loan * (interest * interest_data) / (interest_data - 1);
    }
}

