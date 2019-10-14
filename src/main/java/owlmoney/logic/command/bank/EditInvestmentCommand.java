package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class EditInvestmentCommand extends Command {
    private final String name;
    private final String amount;
    private final String newName;

    /**
     * Creates an instance of EditInvestmentCommand.
     *
     * @param name    Name of bank account.
     * @param amount  New amount of bank account if any.
     * @param newName New name of bank account if any.
     */
    public EditInvestmentCommand(String name, String amount, String newName) {
        this.amount = amount;
        this.name = name;
        this.newName = newName;
    }

    /**
     * Executes the function to edit the details of an investment account in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BankException {
        profile.editInvestmentAccount(name, newName, amount, ui);
        return this.isExit;
    }
}
