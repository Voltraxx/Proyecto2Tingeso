package ms3.main.dtos;

public class CreditRequestStatusUpdateDTO {

    private Long creditRequestId; // ID de la solicitud de crédito
    private String action;        // Acción solicitada (e.g., "approve", "reject", etc.)
    private String currentStatus; // Estado actual de la solicitud (opcional)

    // Constructor vacío
    public CreditRequestStatusUpdateDTO() {}

    // Constructor con parámetros
    public CreditRequestStatusUpdateDTO(Long creditRequestId, String action, String currentStatus) {
        this.creditRequestId = creditRequestId;
        this.action = action;
        this.currentStatus = currentStatus;
    }

    // Getters y Setters
    public Long getCreditRequestId() {
        return creditRequestId;
    }

    public void setCreditRequestId(Long creditRequestId) {
        this.creditRequestId = creditRequestId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
