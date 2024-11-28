/*package ms6.main.Services;

import java.util.HashMap;
import java.util.Map;

@Service
public class TotalCostService {

    @Autowired
    private RestTemplate restTemplate;

    // Función para el HU6 que es calcular los costos totales
    public Map<String, Object> calculateTotalCost(Long creditRequestId, double seguroDesgravamenPorcentaje, double seguroIncendio, double comisionAdministracionPorcentaje) {
        // Llamar al microservicio de Solicitudes para obtener los datos de la solicitud
        String url = "http://credit-requests-service/api/credit-requests/" + creditRequestId;
        CreditRequestDTO creditRequest = restTemplate.getForObject(url, CreditRequestDTO.class);

        // Realizar los cálculos basados en los datos obtenidos
        return performCostCalculation(creditRequest, seguroDesgravamenPorcentaje, seguroIncendio, comisionAdministracionPorcentaje);
    }

    private Map<String, Object> performCostCalculation(CreditRequestDTO creditRequest, double seguroDesgravamenPorcentaje, double seguroIncendio, double comisionAdministracionPorcentaje) {
        Map<String, Object> result = new HashMap<>();

        double loanAmount = creditRequest.getLoanValue();
        int termInMonths = creditRequest.getTerm() * 12;

        // Seguros
        double seguroDesgravamen = loanAmount * (seguroDesgravamenPorcentaje / 100.0);
        double totalSeguros = seguroDesgravamen + seguroIncendio;

        // Comisión por administración
        double comisionAdministracion = loanAmount * (comisionAdministracionPorcentaje / 100.0);

        // Cálculo del costo mensual
        double monthlyQuota = creditRequest.getMonthlyQuota();
        double costoMensual = monthlyQuota + totalSeguros;

        // Cálculo del costo total
        double costoTotal = (costoMensual * termInMonths) + comisionAdministracion;

        // Armar el resultado
        result.put("cuotaMensual", monthlyQuota);
        result.put("seguroDesgravamen", seguroDesgravamen);
        result.put("seguroIncendio", seguroIncendio);
        result.put("totalSeguros", totalSeguros);
        result.put("comisionAdministracion", comisionAdministracion);
        result.put("costoMensual", costoMensual);
        result.put("costoTotal", costoTotal);

        return result;
    }
}
*/