package owlmoney.logic.command.card;

import owlmoney.logic.command.Command;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes EditCardCommand to edit a credit card object.
 */
public class EditCardCommand extends Command {

    private final String name;
    private final String limit;
    private final String rebate;
    private final String newName;

    /**
     * Creates an instance of EditCardCommand.
     *
     * @param name    Name of credit card.
     * @param limit   New limit of credit card if any.
     * @param rebate  New rebate of credit card if any.
     * @param newName New name of credit card if any.
     */
    public EditCardCommand(String name, String limit, String rebate, String newName) {
        this.limit = limit;
        this.rebate = rebate;
        this.name = name;
        this.newName = newName;
    }

    /**
     * Executes the function to edit the details of a credit card in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws CardException If card cannot be found.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws CardException {
        profile.profileEditCardDetails(name, newName, limit, rebate, ui);
        return this.isExit;
    }
}
