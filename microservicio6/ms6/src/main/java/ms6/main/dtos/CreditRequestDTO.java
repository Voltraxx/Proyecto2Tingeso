package ms6.main.dtos;

public class CreditRequestDTO {
    private float loanValue;
    private float monthlyQuota;
    private int term;

    // Getters y Setters
    public float getLoanValue() {
        return loanValue;
    }

    public void setLoanValue(float loanValue) {
        this.loanValue = loanValue;
    }

    public float getMonthlyQuota() {
        return monthlyQuota;
    }

    public void setMonthlyQuota(float monthlyQuota) {
        this.monthlyQuota = monthlyQuota;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }
}