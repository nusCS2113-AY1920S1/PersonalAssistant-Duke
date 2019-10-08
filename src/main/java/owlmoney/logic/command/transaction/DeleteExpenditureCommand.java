package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class DeleteTransactionCommand extends Command {
    private final int expNumber;
    private final String from;

    public DeleteTransactionCommand(int number, String bankName) {
        this.expNumber = number;
        this.from = bankName;
    }

    public void execute(Profile profile, Ui ui) {
        profile.deleteTransaction(this.expNumber, this.from, ui);
    }
}
