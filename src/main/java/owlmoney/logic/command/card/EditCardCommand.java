package owlmoney.logic.command.card;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class EditCardCommand extends Command {

    private final String name;
    private final String limit;
    private final String rebate;
    private final String newName;

    /**
     * Creates an instance of EditSavingCommand.
     *
     * @param name    Name of bank account.
     * @param limit  New income of bank account if any.
     * @param rebate  New amount of bank account if any.
     * @param newName New name of bank account if any.
     */
    public EditCardCommand(String name, String limit, String rebate, String newName) {
        this.limit = limit;
        this.rebate = rebate;
        this.name = name;
        this.newName = newName;
    }

    /**
     * Executes the function to edit the details of a savings account in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws CardException {
        profile.editCardDetails(name, newName, limit, rebate, ui);
        return this.isExit;
    }
}
