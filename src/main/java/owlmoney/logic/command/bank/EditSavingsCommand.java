package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes EditSavingsCommand to edit a saving object.
 */
public class EditSavingsCommand extends Command {
    private final String name;
    private final String income;
    private final String amount;
    private final String newName;

    /**
     * Creates an instance of EditSavingCommand.
     *
     * @param name    Name of bank account.
     * @param income  New income of bank account if any.
     * @param amount  New amount of bank account if any.
     * @param newName New name of bank account if any.
     */
    public EditSavingsCommand(String name, String income, String amount, String newName) {
        this.amount = amount;
        this.income = income;
        this.name = name;
        this.newName = newName;
    }

    /**
     * Executes the function to edit the details of a savings account in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If duplicate bank account name is found.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BankException {
        profile.profileEditSavingsAccount(name, newName, amount, income, ui);
        return this.isExit;
    }
}
