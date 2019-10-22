package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes DeleteSavingsCommand and prints the result.
 */
public class DeleteSavingsCommand extends Command {
    private final String bankName;
    private static final String SAVING = "saving";

    /**
     * Creates an instance of DeleteSavingCommand.
     *
     * @param bankName Bank name to be deleted.
     */
    public DeleteSavingsCommand(String bankName) {
        this.bankName = bankName;
    }

    /**
     * Executes the function to delete a savings account from the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If bank account fails check criteria.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BankException {
        profile.profileDeleteBank(this.bankName, SAVING, ui);
        return this.isExit;
    }
}
