package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class DeleteExpenditureCommand extends Command {
    private final int expNumber;
    private final String from;

    public DeleteExpenditureCommand(int number, String bankName) {
        this.expNumber = number;
        this.from = bankName;
    }

    public boolean execute(Profile profile, Ui ui) {
        profile.deleteExpenditure(this.expNumber, this.from, ui);
        return this.isExit;
    }
}
