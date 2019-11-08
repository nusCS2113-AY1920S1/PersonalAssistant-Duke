package owlmoney.logic.command.bond;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes DeleteBondCommand and prints the results.
 */
public class DeleteBondCommand extends Command {
    private final String bankName;
    private final String bondName;

    /**
     * Creates an instance of DeleteInvestmentCommand.
     *
     * @param bankName The name of the bank account.
     * @param bondName The name of the bond to be deleted.
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
     * @throws BankException If used on savings or bank does not exist.
     * @throws BondException If there are no bonds.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BondException, BankException {
        profile.profileDeleteBond(this.bankName, this.bondName, ui);
        return this.isExit;
    }
}
