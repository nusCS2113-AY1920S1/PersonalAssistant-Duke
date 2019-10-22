package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes ListSavingsCommand to list saving objects.
 */
public class ListSavingsCommand extends Command {
    private static final String SAVING = "saving";

    /**
     * Executes the function to list savings in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If there are no bank account of specified type.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BankException {
        profile.profileListBanks(SAVING, ui);
        return this.isExit;
    }
}
