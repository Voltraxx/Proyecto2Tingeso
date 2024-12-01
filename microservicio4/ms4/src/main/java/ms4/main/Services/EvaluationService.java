package ms4.main.Services;

import ms4.main.dtos.EvaluationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {

    public boolean evaluateRule(EvaluationDTO evaluationDTO, String rule) {
        switch (rule) {
            case "R1": return evaluateQuotaIncomeRatio(evaluationDTO);
            case "R2": return evaluateCreditHistory(evaluationDTO);
            case "R3": return evaluateEmploymentStability(evaluationDTO);
            case "R4": return evaluateDebtIncomeRatio(evaluationDTO);
            case "R5": return evaluateLoanValue(evaluationDTO);
            case "R6": return evaluateApplicantAge(evaluationDTO);
            case "R7": return evaluateSavingsCapability(evaluationDTO);
            case "R71": return evaluateMinimumBalance(evaluationDTO);
            case "R72": return evaluateSavingHistory(evaluationDTO);
            case "R73": return evaluatePeriodicDeposit(evaluationDTO);
            case "R74": return evaluateBalanceAccountAge(evaluationDTO);
            case "R75": return evaluateRecentWithdrawal(evaluationDTO);
            default: throw new IllegalArgumentException("Regla desconocida: " + rule);
        }
    }

    private boolean evaluateQuotaIncomeRatio(EvaluationDTO dto) {
        float quotaIncomeRatio = dto.getMonthlyQuota() / dto.getUserIncome();
        return quotaIncomeRatio <= 0.35;
    }

    private boolean evaluateDebtIncomeRatio(EvaluationDTO dto) {
        float debts = dto.getUserDebts() + dto.getMonthlyQuota();
        float income = dto.getUserIncome();
        return debts < income * 0.5;
    }

    private boolean evaluateLoanValue(EvaluationDTO dto) {
        return true; // Implementar lógica según el caso.
    }

    private boolean evaluateApplicantAge(EvaluationDTO dto) {
        int age = dto.getUserAge();
        int term = dto.getTerm();
        return age + term <= 70;
    }

    private boolean evaluateMinimumBalance(EvaluationDTO dto) {
        float minimumBalance = dto.getUserBalance() / dto.getLoanValue();
        return minimumBalance >= 0.1;
    }

    private boolean evaluateBalanceAccountAge(EvaluationDTO dto) {
        int accountAge = dto.getUserAccountAge();
        float balance = dto.getUserBalance();
        float loanValue = dto.getLoanValue();
        return (accountAge < 2 && balance / loanValue >= 0.2) ||
                (accountAge >= 2 && balance / loanValue >= 0.1);
    }

    private boolean evaluateRecentWithdrawal(EvaluationDTO dto) {
        float balance = dto.getUserBalance();
        List<Float> withdrawals = dto.getUserWithdrawals();
        List<Float> recentWithdrawals = withdrawals.subList(Math.max(withdrawals.size() - 6, 0), withdrawals.size());
        return recentWithdrawals.stream().noneMatch(w -> w > balance * 0.3);
    }

    private boolean evaluateSavingHistory(EvaluationDTO dto) {
        float balance = dto.getUserBalance();
        List<Float> withdrawals = dto.getUserWithdrawals();
        for (float withdrawal : withdrawals) {
            if (withdrawal > (balance * 0.5) || (balance - withdrawal) <= 0) {
                return false;
            }
        }
        return true;
    }

    private boolean evaluatePeriodicDeposit(EvaluationDTO dto) {
        List<Float> deposits = dto.getUserDeposits();
        float income = dto.getUserIncome();
        float totalDeposits = deposits.stream().reduce(0.0f, Float::sum);
        if (totalDeposits < income * 0.05f) {
            return false;
        }
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

    private boolean evaluateEmploymentStability(EvaluationDTO dto) {
        return true; // Implementar lógica según el caso.
    }

    private boolean evaluateCreditHistory(EvaluationDTO dto) {
        return true; // Implementar lógica según el caso.
    }

    private boolean evaluateSavingsCapability(EvaluationDTO dto) {
        return true; // Implementar lógica según el caso.
    }
}
