package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * DeleteExpenditureCommand class which contains the execution function to delete an expenditure.
 */
public class DeleteExpenditureCommand extends Command {
    private final int expNumber;
    private final String from;

    /**
     * Constructor to create an instance of a DeleteExpenditureCommand.
     *
     * @param index Transaction number.
     * @param bankName Bank account name.
     */
    public DeleteExpenditureCommand(int index, String bankName) {
        this.expNumber = index;
        this.from = bankName;
    }

    /**
     * Executes the function to delete a deposit transaction.
     *
     * @param profile Profile of the user.
     * @param ui Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    public boolean execute(Profile profile, Ui ui) {
        profile.deleteExpenditure(this.expNumber, this.from, ui);
        return this.isExit;
    }
}
