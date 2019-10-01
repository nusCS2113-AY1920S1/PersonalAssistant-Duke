package owlmoney.logic.command.expenditure;

import owlmoney.logic.command.OwlMoneyCommand;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class ListExpenditureCommand extends OwlMoneyCommand {
    private final String accName;
    public ListExpenditureCommand(String name) {
        this.accName = name;
    }

    public void execute(Profile profile, Ui ui) {
        profile.listExpenditure(accName, ui);
    }
}
