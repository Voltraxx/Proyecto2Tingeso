package ms6.main.Services;

import ms6.main.dtos.CreditRequestDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
// CAMBIO EXITOSO

@Service
public class TotalCostService {

    public Map<String, Object> calculateTotalCost(CreditRequestDTO creditRequestDTO, double seguroDesgravamenPorcentaje, double seguroIncendio, double comisionAdministracionPorcentaje) {
        Map<String, Object> result = new HashMap<>();

        // Paso 1: Cuota Mensual del Préstamo (ya calculada previamente)
        double monthlyQuota = creditRequestDTO.getMonthlyQuota();
        result.put("cuotaMensual", monthlyQuota);

        // Paso 2: Calcular Seguros
        double loanAmount = creditRequestDTO.getLoanValue();

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
        int totalMonths = creditRequestDTO.getTerm() * 12;
        double costoMensual = monthlyQuota + totalSeguros;
        double costoTotal = (costoMensual * totalMonths) + comisionAdministracion;

        result.put("costoMensual", costoMensual);
        result.put("costoTotal", costoTotal);

        return result;
    }
}