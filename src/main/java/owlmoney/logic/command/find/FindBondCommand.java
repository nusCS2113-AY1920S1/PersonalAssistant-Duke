package owlmoney.logic.command.find;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class FindBondCommand extends Command {
    private final String name;
    private final String from;

    /**
     * Constructor to create an instance of AddExpenditureCommand.
     *
     * @param name        Bank account name.
     * @param from        Represents type of expenditure to be added.
     */
    public FindBondCommand(String name, String from) {
        this.name = name;
        this.from = from;
    }

    /**
     * Executes the function to add a new expenditure to the bank account.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If bank amount becomes negative after adding expenditure.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, BondException {
        profile.findBond(this.name, this.from, ui);
        return this.isExit;
    }
}
