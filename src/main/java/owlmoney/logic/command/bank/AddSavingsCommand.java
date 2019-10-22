package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.Bank;
import owlmoney.model.bank.Saving;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes AddSavingsCommand and prints the result.
 */
public class AddSavingsCommand extends Command {
    private final String name;
    private final double income;
    private final double amount;

    /**
     * Creates an instance of AddSavingCommand.
     *
     * @param name   Name of new saving object.
     * @param income Income of new saving object.
     * @param amount Initial amount of new saving object.
     */
    public AddSavingsCommand(String name, double income, double amount) {
        this.amount = amount;
        this.income = income;
        this.name = name;
    }

    /**
     * Executes the function to create a new savings account in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If duplicate savings account name found.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BankException {
        Bank newSaving = new Saving(this.name, this.amount, this.income);
        profile.profileAddNewBank(newSaving, ui);
        return this.isExit;
    }
}
