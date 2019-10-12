package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.Bank;
import owlmoney.model.bank.Investment;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class AddInvestmentCommand extends Command {
    private final String name;
    private final double amount;

    /**
     * Constructor that creates an instance the AddInvestmentCommand.
     *
     * @param name Name of new investment object.
     * @param amount Initial amount of new investment object.
     */
    public AddInvestmentCommand(String name, double amount) {
        this.amount = amount;
        this.name = name;
    }

    /**
     * Executes the function to create a new investment account in the profile.
     *
     * @param profile Profile of the user.
     * @param ui Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) {
        Bank newInvestment = new Investment(this.name, this.amount);
        profile.addNewBank(newInvestment, ui);
        return this.isExit;
    }
}
