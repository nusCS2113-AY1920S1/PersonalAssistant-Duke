package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class EditDepositCommand extends Command {
    private final String accName;
    private final String amount;
    private final String date;
    private final String description;
    private final int index;

    //working code but haven't parse date
    public EditDepositCommand(String name, String amount, String date, String description, int index) {
        this.accName = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.index = index;
    }

    public void execute(Profile profile, Ui ui) {
        profile.editDeposit(index, accName, description, amount, date, ui);
    }
}
