package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class EditSavingsCommand extends Command {
    private final String name;
    private final String income;
    private final String amount;
    private final String newName;

    public EditSavingsCommand(String name, String income, String amount, String newName) {
        this.amount = amount;
        this.income = income;
        this.name = name;
        this.newName = newName;
    }

    @Override
    public boolean execute(Profile profile, Ui ui) {
        profile.editSavingsAccount(name, newName, amount, income, ui);
        return this.isExit;
    }
}
