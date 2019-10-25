package owlmoney.logic.command.find;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes FindBondCommand to find bonds.
 */
public class FindBondCommand extends Command {
    private final String name;
    private final String from;

    /**
     * Creates an instance of FindBondCommand.
     *
     * @param name The bond name.
     * @param from The name of the investment account containing the bond.
     */
    public FindBondCommand(String name, String from) {
        this.name = name;
        this.from = from;
    }

    /**
     * Executes the function to find the bonds.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If investment account does not exist.
     * @throws BondException If no bonds could be found.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, BondException {
        profile.findBond(this.name, this.from, ui);
        return this.isExit;
    }
}
