package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class ListDepositCommand extends Command {
    private final String accName;
    private final int displayNum;

    public ListDepositCommand(String name, int displayNum) {
        this.accName = name;
        this.displayNum = displayNum;
    }

    public void execute(Profile profile, Ui ui) {
        profile.listDeposit(accName, ui, displayNum);
    }
}
