package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Executes EditDepositCommand to edit a deposit transaction.
 */
public class EditDepositCommand extends Command {
    private final String accName;
    private final String amount;
    private final String date;
    private final String description;
    private final int index;

    /**
     * Creates an instance of EditDepositCommand.
     *
     * @param name        Bank account name.
     * @param amount      New deposit amount if any.
     * @param date        New date of deposit if any.
     * @param description New description of deposit if any.
     * @param index       Transaction number.
     */
    public EditDepositCommand(String name, String amount, String date, String description, int index) {
        this.accName = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.index = index;
    }

    /**
     * Executes the function to edit a deposit transaction.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If incorrect date format.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, TransactionException {
        profile.profileEditDeposit(index, accName, description, amount, date, ui);
        return this.isExit;
    }
}
