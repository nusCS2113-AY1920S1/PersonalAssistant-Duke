package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * EditRecurringExpenditureCommand class which contains the execution function
 * to edit a recurring expenditure transaction.
 */
public class EditRecurringExpenditureCommand extends Command {

    private final String accName;
    private final String amount;
    private final String description;
    private final String category;
    private final int index;
    private final String type;

    /**
     * Construction to create an instance of EditRecurringExpenditureCommand.
     *
     * @param name        Bank account name.
     * @param amount      New amount of recurring expenditure if any.
     * @param description New description of recurring expenditure if any.
     * @param category    New category of recurring expenditure if any.
     * @param index       Transaction number
     * @param type        The type of account to retrieve expenditure from.
     */
    public EditRecurringExpenditureCommand(String name, String amount,
            String description, String category, int index, String type) {
        this.accName = name;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.index = index;
        this.type = type;
    }

    /**
     * Executes the function to delete a recurring expenditure.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException        If bank account does not exist or is an investment account.
     * @throws TransactionException If there are 0 recurring expenditure or index is out of range.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, TransactionException {
        profile.profileEditRecurringExpenditure(accName, index, description, amount, category, ui, this.type);
        return this.isExit;
    }
}
