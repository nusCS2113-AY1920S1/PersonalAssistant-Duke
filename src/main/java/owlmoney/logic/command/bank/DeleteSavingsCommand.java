package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * DeleteSavingsCommand class which contains the execution function to delete a saving object.
 */
public class DeleteSavingsCommand extends Command {
    private final String bankName;

    /**
     * Constructor that creates an instance the DeleteSavingCommand.
     *
     * @param bankName Bank name to be deleted.
     */
    public DeleteSavingsCommand(String bankName) {
        this.bankName = bankName;
    }

    /**
     * Executes the function to delete a saving from the profile.
     *
     * @param profile Profile of the user.
     * @param ui Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) {
        profile.deleteBank(this.bankName, ui);
        return this.isExit;
    }
}
