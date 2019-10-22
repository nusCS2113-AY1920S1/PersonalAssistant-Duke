package owlmoney.logic.command.transaction;

import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.Expenditure;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Executes AddRecurringExpenditureCommand and prints the result.
 */
public class AddRecurringExpenditureCommand extends Command {
    private final String accName;
    private final double amount;
    private final Date date;
    private final String description;
    private final String category;
    private final String type;

    /**
     * Creates an instance of AddRecurringExpenditureCommand.
     *
     * @param name        Bank account name.
     * @param amount      Amount of the recurring expenditure.
     * @param date        Date of the next expenditure.
     * @param description Description of the recurring expenditure.
     * @param category    Category of the recurring expenditure.
     * @param type        Represents type of expenditure to be added.
     */
    public AddRecurringExpenditureCommand(String name, double amount, Date date, String description,
            String category, String type) {
        this.accName = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.type = type;
    }

    /**
     * Executes the function to add a new recurring expenditure to the bank account.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If bank account does not exists or is an investment account.
     * @throws TransactionException If the recurring expenditure list is full.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, TransactionException {
        Transaction newExpenditure = new Expenditure(this.description, this.amount, this.date, this.category);
        profile.profileAddRecurringExpenditure(accName, newExpenditure, ui, this.type);
        return this.isExit;
    }
}
