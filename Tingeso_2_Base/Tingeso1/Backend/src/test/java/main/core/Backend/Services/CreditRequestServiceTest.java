package main.core.Backend.Services;

import main.core.Backend.Entities.CreditRequest;
import main.core.Backend.Repositories.CreditRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import main.core.Backend.Entities.User;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class CreditRequestServiceTest {

    @Mock
    private CreditRequest creditRequest;

    @Mock
    private User user;

    @Mock
    private CreditRequestRepository creditRequestRepository;

    @Spy
    @InjectMocks
    private CreditRequestService creditRequestService;

    @Mock
    private MultipartFile document1;
    @Mock
    private MultipartFile document2;
    @Mock
    private MultipartFile document3;
    @Mock
    private MultipartFile document4;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(creditRequest.getUser()).thenReturn(user);
    }

    @Test
    void testGetCreditRequests() {
        List<CreditRequest> creditRequests = new ArrayList<>();
        creditRequests.add(new CreditRequest());
        creditRequests.add(new CreditRequest());

        when(creditRequestRepository.findAll()).thenReturn(creditRequests);

        List<CreditRequest> result = creditRequestService.getCreditRequests();

        assertThat(result).hasSize(2);
        verify(creditRequestRepository, times(1)).findAll();
    }

    @Test
    void testGetCreditRequestById_Found() {
        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setId(1L);

        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(creditRequest));

        CreditRequest result = creditRequestService.getCreditRequestById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(creditRequestRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCreditRequestById_NotFound() {
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.empty());

        CreditRequest result = creditRequestService.getCreditRequestById(1L);

        assertThat(result).isNull();
        verify(creditRequestRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveCreditRequest_Valid() {
        CreditRequest creditRequest = new CreditRequest();

        // Simulamos la validación y el cálculo sin mockear, ya que estamos usando @Spy en creditRequestService
        doNothing().when(creditRequestService).validateCreditRequest(creditRequest);
        doNothing().when(creditRequestService).calculateLoanValue(creditRequest);

        when(creditRequestRepository.save(creditRequest)).thenReturn(creditRequest);

        CreditRequest result = creditRequestService.saveCreditRequest(creditRequest);

        assertThat(result).isNotNull();
        verify(creditRequestService, times(1)).validateCreditRequest(creditRequest);
        verify(creditRequestService, times(1)).calculateLoanValue(creditRequest);
        verify(creditRequestRepository, times(1)).save(creditRequest);
    }

    @Test
    void testSaveCreditRequest_Invalid() {
        CreditRequest creditRequest = new CreditRequest();

        // Simulamos una excepción en validateCreditRequest
        doThrow(new IllegalArgumentException("Invalid Credit Request"))
                .when(creditRequestService).validateCreditRequest(creditRequest);

        assertThatThrownBy(() -> creditRequestService.saveCreditRequest(creditRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid Credit Request");

        verify(creditRequestService, times(1)).validateCreditRequest(creditRequest);
        verify(creditRequestRepository, never()).save(any(CreditRequest.class));
    }

    @Test
    void testUpdateCreditRequest() {
        CreditRequest creditRequest = new CreditRequest();

        // Simulamos la validación y el cálculo sin mockear, ya que estamos usando @Spy en creditRequestService
        doNothing().when(creditRequestService).validateCreditRequest(creditRequest);
        doNothing().when(creditRequestService).calculateLoanValue(creditRequest);

        when(creditRequestRepository.save(creditRequest)).thenReturn(creditRequest);

        CreditRequest result = creditRequestService.updateCreditRequest(creditRequest);

        assertThat(result).isNotNull();
        verify(creditRequestService, times(1)).validateCreditRequest(creditRequest);
        verify(creditRequestService, times(1)).calculateLoanValue(creditRequest);
        verify(creditRequestRepository, times(1)).save(creditRequest);
    }

    @Test
    void testDeleteCreditRequest_Success() throws Exception {
        Long creditRequestId = 1L;

        doNothing().when(creditRequestRepository).deleteById(creditRequestId);

        boolean result = creditRequestService.deleteCreditRequest(creditRequestId);

        assertThat(result).isTrue();
        verify(creditRequestRepository, times(1)).deleteById(creditRequestId);
    }

    @Test
    void testDeleteCreditRequest_Failure() {
        Long creditRequestId = 1L;

        doThrow(new RuntimeException("Delete failed")).when(creditRequestRepository).deleteById(creditRequestId);

        assertThatThrownBy(() -> creditRequestService.deleteCreditRequest(creditRequestId))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Delete failed");

        verify(creditRequestRepository, times(1)).deleteById(creditRequestId);
    }

    // Test para `getLoanConditions`
    @Test
    void testGetLoanConditions_ValidType() {
        CreditRequestService.LoanConditions result = creditRequestService.getLoanConditions("Primera Vivienda");
        assertThat(result).isNotNull();
        assertThat(result.getMaxTerm()).isEqualTo(30);
        assertThat(result.getMinInterest()).isEqualTo(3.5f);
        assertThat(result.getMaxInterest()).isEqualTo(5.0f);
        assertThat(result.getMaxFunding()).isEqualTo(0.8f);
    }

    @Test
    void testGetLoanConditions_InvalidType() {
        CreditRequestService.LoanConditions result = creditRequestService.getLoanConditions("Inexistente");
        assertThat(result).isNull();
    }

    // Tests para `validateCreditRequest`
    @Test
    void testValidateCreditRequest_ValidRequest() {
        CreditRequest validRequest = new CreditRequest();
        validRequest.setType("Primera Vivienda");
        validRequest.setInterest(4.0f);
        validRequest.setTerm(25);

        // Ejecuta el método y asegura que no se lance ninguna excepción
        assertThatCode(() -> creditRequestService.validateCreditRequest(validRequest)).doesNotThrowAnyException();
    }

    @Test
    void testValidateCreditRequest_InvalidInterest() {
        CreditRequest invalidInterestRequest = new CreditRequest();
        invalidInterestRequest.setType("Primera Vivienda");
        invalidInterestRequest.setInterest(6.0f); // Fuera del rango permitido
        invalidInterestRequest.setTerm(25);

        assertThatThrownBy(() -> creditRequestService.validateCreditRequest(invalidInterestRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El interés debe estar entre 3.5% y 5.0%");
    }

    @Test
    void testValidateCreditRequest_InvalidTerm() {
        CreditRequest invalidTermRequest = new CreditRequest();
        invalidTermRequest.setType("Primera Vivienda");
        invalidTermRequest.setInterest(4.0f);
        invalidTermRequest.setTerm(35); // Excede el plazo máximo permitido

        assertThatThrownBy(() -> creditRequestService.validateCreditRequest(invalidTermRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El plazo máximo permitido es de 30 años para este tipo de préstamo");
    }

    @Test
    void testValidateCreditRequest_InvalidLoanType() {
        CreditRequest invalidTypeRequest = new CreditRequest();
        invalidTypeRequest.setType("Inexistente");
        invalidTypeRequest.setInterest(4.0f);
        invalidTypeRequest.setTerm(25);

        assertThatThrownBy(() -> creditRequestService.validateCreditRequest(invalidTypeRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Tipo de préstamo no válido");
    }

    // Test para `evaluateRule`
    @Test
    void testEvaluateRule_ValidRule() {
        when(creditRequest.getUser()).thenReturn(user);

        when(user.getIncome()).thenReturn(1000f);
        when(creditRequest.getMonthlyQuota()).thenReturn(300f);

        boolean result = creditRequestService.evaluateRule(creditRequest, "R1");
        assertThat(result).isTrue();
    }

    @Test
    void testEvaluateRule_InvalidRule() {
        assertThatThrownBy(() -> creditRequestService.evaluateRule(creditRequest, "R999"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Regla desconocida");
    }

    // Test para `evaluateQuotaIncomeRatio`
    @Test
    void testEvaluateQuotaIncomeRatio_True() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getIncome()).thenReturn(2000f);
        when(creditRequest.getMonthlyQuota()).thenReturn(600f);

        boolean result = creditRequestService.evaluateQuotaIncomeRatio(creditRequest);
        assertThat(result).isTrue();
    }

    @Test
    void testEvaluateQuotaIncomeRatio_False() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getIncome()).thenReturn(2000f);
        when(creditRequest.getMonthlyQuota()).thenReturn(800f);

        boolean result = creditRequestService.evaluateQuotaIncomeRatio(creditRequest);
        assertThat(result).isFalse();
    }

    // Test para `evaluateCreditHistory`
    @Test
    void testEvaluateCreditHistory() {
        boolean result = creditRequestService.evaluateCreditHistory(creditRequest);
        assertThat(result).isTrue();
    }

    // Test para `evaluateEmploymentStability`
    @Test
    void testEvaluateEmploymentStability() {
        boolean result = creditRequestService.evaluateEmploymentStability(creditRequest);
        assertThat(result).isTrue();
    }

    // Test para `evaluateDebtIncomeRatio`
    @Test
    void testEvaluateDebtIncomeRatio_True() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getDebts()).thenReturn(300f);
        when(user.getIncome()).thenReturn(2000f);
        when(creditRequest.getMonthlyQuota()).thenReturn(400f);

        boolean result = creditRequestService.evaluateDebtIncomeRatio(creditRequest);
        assertThat(result).isTrue();
    }

    @Test
    void testEvaluateDebtIncomeRatio_False() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getDebts()).thenReturn(1200f);
        when(user.getIncome()).thenReturn(2000f);
        when(creditRequest.getMonthlyQuota()).thenReturn(500f);

        boolean result = creditRequestService.evaluateDebtIncomeRatio(creditRequest);
        assertThat(result).isFalse();
    }

    // Test para `evaluateApplicantAge`
    @Test
    void testEvaluateApplicantAge_True() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getAge()).thenReturn(35);
        when(creditRequest.getTerm()).thenReturn(30);

        boolean result = creditRequestService.evaluateApplicantAge(creditRequest);
        assertThat(result).isTrue();
    }

    @Test
    void testEvaluateApplicantAge_False() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getAge()).thenReturn(50);
        when(creditRequest.getTerm()).thenReturn(25);

        boolean result = creditRequestService.evaluateApplicantAge(creditRequest);
        assertThat(result).isFalse();
    }

    // Test para `evaluateSavingsCapability`
    @Test
    void testEvaluateSavingsCapability() {
        boolean result = creditRequestService.evaluateSavingsCapability(creditRequest);
        assertThat(result).isTrue();
    }

    // Test para `uploadDocuments` con todos los documentos subidos correctamente
    @Test
    void testUploadDocuments_AllDocumentsUploaded() throws IOException {
        // Crear una instancia real de CreditRequest en lugar de un mock
        CreditRequest realCreditRequest = new CreditRequest();
        realCreditRequest.setType("Segunda Vivienda"); // Configurar tipo de préstamo que requiere 4 documentos

        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(realCreditRequest));
        when(creditRequestRepository.save(any(CreditRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(document1.getBytes()).thenReturn(new byte[]{1});
        when(document2.getBytes()).thenReturn(new byte[]{2});
        when(document3.getBytes()).thenReturn(new byte[]{3});
        when(document4.getBytes()).thenReturn(new byte[]{4});

        when(document1.getSize()).thenReturn(100L);
        when(document2.getSize()).thenReturn(200L);
        when(document3.getSize()).thenReturn(300L);
        when(document4.getSize()).thenReturn(400L);

        CreditRequest updatedRequest = creditRequestService.uploadDocuments(1L, document1, document2, document3, document4);

        assertThat(updatedRequest.getStatus()).isEqualTo("En revisión inicial");
    }

    // Test para `uploadDocuments` con un tipo de préstamo que requiere menos documentos
    @Test
    void testUploadDocuments_PartialDocumentsUploaded() throws IOException {
        CreditRequest realCreditRequest = new CreditRequest();
        realCreditRequest.setType("Primera Vivienda"); // Configurar tipo de préstamo que requiere 3 documentos

        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(realCreditRequest));
        when(creditRequestRepository.save(any(CreditRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(document1.getBytes()).thenReturn(new byte[]{1});
        when(document2.getBytes()).thenReturn(new byte[]{2});
        when(document3.getBytes()).thenReturn(new byte[]{3});

        when(document1.getSize()).thenReturn(100L);
        when(document2.getSize()).thenReturn(200L);
        when(document3.getSize()).thenReturn(300L);

        CreditRequest updatedRequest = creditRequestService.uploadDocuments(1L, document1, document2, document3, null);

        assertThat(updatedRequest.getStatus()).isEqualTo("En revisión inicial");
    }

    // Test para `uploadDocuments` cuando no se encuentran suficientes documentos
    @Test
    void testUploadDocuments_DocumentsMissing() throws IOException {
        CreditRequest realCreditRequest = new CreditRequest();
        realCreditRequest.setType("Primera Vivienda"); // Configurar tipo de préstamo que requiere 3 documentos

        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(realCreditRequest));
        when(creditRequestRepository.save(any(CreditRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(document1.getBytes()).thenReturn(new byte[]{1});
        when(document2.getBytes()).thenReturn(new byte[]{2});

        when(document1.getSize()).thenReturn(100L);
        when(document2.getSize()).thenReturn(200L);

        CreditRequest updatedRequest = creditRequestService.uploadDocuments(1L, document1, document2, null, null);

        assertThat(updatedRequest.getStatus()).isEqualTo("Pendiente de documentación");
    }

    // Test para `uploadDocuments` con CreditRequest no encontrada
    @Test
    void testUploadDocuments_CreditRequestNotFound() {
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> creditRequestService.uploadDocuments(1L, document1, document2, document3, document4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Solicitud de crédito no encontrada con el ID proporcionado.");
    }

    // Test para `calculateLoanValue` con cada tipo de préstamo
    @Test
    void testCalculateLoanValue_PrimeraVivienda() {
        when(creditRequest.getType()).thenReturn("Primera Vivienda");
        when(creditRequest.getPropertyValue()).thenReturn((int) 100000f);

        creditRequestService.calculateLoanValue(creditRequest);

        verify(creditRequest).setLoanValue(80000f);
    }

    @Test
    void testCalculateLoanValue_SegundaVivienda() {
        when(creditRequest.getType()).thenReturn("Segunda Vivienda");
        when(creditRequest.getPropertyValue()).thenReturn((int) 100000f);

        creditRequestService.calculateLoanValue(creditRequest);

        verify(creditRequest).setLoanValue(70000f);
    }

    @Test
    void testCalculateLoanValue_PropiedadesComerciales() {
        when(creditRequest.getType()).thenReturn("Propiedades Comerciales");
        when(creditRequest.getPropertyValue()).thenReturn((int) 100000f);

        creditRequestService.calculateLoanValue(creditRequest);

        verify(creditRequest).setLoanValue(60000f);
    }

    @Test
    void testCalculateLoanValue_Remodelacion() {
        when(creditRequest.getType()).thenReturn("Remodelacion");
        when(creditRequest.getPropertyValue()).thenReturn((int) 100000f);

        creditRequestService.calculateLoanValue(creditRequest);

        verify(creditRequest).setLoanValue(50000f);
    }

    // Test para `calculateLoanValue` con tipo de préstamo inválido
    @Test
    void testCalculateLoanValue_InvalidLoanType() {
        when(creditRequest.getType()).thenReturn("Invalido");

        assertThatThrownBy(() -> creditRequestService.calculateLoanValue(creditRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Tipo de préstamo no válido.");
    }

    // Test para `calculateTotalCost` con valores de préstamo y cuota mensual
    @Test
    public void testCalculateTotalCost_ValidValues() {
        // Configurar valores de prueba para CreditRequest
        when(creditRequest.getMonthlyQuota()).thenReturn(Float.valueOf(500.0f));
        when(creditRequest.getLoanValue()).thenReturn(Float.valueOf(100000.0f));
        when(creditRequest.getTerm()).thenReturn(15); // Plazo en años

        // Valores de porcentaje para seguro de desgravamen y comisión por administración
        double seguroDesgravamenPorcentaje = 0.3; // 0.3%
        double seguroIncendio = 200.0; // Monto fijo ingresado desde el frontend
        double comisionAdministracionPorcentaje = 1.0; // 1%

        // Llamar a la función de cálculo
        Map<String, Object> result = creditRequestService.calculateTotalCost(
            creditRequest, seguroDesgravamenPorcentaje, seguroIncendio, comisionAdministracionPorcentaje
        );

        // Realizar las aserciones
        assertThat(result).isNotNull();
        assertThat(result.get("cuotaMensual")).isEqualTo(500.0);

        // Calcular los valores esperados en base a los porcentajes
        double loanValue = 100000.0;
        double expectedSeguroDesgravamen = loanValue * (seguroDesgravamenPorcentaje / 100.0);
        double expectedComisionAdministracion = loanValue * (comisionAdministracionPorcentaje / 100.0);
        double totalSeguros = expectedSeguroDesgravamen + seguroIncendio;

        assertThat(result.get("seguroDesgravamen")).isEqualTo(expectedSeguroDesgravamen);
        assertThat(result.get("seguroIncendio")).isEqualTo(seguroIncendio);
        assertThat(result.get("totalSeguros")).isEqualTo(totalSeguros);
        assertThat(result.get("comisionAdministracion")).isEqualTo(expectedComisionAdministracion);

        int totalMonths = 15 * 12;
        double expectedCostoMensual = 500.0 + totalSeguros; // cuotaMensual + totalSeguros
        double expectedCostoTotal = (expectedCostoMensual * totalMonths) + expectedComisionAdministracion;

        assertThat(result.get("costoMensual")).isEqualTo(expectedCostoMensual);
        assertThat(result.get("costoTotal")).isEqualTo(expectedCostoTotal);
    }

    @Test
    public void testCalculateTotalCost_ZeroInsuranceAndCommission() {
        // Configurar valores de prueba para CreditRequest
        when(creditRequest.getMonthlyQuota()).thenReturn(Float.valueOf(400.0f));
        when(creditRequest.getLoanValue()).thenReturn(Float.valueOf(80000.0f));
        when(creditRequest.getTerm()).thenReturn(10); // Plazo en años

        // Porcentajes de seguro y comisión (0%)
        double seguroDesgravamenPorcentaje = 0.0;
        double seguroIncendio = 0.0;
        double comisionAdministracionPorcentaje = 0.0;

        // Llamar a la función de cálculo
        Map<String, Object> result = creditRequestService.calculateTotalCost(
            creditRequest, seguroDesgravamenPorcentaje, seguroIncendio, comisionAdministracionPorcentaje
        );

        // Realizar las aserciones
        assertThat(result).isNotNull();
        assertThat(result.get("cuotaMensual")).isEqualTo(400.0);
        assertThat(result.get("seguroDesgravamen")).isEqualTo(0.0);
        assertThat(result.get("seguroIncendio")).isEqualTo(0.0);
        assertThat(result.get("totalSeguros")).isEqualTo(0.0);
        assertThat(result.get("comisionAdministracion")).isEqualTo(0.0);

        int totalMonths = 10 * 12;
        double expectedCostoMensual = 400.0;
        double expectedCostoTotal = (expectedCostoMensual * totalMonths);

        assertThat(result.get("costoMensual")).isEqualTo(expectedCostoMensual);
        assertThat(result.get("costoTotal")).isEqualTo(expectedCostoTotal);
    }

    // Test for evaluateRule
    @Test
    public void testEvaluateRule_ValidRules() {
        when(creditRequestService.evaluateQuotaIncomeRatio(creditRequest)).thenReturn(true);
        assertTrue(creditRequestService.evaluateRule(creditRequest, "R1"));

        when(creditRequestService.evaluateCreditHistory(creditRequest)).thenReturn(true);
        assertTrue(creditRequestService.evaluateRule(creditRequest, "R2"));

        when(creditRequestService.evaluateEmploymentStability(creditRequest)).thenReturn(true);
        assertTrue(creditRequestService.evaluateRule(creditRequest, "R3"));
    }

    @Test
    public void testEvaluateRule_UnknownRule() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> creditRequestService.evaluateRule(creditRequest, "R999")
        );
        assertEquals("Regla desconocida: R999", exception.getMessage());
    }

    // Test for evaluateMinimumBalance
    @Test
    public void testEvaluateMinimumBalance() {
        when(creditRequest.getLoanValue()).thenReturn(100000f);

        // Case where balance is exactly 10% of loan value
        when(user.getBalance()).thenReturn(10000f);
        assertTrue(creditRequestService.evaluateMinimumBalance(creditRequest));

        // Case where balance is below 10% of loan value
        when(user.getBalance()).thenReturn(9000f);
        assertFalse(creditRequestService.evaluateMinimumBalance(creditRequest));
    }

    // Test for evaluateBalanceAccountAge
    @Test
    public void testEvaluateBalanceAccountAge() {
        when(creditRequest.getLoanValue()).thenReturn(100000f);

        // Case where account age is less than 2 years, balance is 20% of loan
        when(user.getAccount_age()).thenReturn(1);
        when(user.getBalance()).thenReturn(20000f);
        assertTrue(creditRequestService.evaluateBalanceAccountAge(creditRequest));

        // Case where account age is 2 years or more, balance is 10% of loan
        when(user.getAccount_age()).thenReturn(2);
        when(user.getBalance()).thenReturn(10000f);
        assertTrue(creditRequestService.evaluateBalanceAccountAge(creditRequest));

        // Case where balance is insufficient
        when(user.getBalance()).thenReturn(5000f);
        assertFalse(creditRequestService.evaluateBalanceAccountAge(creditRequest));
    }

    // Test for evaluateRecentWithdrawal
    @Test
    public void testEvaluateRecentWithdrawal() {
        when(user.getBalance()).thenReturn(10000f);

        // Case where no withdrawals exceed 30% of balance
        when(user.getWithdrawals()).thenReturn(Arrays.asList(1000f, 2000f, 2500f, 1000f, 500f, 1500f));
        assertTrue(creditRequestService.evaluateRecentWithdrawal(creditRequest));

        // Case where one withdrawal exceeds 30% of balance
        when(user.getWithdrawals()).thenReturn(Arrays.asList(1000f, 4000f, 2500f, 1000f, 500f, 1500f));
        assertFalse(creditRequestService.evaluateRecentWithdrawal(creditRequest));
    }

    // Test for evaluateSavingHistory
    @Test
    public void testEvaluateSavingHistory() {
        when(user.getBalance()).thenReturn(10000f);

        // Case with withdrawals below 50% of balance
        when(user.getWithdrawals()).thenReturn(Arrays.asList(3000f, 2000f, 1500f));
        assertTrue(creditRequestService.evaluateSavingHistory(creditRequest));

        // Case where one withdrawal is above 50% of balance
        when(user.getWithdrawals()).thenReturn(Arrays.asList(6000f, 2000f, 1500f));
        assertFalse(creditRequestService.evaluateSavingHistory(creditRequest));
    }

    // Test for evaluatePeriodicDeposit
    @Test
    public void testEvaluatePeriodicDeposit() {
        when(user.getIncome()).thenReturn(20000f);

        // Case where total deposits meet minimum requirement and no 3 consecutive zero months
        when(user.getDeposits()).thenReturn(Arrays.asList(1500f, 1000f, 2000f, 500f, 700f, 500f, 1500f));
        assertTrue(creditRequestService.evaluatePeriodicDeposit(creditRequest));

        // Case where deposits do not meet the minimum sum requirement
        when(user.getDeposits()).thenReturn(Arrays.asList(300f, 400f, 200f));
        assertFalse(creditRequestService.evaluatePeriodicDeposit(creditRequest));

        // Case where there are 3 consecutive months with zero deposits
        when(user.getDeposits()).thenReturn(Arrays.asList(1500f, 0f, 0f, 0f, 500f));
        assertFalse(creditRequestService.evaluatePeriodicDeposit(creditRequest));
    }

    // Test for getDocumentByNumber
    @Test
    public void testGetDocumentByNumber_ValidDocument1() {
        byte[] document1 = new byte[]{1, 2, 3};
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(creditRequest));
        when(creditRequest.getDocument1()).thenReturn(document1);

        byte[] result = creditRequestService.getDocumentByNumber(1L, 1);
        assertArrayEquals(document1, result);
    }

    @Test
    public void testGetDocumentByNumber_ValidDocument2() {
        byte[] document2 = new byte[]{4, 5, 6};
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(creditRequest));
        when(creditRequest.getDocument2()).thenReturn(document2);

        byte[] result = creditRequestService.getDocumentByNumber(1L, 2);
        assertArrayEquals(document2, result);
    }

    @Test
    public void testGetDocumentByNumber_InvalidDocumentNumber() {
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(creditRequest));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            creditRequestService.getDocumentByNumber(1L, 5);
        });

        assertEquals("Número de documento inválido: 5", exception.getMessage());
    }

    @Test
    public void testGetDocumentByNumber_RequestNotFound() {
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            creditRequestService.getDocumentByNumber(1L, 1);
        });

        assertEquals("Solicitud de crédito no encontrada con el ID proporcionado.", exception.getMessage());
    }

    // Test for updateStatusBasedOnAction
    @Test
    public void testUpdateStatusBasedOnAction_Evaluate() {
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(creditRequest));

        creditRequestService.updateStatusBasedOnAction(1L, "evaluate");

        verify(creditRequest).setStatus("En Evaluación");
        verify(creditRequestRepository).save(creditRequest);
    }

    @Test
    public void testUpdateStatusBasedOnAction_Cancel() {
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(creditRequest));

        creditRequestService.updateStatusBasedOnAction(1L, "cancel");

        verify(creditRequest).setStatus("Cancelada por el Cliente");
        verify(creditRequestRepository).save(creditRequest);
    }

    @Test
    public void testUpdateStatusBasedOnAction_UnknownAction() {
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(creditRequest));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            creditRequestService.updateStatusBasedOnAction(1L, "unknownAction");
        });

        assertEquals("Acción no reconocida: unknownAction", exception.getMessage());
    }

    @Test
    public void testUpdateStatusBasedOnAction_RequestNotFound() {
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            creditRequestService.updateStatusBasedOnAction(1L, "evaluate");
        });

        assertEquals("Solicitud no encontrada", exception.getMessage());
    }

    @Test
    public void testUpdateStatusBasedOnAction_Approve() {
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(creditRequest));

        creditRequestService.updateStatusBasedOnAction(1L, "approve");

        verify(creditRequest).setStatus("Pre-Aprobada");
        verify(creditRequestRepository).save(creditRequest);
    }

    @Test
    public void testUpdateStatusBasedOnAction_FinalApproval() {
        when(creditRequestRepository.findById(1L)).thenReturn(Optional.of(creditRequest));

        creditRequestService.updateStatusBasedOnAction(1L, "finalApproval");

        verify(creditRequest).setStatus("Aprobada");
        verify(creditRequestRepository).save(creditRequest);
    }

    // Test for evaluateLoanValue
    @Test
    public void testEvaluateLoanValue() {
        assertTrue(creditRequestService.evaluateLoanValue(creditRequest));
    }

    // Test for evaluateApplicantAge
    @Test
    public void testEvaluateApplicantAge_UnderLimit() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getAge()).thenReturn(50);
        when(creditRequest.getTerm()).thenReturn(20);

        assertTrue(creditRequestService.evaluateApplicantAge(creditRequest));
    }

    @Test
    public void testEvaluateApplicantAge_OverLimit() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getAge()).thenReturn(60);
        when(creditRequest.getTerm()).thenReturn(15);

        assertFalse(creditRequestService.evaluateApplicantAge(creditRequest));
    }

    // Test for evaluateMinimumBalance
    @Test
    public void testEvaluateMinimumBalance_SufficientBalance() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getBalance()).thenReturn(10000.0f);
        when(creditRequest.getLoanValue()).thenReturn(90000.0f);

        assertTrue(creditRequestService.evaluateMinimumBalance(creditRequest));
    }

    @Test
    public void testEvaluateMinimumBalance_InsufficientBalance() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getBalance()).thenReturn(5000.0f);
        when(creditRequest.getLoanValue()).thenReturn(100000.0f);

        assertFalse(creditRequestService.evaluateMinimumBalance(creditRequest));
    }

    // Test for evaluateBalanceAccountAge
    @Test
    public void testEvaluateBalanceAccountAge_YoungAccountSufficientBalance() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getAccount_age()).thenReturn(1);
        when(user.getBalance()).thenReturn(20000.0f);
        when(creditRequest.getLoanValue()).thenReturn(80000.0f);

        assertTrue(creditRequestService.evaluateBalanceAccountAge(creditRequest));
    }

    @Test
    public void testEvaluateBalanceAccountAge_OldAccountSufficientBalance() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getAccount_age()).thenReturn(3);
        when(user.getBalance()).thenReturn(10000.0f);
        when(creditRequest.getLoanValue()).thenReturn(90000.0f);

        assertTrue(creditRequestService.evaluateBalanceAccountAge(creditRequest));
    }

    @Test
    public void testEvaluateBalanceAccountAge_InsufficientBalance() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getAccount_age()).thenReturn(1);
        when(user.getBalance()).thenReturn(5000.0f);
        when(creditRequest.getLoanValue()).thenReturn(50000.0f);

        assertFalse(creditRequestService.evaluateBalanceAccountAge(creditRequest));
    }

    // Test for evaluateRecentWithdrawal
    @Test
    public void testEvaluateRecentWithdrawal_NoLargeWithdrawals() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getBalance()).thenReturn(10000.0f);
        when(user.getWithdrawals()).thenReturn(List.of(1000.0f, 2000.0f, 500.0f, 800.0f, 900.0f, 700.0f));

        assertTrue(creditRequestService.evaluateRecentWithdrawal(creditRequest));
    }

    @Test
    public void testEvaluateRecentWithdrawal_WithLargeWithdrawal() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getBalance()).thenReturn(10000.0f);
        when(user.getWithdrawals()).thenReturn(List.of(1000.0f, 5000.0f, 2000.0f, 4000.0f, 800.0f, 900.0f));

        assertFalse(creditRequestService.evaluateRecentWithdrawal(creditRequest));
    }

    // Test for evaluateSavingHistory
    @Test
    public void testEvaluateSavingHistory_NoExcessiveWithdrawals() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getBalance()).thenReturn(10000.0f);
        when(user.getWithdrawals()).thenReturn(List.of(1000.0f, 2000.0f, 500.0f, 800.0f, 900.0f, 700.0f));

        assertTrue(creditRequestService.evaluateSavingHistory(creditRequest));
    }

    @Test
    public void testEvaluateSavingHistory_WithExcessiveWithdrawal() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getBalance()).thenReturn(10000.0f);
        when(user.getWithdrawals()).thenReturn(List.of(6000.0f, 3000.0f, 2000.0f, 1000.0f, 500.0f, 200.0f));

        assertFalse(creditRequestService.evaluateSavingHistory(creditRequest));
    }

    // Test for evaluatePeriodicDeposit
    @Test
    public void testEvaluatePeriodicDeposit_SufficientDeposits() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getDeposits()).thenReturn(List.of(500.0f, 700.0f, 800.0f, 500.0f, 600.0f));
        when(user.getIncome()).thenReturn(10000.0f);

        assertTrue(creditRequestService.evaluatePeriodicDeposit(creditRequest));
    }

    @Test
    public void testEvaluatePeriodicDeposit_WithThreeConsecutiveZeroDeposits() {
        when(creditRequest.getUser()).thenReturn(user);
        when(user.getDeposits()).thenReturn(List.of(500.0f, 0.0f, 0.0f, 0.0f, 600.0f));
        when(user.getIncome()).thenReturn(10000.0f);

        assertFalse(creditRequestService.evaluatePeriodicDeposit(creditRequest));
    }

}