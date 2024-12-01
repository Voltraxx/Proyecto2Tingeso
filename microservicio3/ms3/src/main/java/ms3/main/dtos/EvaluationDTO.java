package ms3.main.dtos;

import java.util.List;

public class EvaluationDTO {
    private Long creditRequestId;
    private Float monthlyQuota;
    private Float loanValue;
    private int term;
    private Float userIncome;
    private Float userDebts;
    private Float userBalance;
    private int userAge;
    private int userAccountAge;
    private List<Float> userWithdrawals;
    private List<Float> userDeposits;

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
