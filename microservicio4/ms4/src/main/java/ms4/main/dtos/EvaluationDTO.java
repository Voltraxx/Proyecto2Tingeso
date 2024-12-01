package ms4.main.dtos;

import java.util.List;

public class EvaluationDTO {
    private Long creditRequestId;  // ID de la solicitud de crédito
    private Float monthlyQuota;    // Cuota mensual del préstamo
    private Float loanValue;       // Valor del préstamo
    private int term;              // Plazo del préstamo en años
    private Float userIncome;      // Ingreso mensual del usuario
    private Float userDebts;       // Deudas del usuario
    private Float userBalance;     // Saldo del usuario
    private int userAge;           // Edad del usuario
    private int userAccountAge;    // Antigüedad de la cuenta del usuario
    private List<Float> userWithdrawals; // Lista de retiros del usuario
    private List<Float> userDeposits;    // Lista de depósitos del usuario

    // Getters y Setters
    public Long getCreditRequestId() {
        return creditRequestId;
    }

    public void setCreditRequestId(Long creditRequestId) {
        this.creditRequestId = creditRequestId;
    }

    public Float getMonthlyQuota() {
        return monthlyQuota;
    }

    public void setMonthlyQuota(Float monthlyQuota) {
        this.monthlyQuota = monthlyQuota;
    }

    public Float getLoanValue() {
        return loanValue;
    }

    public void setLoanValue(Float loanValue) {
        this.loanValue = loanValue;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Float getUserIncome() {
        return userIncome;
    }

    public void setUserIncome(Float userIncome) {
        this.userIncome = userIncome;
    }

    public Float getUserDebts() {
        return userDebts;
    }

    public void setUserDebts(Float userDebts) {
        this.userDebts = userDebts;
    }

    public Float getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Float userBalance) {
        this.userBalance = userBalance;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public int getUserAccountAge() {
        return userAccountAge;
    }

    public void setUserAccountAge(int userAccountAge) {
        this.userAccountAge = userAccountAge;
    }

    public List<Float> getUserWithdrawals() {
        return userWithdrawals;
    }

    public void setUserWithdrawals(List<Float> userWithdrawals) {
        this.userWithdrawals = userWithdrawals;
    }

    public List<Float> getUserDeposits() {
        return userDeposits;
    }

    public void setUserDeposits(List<Float> userDeposits) {
        this.userDeposits = userDeposits;
    }
}

