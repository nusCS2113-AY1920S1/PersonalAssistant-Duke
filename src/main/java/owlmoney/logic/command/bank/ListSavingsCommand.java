package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * ListSavingsCommand class which contains the execution function to list saving objects.
 */
public class ListSavingsCommand extends Command {

    /**
     * Executes the function to list savings in the profile.
     *
     * @param profile Profile of the user.
     * @param ui Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) {
        profile.listBanks(ui);
        return this.isExit;
    }
}
