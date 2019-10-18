package owlmoney.logic.command.bond;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes ListBondCommand and prints the results.
 */
public class ListBondCommand extends Command {
    private final String bankName;
    private final int displayNum;

    /**
     * Constructor to create an instance of ListBondCommand.
     *
     * @param bankName   Bank account name.
     * @param displayNum Number of expenditures to display.
     */
    public ListBondCommand(String bankName, int displayNum) {
        this.bankName = bankName;
        this.displayNum = displayNum;
    }

    /**
     * Executes the function to list the bonds in the investment account from the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException if used on savings or bank does not exist.
     * @throws BondException if there are no bonds.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, BondException {
        profile.profileListBonds(bankName, ui, displayNum);
        return this.isExit;
    }
}
