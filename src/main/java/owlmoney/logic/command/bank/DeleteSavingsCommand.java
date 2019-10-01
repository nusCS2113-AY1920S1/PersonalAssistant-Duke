package owlmoney.logic.command.bank;

import owlmoney.logic.command.OwlMoneyCommand;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class DeleteSavingsCommand extends OwlMoneyCommand {
    private final String bankName;

    public DeleteSavingsCommand (String bankName) {
        this.bankName = bankName;
    }

    public void execute(Profile profile, Ui ui) {
        profile.deleteBank(this.bankName, ui);
    }
}
