package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class ListExpenditureCommand extends Command {
    private final String accName;
    private final int displayNum;

    public ListExpenditureCommand(String name, int displayNum) {
        this.accName = name;
        this.displayNum = displayNum;
    }

    public boolean execute(Profile profile, Ui ui) {
        profile.listExpenditure(accName, ui, displayNum);
        return this.isExit;
    }
}
