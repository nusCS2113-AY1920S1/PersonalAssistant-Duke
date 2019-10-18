package owlmoney.logic.command.transaction;

import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.transaction.Expenditure;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * AddExpenditureCommand class which contains the execution function to add an expenditure transaction.
 */
public class AddExpenditureCommand extends Command {

    private final String accName;
    private final double amount;
    private final Date date;
    private final String description;
    private final String category;
    private final String type;

    /**
     * Constructor to create an instance of AddExpenditureCommand.
     *
     * @param name        Bank account name.
     * @param amount      Amount of the expenditure.
     * @param date        Date of the expenditure.
     * @param description Description of the expenditure.
     * @param category    Category of the expenditure.
     * @param type        Represents type of expenditure to be added.
     */
    public AddExpenditureCommand(String name, double amount, Date date, String description,
            String category, String type) {
        this.accName = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.type = type;
    }

    /**
     * Executes the function to add a new expenditure to the bank account.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If bank amount becomes negative after adding expenditure.
     * @throws CardException If the credit card name cannot be found.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, CardException {
        Transaction newExpenditure = new Expenditure(this.description, this.amount, this.date, this.category);
        profile.profileAddNewExpenditure(accName, newExpenditure, ui, this.type);
        return this.isExit;
    }
}
