package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * ListExpenditureCommand class which contains the execution function to list expenditures.
 */
public class ListExpenditureCommand extends Command {
    private final String accName;
    private final int displayNum;

    /**
     * Constructor to create an instance of ListExpenditureCommand.
     *
     * @param name       Bank account name.
     * @param displayNum Number of expenditures to display.
     */
    public ListExpenditureCommand(String name, int displayNum) {
        this.accName = name;
        this.displayNum = displayNum;
    }

    /**
     * Executes the function to delete a deposit transaction.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    public boolean execute(Profile profile, Ui ui) {
        profile.listExpenditure(accName, ui, displayNum);
        return this.isExit;
    }
}
