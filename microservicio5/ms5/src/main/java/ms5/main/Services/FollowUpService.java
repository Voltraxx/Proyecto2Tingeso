package ms5.main.Services;

import org.springframework.stereotype.Service;

@Service
public class FollowUpService {

    public String calculateNewStatus(String action) {
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

        return newStatus;
    }
}
