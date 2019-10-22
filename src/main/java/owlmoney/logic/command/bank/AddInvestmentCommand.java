package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.Bank;
import owlmoney.model.bank.Investment;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes AddInvestmentCommand to add a new investment object.
 */
public class AddInvestmentCommand extends Command {
    private final String name;
    private final double amount;

    /**
     * Creates an instance of AddInvestmentCommand.
     *
     * @param name   Name of new investment object.
     * @param amount Initial amount of money in the new investment object.
     */
    public AddInvestmentCommand(String name, double amount) {
        this.amount = amount;
        this.name = name;
    }

    /**
     * Executes the function to create a new investment account in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If duplicate investment account name found.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BankException {
        Bank newInvestment = new Investment(this.name, this.amount);
        profile.profileAddNewBank(newInvestment, ui);
        return this.isExit;
    }
}
