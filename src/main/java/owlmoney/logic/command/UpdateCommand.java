package owlmoney.logic.command;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes UpdateCommand and prints the result.
 */
public class UpdateCommand extends Command {
    private Boolean manualCall;

    public UpdateCommand(boolean manualCall) {
        this.manualCall = manualCall;
    }

    /**
     * Executes the function to update outdated recurring transactions.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return False so OwlMoney does not terminate yet.
     * @throws BankException If cannot update income.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BankException {
        profile.profileUpdate(ui, manualCall);
        return isExit;
    }
}
