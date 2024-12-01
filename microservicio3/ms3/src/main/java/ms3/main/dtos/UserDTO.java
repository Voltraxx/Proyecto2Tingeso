package ms3.main.dtos;

import java.util.List;

public class UserDTO {
    private Long id;
    private Float income;
    private Float debts;
    private Float balance;
    private int age;
    private int accountAge;
    private List<Float> withdrawals;
    private List<Float> deposits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getIncome() {
        return income;
    }

    public void setIncome(Float income) {
        this.income = income;
    }

    public Float getDebts() {
        return debts;
    }

    public void setDebts(Float debts) {
        this.debts = debts;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAccountAge() {
        return accountAge;
    }

    public void setAccountAge(int accountAge) {
        this.accountAge = accountAge;
    }

    public List<Float> getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(List<Float> withdrawals) {
        this.withdrawals = withdrawals;
    }

    public List<Float> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Float> deposits) {
        this.deposits = deposits;
    }
}

