package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * EditExpenditureCommand class which contains the execution function to edit an expenditure transaction.
 */
public class EditExpenditureCommand extends Command {

    private final String accName;
    private final String amount;
    private final String date;
    private final String description;
    private final String category;
    private final int index;

    /**
     * Construction to create an instance of EditExpenditureCommand.
     *
     * @param name        Bank account name.
     * @param amount      New amount of expenditure if any.
     * @param date        New date of expenditure if any.
     * @param description New description of expenditure if any.
     * @param category    New category of expenditure if any.
     * @param index       Transaction number
     */
    public EditExpenditureCommand(String name, String amount, String date, String description, String category,
            int index) {
        this.accName = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.index = index;
    }

    /**
     * Executes the function to delete a deposit transaction.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    public boolean execute(Profile profile, Ui ui) {
        profile.editExpenditure(index, accName, description, amount, date, category, ui);
        return this.isExit;
    }
}
