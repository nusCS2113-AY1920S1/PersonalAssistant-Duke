package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * DeleteRecurringExpenditureCommand class which contains the execution function to delete a recurring expenditure.
 */
public class DeleteRecurringExpenditureCommand extends Command {
    private final int expenditureNumber;
    private final String from;
    private final String type;

    /**
     * Constructor to create an instance of a DeleteRecurringExpenditureCommand.
     *
     * @param index       Recurring expenditure index number.
     * @param accountName Card or Bank account name.
     * @param type        The type of account to delete expenditure from.
     */
    public DeleteRecurringExpenditureCommand(int index, String accountName, String type) {
        this.expenditureNumber = index;
        this.from = accountName;
        this.type = type;
    }

    /**
     * Executes the function to delete a deposit transaction.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException        If bank account does not exist or is an investment account.
     * @throws TransactionException If invalid transaction.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, TransactionException {
        profile.profileDeleteRecurringExpenditure(this.from, this.expenditureNumber, ui, this.type);
        return this.isExit;
    }
}
