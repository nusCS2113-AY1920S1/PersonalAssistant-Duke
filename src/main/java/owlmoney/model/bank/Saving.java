package owlmoney.model.bank;

import owlmoney.model.expenditure.Expenditure;
import owlmoney.model.expenditure.ExpenditureList;

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
    public void getDescription() {
        super.getDescription();
        System.out.println(income);
        System.out.println(type);
    }

    @Override
    public void addInExpenditure(Expenditure exp) {
        myExpenditure.addToList(exp);
    }

    @Override
    public void listAllExpenditure() {
        myExpenditure.listExpenditure();
    }
}
