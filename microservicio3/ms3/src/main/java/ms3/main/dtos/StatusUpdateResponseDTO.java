package ms3.main.dtos;

public class StatusUpdateResponseDTO {

    private Long creditRequestId; // ID de la solicitud de crédito
    private String newStatus;     // Nuevo estado calculado por FollowUp

    // Constructor vacío
    public StatusUpdateResponseDTO() {}

    // Constructor con parámetros
    public StatusUpdateResponseDTO(Long creditRequestId, String newStatus) {
        this.creditRequestId = creditRequestId;
        this.newStatus = newStatus;
    }

    // Getters y Setters
    public Long getCreditRequestId() {
        return creditRequestId;
    }

    public void setCreditRequestId(Long creditRequestId) {
        this.creditRequestId = creditRequestId;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
