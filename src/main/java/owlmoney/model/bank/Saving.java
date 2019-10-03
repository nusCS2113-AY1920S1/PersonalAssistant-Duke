package owlmoney.model.bank;

import owlmoney.model.expenditure.Expenditure;
import owlmoney.model.expenditure.ExpenditureList;
import owlmoney.ui.Ui;

public class Saving extends Bank{

    private String type;
    private double income;
    private ExpenditureList myExpenditure;
    private static final String SAVING = "saving";

    public Saving(String name, double currentAmount, double income) {
        super(name,currentAmount);
        this.income = income;
        type = SAVING;
        myExpenditure = new ExpenditureList();
    }

    @Override
    public String getDescription() {
        return super.getDescription() + "\nIncome: " + income + "\nType: " + type;
    }

    @Override
    public void addInExpenditure(Expenditure exp, Ui ui) {
        myExpenditure.addToList(exp, ui);
    }

    @Override
    public void listAllExpenditure(Ui ui) {
        myExpenditure.listExpenditure(ui);
    }

    @Override
    public void deleteExpend(int exId, Ui ui) {
        myExpenditure.deleteFromList(exId, ui);
    }
}
