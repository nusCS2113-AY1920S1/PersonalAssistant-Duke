package owlmoney.logic.command.bond;

import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.Deposit;
import owlmoney.ui.Ui;

/**
 * Executes DeleteBondCommand and prints the results.
 */
public class DeleteBondCommand extends Command {
    private final String bankName;
    private final String bondName;
    private static final String BOND = "bonds";

    /**
     * Creates an instance of DeleteInvestmentCommand.
     *
     * @param bankName the name of the bank account.
     * @param bondName the name of the bond to be deleted.
     */
    public DeleteBondCommand(String bankName, String bondName) {
        this.bankName = bankName;
        this.bondName = bondName;
    }

    /**
     * Executes the function to delete an investment account from the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException if used on savings or bank does not exist.
     * @throws BondException if there are no bonds.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BondException, BankException {
        Bond tempBond = profile.profileGetBond(this.bankName, this.bondName);
        Deposit newDeposit = new Deposit(this.bondName, tempBond.getAmount(), new Date(), BOND);
        profile.profileAddNewDeposit(this.bankName, newDeposit, ui, BOND);
        profile.profileDeleteBond(this.bankName, this.bondName, ui);
        return this.isExit;
    }
}
