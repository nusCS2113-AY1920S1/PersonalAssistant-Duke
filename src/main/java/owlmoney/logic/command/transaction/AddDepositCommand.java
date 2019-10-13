package owlmoney.logic.command.transaction;

import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.Deposit;
import owlmoney.model.transaction.Transaction;
import owlmoney.ui.Ui;

/**
 * AddDepositCommand class which contains the execution function to add a new deposit transaction.
 */
public class AddDepositCommand extends Command {

    private final String accName;
    private final double amount;
    private final Date date;
    private final String description;
    private final String category = "deposit";

    /**
     * Constructor that creates an instance of AddDepositCommand.
     *
     * @param name        Bank account name.
     * @param amount      Amount deposited.
     * @param date        Date of deposit.
     * @param description Description of deposit.
     */
    public AddDepositCommand(String name, double amount, Date date, String description) {
        this.accName = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    /**
     * Executes the function to add a new deposit to the bank.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    public boolean execute(Profile profile, Ui ui) {
        Transaction newDeposit = new Deposit(this.description, this.amount, this.date, this.category);
        profile.addNewDeposit(accName, newDeposit, ui);
        return this.isExit;
    }
}
