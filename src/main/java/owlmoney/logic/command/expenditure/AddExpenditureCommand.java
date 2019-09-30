package owlmoney.logic.command.expenditure;

import owlmoney.model.expenditure.Expenditure;
import owlmoney.model.profile.Profile;

public class AddExpenditureCommand {

    private final String accName;
    private final double amount;
    private final String date;
    private final String description;
    private final String category;

    //this is a rough coding to provide the function temporarily
    //need to properly structure in future
    //might need to recode
    public AddExpenditureCommand(String name, double amount, String date, String description, String category) {
        this.accName = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public void execute(Profile profile) {
        Expenditure newExp = new Expenditure(this.description, this.amount, this.date, this.category);
        profile.addNewExpenditure(accName, newExp);
    }
}
