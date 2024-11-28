package main.core.Backend.Services;

import main.core.Backend.Entities.CreditRequest;
import main.core.Backend.Repositories.CreditRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class CreditRequestService {
    @Autowired
    CreditRequestRepository creditRequestRepository;

    // Función para crear la solicitud de crédito
    // Mapa para almacenar las condiciones de cada tipo de préstamo
    public final Map<String, LoanConditions> loanConditionsMap = Map.of(
            "Primera Vivienda", new LoanConditions(30, 3.5f, 5.0f, 0.8f),
            "Segunda Vivienda", new LoanConditions(20, 4.0f, 6.0f, 0.7f),
            "Propiedades Comerciales", new LoanConditions(25, 5.0f, 7.0f, 0.6f),
            "Remodelación", new LoanConditions(15, 4.5f, 6.0f, 0.5f)
    );

    // Métodos para CRUD de CreditRequest
    public ArrayList<CreditRequest> getCreditRequests() {
        return (ArrayList<CreditRequest>) creditRequestRepository.findAll();
    }

    public CreditRequest getCreditRequestById(Long id) {
        return creditRequestRepository.findById(id).orElse(null);
    }

    public CreditRequest saveCreditRequest(CreditRequest creditRequest) throws IllegalArgumentException {
        validateCreditRequest(creditRequest); // Validar antes de guardar
        calculateLoanValue(creditRequest);
        return creditRequestRepository.save(creditRequest);
    }

    public CreditRequest updateCreditRequest(CreditRequest creditRequest) throws IllegalArgumentException {
        validateCreditRequest(creditRequest); // Validar antes de actualizar
        calculateLoanValue(creditRequest);
        return creditRequestRepository.save(creditRequest);
    }

    public boolean deleteCreditRequest(Long id) throws Exception {
        try {
            creditRequestRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Método para obtener las condiciones de préstamo según el tipo al crear la solicitud
    public LoanConditions getLoanConditions(String loanType) {
        return loanConditionsMap.getOrDefault(loanType, null);
    }

    // Método de validación para la creación de solicitud de crédito
    public void validateCreditRequest(CreditRequest creditRequest) {
        LoanConditions conditions = loanConditionsMap.get(creditRequest.getType());

        if (conditions != null) {
            // Validación de interés
            if (creditRequest.getInterest() < conditions.getMinInterest() || creditRequest.getInterest() > conditions.getMaxInterest()) {
                throw new IllegalArgumentException("El interés debe estar entre " + conditions.getMinInterest() + "% y " + conditions.getMaxInterest() + "%.");
            }

            // Validación de plazo máximo
            if (creditRequest.getTerm() > conditions.getMaxTerm()) {
                throw new IllegalArgumentException("El plazo máximo permitido es de " + conditions.getMaxTerm() + " años para este tipo de préstamo.");
            }
        } else {
            throw new IllegalArgumentException("Tipo de préstamo no válido.");
        }
    }

    // Clase interna para representar las condiciones del préstamo
    public static class LoanConditions {
        private int maxTerm;
        private float minInterest;
        private float maxInterest;
        private float maxFunding;

        public LoanConditions(int maxTerm, float minInterest, float maxInterest, float maxFunding) {
            this.maxTerm = maxTerm;
            this.minInterest = minInterest;
            this.maxInterest = maxInterest;
            this.maxFunding = maxFunding;
        }

        public int getMaxTerm() { return maxTerm; }
        public float getMinInterest() { return minInterest; }
        public float getMaxInterest() { return maxInterest; }
        public float getMaxFunding() { return maxFunding; }
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateRule(CreditRequest creditRequest, String rule) {
        switch (rule) {
            case "R1": return evaluateQuotaIncomeRatio(creditRequest);
            case "R2": return evaluateCreditHistory(creditRequest);
            case "R3": return evaluateEmploymentStability(creditRequest);
            case "R5": return evaluateLoanValue(creditRequest);
            case "R4": return evaluateDebtIncomeRatio(creditRequest);
            case "R6": return evaluateApplicantAge(creditRequest);
            case "R7": return evaluateSavingsCapability(creditRequest);
            case "R71": return evaluateMinimumBalance(creditRequest);
            case "R72": return evaluateSavingHistory(creditRequest);
            case "R73": return evaluatePeriodicDeposit(creditRequest);
            case "R74": return evaluateBalanceAccountAge(creditRequest);
            case "R75": return evaluateRecentWithdrawal(creditRequest);
            default: throw new IllegalArgumentException("Regla desconocida: " + rule);
        }
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateQuotaIncomeRatio(CreditRequest creditRequest) {
        float quotaIncomeRatio = creditRequest.getMonthlyQuota() / creditRequest.getUser().getIncome();
        return quotaIncomeRatio <= 0.35;
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateCreditHistory(CreditRequest creditRequest) {
        return true;
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateEmploymentStability(CreditRequest creditRequest) {
        return true;
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateDebtIncomeRatio(CreditRequest creditRequest) {
        float debts = creditRequest.getUser().getDebts() + creditRequest.getMonthlyQuota();
        float income = creditRequest.getUser().getIncome();

        return debts < income*0.5;
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateLoanValue(CreditRequest creditRequest){
        return true;
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateApplicantAge(CreditRequest creditRequest) {
        int age = creditRequest.getUser().getAge();
        int limit = creditRequest.getTerm();

        return age + limit <= 70;
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateSavingsCapability(CreditRequest creditRequest) {
        return true;
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateMinimumBalance(CreditRequest creditRequest){
        float minimumBalance = creditRequest.getUser().getBalance() / creditRequest.getLoanValue();
        return minimumBalance >= 0.1;
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateBalanceAccountAge(CreditRequest creditRequest){
        int accountAge = creditRequest.getUser().getAccount_age();
        float balance = creditRequest.getUser().getBalance();
        float loanValue = creditRequest.getLoanValue();
        boolean x = false;
        
        if (accountAge < 2 && balance/loanValue >= 0.2){
            x = true;
        } else if (accountAge >= 2 && balance/loanValue >= 0.1){
            x = true;
        }

        return x;
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateRecentWithdrawal(CreditRequest creditRequest) {
        float balance = creditRequest.getUser().getBalance();
        List<Float> withdrawals = creditRequest.getUser().getWithdrawals(); // Suponiendo que esta lista contiene los últimos 12 meses de retiros

        // Tomamos los últimos 6 meses de retiros
        List<Float> recentWithdrawals = withdrawals.subList(Math.max(withdrawals.size() - 6, 0), withdrawals.size());

        // Verificamos si alguno de los retiros es superior al 30% del saldo
        for (Float withdrawal : recentWithdrawals) {
            if (withdrawal > balance * 0.3) {
                return false; // Marcar como negativo si hay un retiro mayor al 30% del saldo
            }
        }

        return true; // Marcar como positivo si no hay retiros mayores al 30% del saldo
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluateSavingHistory(CreditRequest creditRequest) {
        float balance = creditRequest.getUser().getBalance();
        List<Float> withdrawals = creditRequest.getUser().getWithdrawals();

        for (float withdrawal : withdrawals) {
            if (withdrawal > (balance * 0.5) || (balance - withdrawal) <= 0) {
                return false;
            }
        }
        return true;
    }

    // Función para el HU5 que es evaluar la solicitud
    public boolean evaluatePeriodicDeposit(CreditRequest creditRequest) {
        List<Float> deposits = creditRequest.getUser().getDeposits();
        float income = creditRequest.getUser().getIncome();
        float minimumDepositSum = income * 0.05f;

        // Verificar que la suma de los depósitos sea al menos el 5% del ingreso mensual
        float totalDeposits = deposits.stream().reduce(0.0f, Float::sum);
        if (totalDeposits < minimumDepositSum) {
            return false;
        }

        // Verificar que no haya 3 meses consecutivos sin depósitos
        int consecutiveZeroMonths = 0;
        for (Float deposit : deposits) {
            if (deposit == 0) {
                consecutiveZeroMonths++;
                if (consecutiveZeroMonths >= 3) {
                    return false;
                }
            } else {
                consecutiveZeroMonths = 0; 
            }
        }

        return true;
    }

    // Función para el HU3 que es crear o editar la solicitud de crédito
    public CreditRequest uploadDocuments(Long creditRequestId, MultipartFile document1, MultipartFile document2, MultipartFile document3, MultipartFile document4) throws IOException {
        Optional<CreditRequest> creditRequestOptional = creditRequestRepository.findById(creditRequestId);
        int quantity = 0;

        if (creditRequestOptional.isPresent()) {
            CreditRequest creditRequest = creditRequestOptional.get();

            if (document1 != null) {
                creditRequest.setDocument1(document1.getBytes());
                System.out.println("document1 guardado con tamaño: " + document1.getSize());
                quantity++;
            }
            if (document2 != null) {
                creditRequest.setDocument2(document2.getBytes());
                System.out.println("document2 guardado con tamaño: " + document2.getSize());
                quantity++;
            }
            if (document3 != null) {
                creditRequest.setDocument3(document3.getBytes());
                System.out.println("document3 guardado con tamaño: " + document3.getSize());
                quantity++;
            }
            if (document4 != null) {
                creditRequest.setDocument4(document4.getBytes());
                System.out.println("document4 guardado con tamaño: " + document4.getSize());
                quantity++;
            }

            if((quantity == 3 && (creditRequest.getType().equals("Primera Vivienda") || creditRequest.getType().equals("Remodelacion"))) ||
                    (quantity == 4 && (creditRequest.getType().equals("Segunda Vivienda") || creditRequest.getType().equals("Propiedades Comerciales")))){
                creditRequest.setStatus("En revisión inicial");
            } else {
                creditRequest.setStatus("Pendiente de documentación");
            }
            return creditRequestRepository.save(creditRequest);
        } else {
            throw new IllegalArgumentException("Solicitud de crédito no encontrada con el ID proporcionado.");
        }
    }

    // Función para el HU3 que es crear o editar la solicitud de crédito
    public void calculateLoanValue(CreditRequest creditRequest) {
        switch (creditRequest.getType()) {
            case "Primera Vivienda":
                creditRequest.setLoanValue((float) (creditRequest.getPropertyValue() * 0.8));
                break;
            case "Segunda Vivienda":
                creditRequest.setLoanValue((float) (creditRequest.getPropertyValue() * 0.7));
                break;
            case "Propiedades Comerciales":
                creditRequest.setLoanValue((float) (creditRequest.getPropertyValue() * 0.6));
                break;
            case "Remodelacion":
                creditRequest.setLoanValue((float) (creditRequest.getPropertyValue() * 0.5));
                break;
            default:
                throw new IllegalArgumentException("Tipo de préstamo no válido.");
        }
    }

    // Función para el HU6 que es calcular los costos totales
    public Map<String, Object> calculateTotalCost(CreditRequest creditRequest, double seguroDesgravamenPorcentaje, double seguroIncendio, double comisionAdministracionPorcentaje) {
        Map<String, Object> result = new HashMap<>();
        
        // Paso 1: Cuota Mensual del Préstamo (ya calculada previamente)
        double monthlyQuota = creditRequest.getMonthlyQuota();
        result.put("cuotaMensual", monthlyQuota);
        
        // Paso 2: Calcular Seguros
        double loanAmount = creditRequest.getLoanValue();
        
        // El seguro de desgravamen se calcula como porcentaje del valor del préstamo mensual
        double seguroDesgravamen = loanAmount * (seguroDesgravamenPorcentaje / 100.0); 
        // El seguro de incendio sigue siendo un valor fijo proporcionado por el frontend
        double totalSeguros = seguroDesgravamen + seguroIncendio;

        result.put("seguroDesgravamen", seguroDesgravamen);
        result.put("seguroIncendio", seguroIncendio);
        result.put("totalSeguros", totalSeguros);

        // Paso 3: Comisión por Administración como porcentaje del valor del préstamo
        double comisionAdministracion = loanAmount * (comisionAdministracionPorcentaje / 100.0);
        result.put("comisionAdministracion", comisionAdministracion);

        // Paso 4: Cálculo del Costo Total del Préstamo
        int totalMonths = creditRequest.getTerm() * 12;
        double costoMensual = monthlyQuota + totalSeguros;
        double costoTotal = (costoMensual * totalMonths) + comisionAdministracion;

        result.put("costoMensual", costoMensual);
        result.put("costoTotal", costoTotal);

        return result;
    }

    public byte[] getDocumentByNumber(Long creditRequestId, int docNumber) {
        Optional<CreditRequest> creditRequestOptional = creditRequestRepository.findById(creditRequestId);

        if (creditRequestOptional.isPresent()) {
            CreditRequest creditRequest = creditRequestOptional.get();
            switch (docNumber) {
                case 1:
                    return creditRequest.getDocument1();
                case 2:
                    return creditRequest.getDocument2();
                case 3:
                    return creditRequest.getDocument3();
                case 4:
                    return creditRequest.getDocument4();
                default:
                    throw new IllegalArgumentException("Número de documento inválido: " + docNumber);
            }
        } else {
            throw new IllegalArgumentException("Solicitud de crédito no encontrada con el ID proporcionado.");
        }
    }

    // Función para el HU4 y HU5 que es hacer seguimiento a la solicitud y evaluar
    public void updateStatusBasedOnAction(Long id, String action) {
        CreditRequest creditRequest = creditRequestRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        String newStatus;
        
        switch (action) {
            case "evaluate":
                newStatus = "En Evaluación"; // E3
                break;
            case "cancel":
                newStatus = "Cancelada por el Cliente"; // E8
                break;
            case "approve":
                newStatus = "Pre-Aprobada"; // E4
                break;
            case "reject":
                newStatus = "Rechazada"; // E7
                break;
            case "moveToFinalApproval":
                newStatus = "En Aprobación Final"; // E5
                break;
            case "finalApproval":
                newStatus = "Aprobada"; // E6
                break;
            case "disburseLoan":
                newStatus = "En Desembolso"; // E9
                break;
            default:
                throw new IllegalArgumentException("Acción no reconocida: " + action);
        }
        
        creditRequest.setStatus(newStatus);
        creditRequestRepository.save(creditRequest);
    }


}
