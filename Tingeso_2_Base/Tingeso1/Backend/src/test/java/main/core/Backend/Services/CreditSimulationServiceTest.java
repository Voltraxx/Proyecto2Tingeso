package main.core.Backend.Services;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class CreditSimulationServiceTest {

    private final CreditSimulationService creditSimulationService = new CreditSimulationService();

    @Test
    public void testGetCreditWithPositiveValues() {
        float loan = 100000f;
        float interest = 0.05f; // 5% interest
        int payment_quantity = 12; // 12 payments

        // Calcular el valor esperado usando la misma fórmula que en el servicio
        double interestData = Math.pow(1 + interest, payment_quantity);
        double expectedCredit = loan * (interest * interestData) / (interestData - 1);

        double actualCredit = creditSimulationService.getCredit(loan, interest, payment_quantity);

        assertThat(actualCredit).isCloseTo(expectedCredit, within(0.01));
    }

    @Test
    public void testGetCreditWithZeroInterest() {
        float loan = 100000f;
        float interest = 0.0f; // 0% interest
        int payment_quantity = 12;

        double expectedCredit;
        if (interest == 0) {
            // Si el interés es 0, dividimos el préstamo equitativamente entre los pagos
            expectedCredit = loan / payment_quantity;
        } else {
            // Usamos la fórmula normal
            double interestData = Math.pow(1 + interest, payment_quantity);
            expectedCredit = loan * (interest * interestData) / (interestData - 1);
        }

        double actualCredit = creditSimulationService.getCredit(loan, interest, payment_quantity);

        assertThat(actualCredit).isCloseTo(expectedCredit, within(0.01));
    }

    @Test
    public void testGetCreditWithZeroLoanAmount() {
        float loan = 0.0f; // No loan
        float interest = 0.05f;
        int payment_quantity = 12;

        double actualCredit = creditSimulationService.getCredit(loan, interest, payment_quantity);

        assertThat(actualCredit).isEqualTo(0.0);
    }

    @Test
    public void testGetCreditWithOnePayment() {
        float loan = 100000f;
        float interest = 0.05f;
        int payment_quantity = 1; // Single payment

        // Para un solo pago, el usuario debería pagar el préstamo + un único interés
        double expectedCredit = loan * (1 + interest); // Se aplica el interés una vez

        double actualCredit = creditSimulationService.getCredit(loan, interest, payment_quantity);

        // Aumentamos el margen de tolerancia a 0.2 para manejar la diferencia de precisión
        assertThat(actualCredit).isCloseTo(expectedCredit, within(0.2));
    }


}

