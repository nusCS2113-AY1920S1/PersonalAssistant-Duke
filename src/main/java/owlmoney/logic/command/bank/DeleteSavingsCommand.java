package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class DeleteSavingsCommand extends Command {
    private final String bankName;

    public DeleteSavingsCommand(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public boolean execute(Profile profile, Ui ui) {
        profile.deleteBank(this.bankName, ui);
        return this.isExit;
    }
}
