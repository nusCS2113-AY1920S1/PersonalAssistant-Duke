package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.Bank;
import owlmoney.model.bank.Saving;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class AddSavingsCommand extends Command {
    private final String name;
    private final double income;
    private final double amount;

    public AddSavingsCommand(String name, double income, double amount) {
        this.amount = amount;
        this.income = income;
        this.name = name;
    }

    @Override
    public boolean execute(Profile profile, Ui ui) {
        Bank newSaving = new Saving(this.name, this.amount, this.income);
        profile.addNewBank(newSaving, ui);
        return this.isExit;
    }
}
