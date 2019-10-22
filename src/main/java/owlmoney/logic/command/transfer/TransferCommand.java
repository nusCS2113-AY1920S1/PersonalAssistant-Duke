package owlmoney.logic.command.transfer;

import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes TransferCommand to transfer fund.
 */
public class TransferCommand extends Command {
    private final String from;
    private final String to;
    private final double amount;
    private final Date date;

    /**
     * Creates an instance of TransferCommand.
     *
     * @param from   The account name for transferring the fund.
     * @param to     The account name to receive the fund.
     * @param amount The amount to be transferred.
     * @param date   The date that the fund was transferred.
     */
    public TransferCommand(String from, String to, double amount, Date date) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Executes the function to transfer the fund in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If bank account could not be found or insufficient amount to transfer.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BankException {
        profile.transferFund(this.from, this.to, this.amount, this.date, ui);
        return this.isExit;
    }
}
