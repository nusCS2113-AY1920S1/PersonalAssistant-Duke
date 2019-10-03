package owlmoney.model.bank;

import owlmoney.model.expenditure.Expenditure;
import owlmoney.ui.Ui;

public abstract class Bank {
    String accountName;
    double currentAmount;

    public Bank(String name, double currentAmount) {
        this.accountName = name;
        this.currentAmount = currentAmount;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public double getCurrentAmount() {
        return this.currentAmount;
    }

    public String getDescription() {
        return "Account name: " + accountName + "\nCurrent Amount: " + currentAmount;
    }

    public abstract void addInExpenditure(Expenditure exp, Ui ui);

    public abstract void listAllExpenditure(Ui ui);

    public abstract void deleteExpend(int exNum, Ui ui);
}
