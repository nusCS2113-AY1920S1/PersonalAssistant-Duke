package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class DeleteDepositCommand extends Command {
    private final int expNumber;
    private final String from;

    public DeleteDepositCommand(String bankName, int number) {
        this.expNumber = number;
        this.from = bankName;
    }

    public void execute(Profile profile, Ui ui) {
        profile.deleteDeposit(this.expNumber, this.from, ui);
    }
}
