package ms3.main.dtos;

public class CreditRequestDTO {
    private float loanValue;
    private float monthlyQuota;
    private int term;
    private double seguroDesgravamen;
    private double seguroIncendio;
    private double comisionAdministracion;

    // Getters y Setters
    public float getLoanValue() { return loanValue; }
    public void setLoanValue(float loanValue) { this.loanValue = loanValue; }

    public float getMonthlyQuota() { return monthlyQuota; }
    public void setMonthlyQuota(float monthlyQuota) { this.monthlyQuota = monthlyQuota; }

    public int getTerm() { return term; }
    public void setTerm(int term) { this.term = term; }

    public double getSeguroDesgravamen() { return seguroDesgravamen; }
    public void setSeguroDesgravamen(double seguroDesgravamen) { this.seguroDesgravamen = seguroDesgravamen; }

    public double getSeguroIncendio() { return seguroIncendio; }
    public void setSeguroIncendio(double seguroIncendio) { this.seguroIncendio = seguroIncendio; }

    public double getComisionAdministracion() { return comisionAdministracion; }
    public void setComisionAdministracion(double comisionAdministracion) { this.comisionAdministracion = comisionAdministracion; }
}
