package owlmoney.logic.command.expenditure;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class ListExpenditureCommand extends Command {
    private final String accName;

    public ListExpenditureCommand(String name) {
        this.accName = name;
    }

    public void execute(Profile profile, Ui ui) {
        profile.listExpenditure(accName, ui);
    }
}
