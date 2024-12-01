package ms3.main.Services;

import ms3.main.Clients.TotalCostFeignClient;
import ms3.main.Entities.CreditRequest;
import ms3.main.Repositories.CreditRequestRepository;
import ms3.main.dtos.CreditRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class CreditRequestService {

    @Autowired
    CreditRequestRepository creditRequestRepository;

    @Autowired
    private TotalCostFeignClient totalCostFeignClient;

    // Función para crear la solicitud de crédito
    // Mapa para almacenar las condiciones de cada tipo de préstamo
    public final Map<String, LoanConditions> loanConditionsMap = Map.of(
            "Primera Vivienda", new LoanConditions(30, 3.5f, 5.0f, 0.8f),
            "Segunda Vivienda", new LoanConditions(20, 4.0f, 6.0f, 0.7f),
            "Propiedades Comerciales", new LoanConditions(25, 5.0f, 7.0f, 0.6f),
            "Remodelación", new LoanConditions(15, 4.5f, 6.0f, 0.5f)
    );

    // Método para obtener las condiciones de préstamo según el tipo al crear la solicitud
    public LoanConditions getLoanConditions(String loanType) {
        return loanConditionsMap.getOrDefault(loanType, null);
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

    // Métodos para CRUD de CreditRequest
    public ArrayList<CreditRequest> getCreditRequests() {
        return (ArrayList<CreditRequest>) creditRequestRepository.findAll();
    }

    public CreditRequest getCreditRequestById(Long id) {
        return creditRequestRepository.findById(id).orElse(null);
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

    public Map<String, Object> calculateTotalCost(CreditRequestDTO requestBody) {
        // Llamar al microservicio de TotalCost usando FeignClient
        return totalCostFeignClient.calculateTotalCost(requestBody);
    }
}