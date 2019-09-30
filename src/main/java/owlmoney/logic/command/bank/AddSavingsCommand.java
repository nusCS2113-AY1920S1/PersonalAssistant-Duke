package owlmoney.logic.command.bank;

import owlmoney.model.bank.Bank;
import owlmoney.model.bank.Saving;
import owlmoney.model.profile.Profile;

public class AddSavingsCommand {
    private final String name;
    private final double income;
    private final double amount;

    //this is a rough coding to provide the function temporarily
    //need to properly structure in future
    //might need to recode
    public AddSavingsCommand(String name, double income, double amount) {
        this.amount = amount;
        this.income = income;
        this.name = name;
    }

    public void execute(Profile profile) {
        Bank newSaving = new Saving(this.name, this.amount, this.income);
        //newSaving.getDescription();
        profile.addNewBank(newSaving);
    }
}
