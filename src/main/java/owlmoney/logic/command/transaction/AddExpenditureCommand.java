package owlmoney.logic.command.transaction;

import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
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

    /**
     * Constructor to create an instance of AddExpenditureCommand.
     *
     * @param name        Bank account name.
     * @param amount      Amount of the expenditure.
     * @param date        Date of the expenditure.
     * @param description Description of the expenditure.
     * @param category    Category of the expenditure.
     */
    public AddExpenditureCommand(String name, double amount, Date date, String description, String category) {
        this.accName = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    /**
     * Executes the function to add a new expenditure to the bank account.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException {
        Transaction newExpenditure = new Expenditure(this.description, this.amount, this.date, this.category);
        profile.addNewExpenditure(accName, newExpenditure, ui);
        return this.isExit;
    }
}
