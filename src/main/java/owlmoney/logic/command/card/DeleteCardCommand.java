package owlmoney.logic.command.card;

import owlmoney.logic.command.Command;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * DeleteCardCommand class which contains the execution function to delete a card object.
 */
public class DeleteCardCommand extends Command {
    private final String name;

    /**
     * Constructor that creates an instance the DeleteCardCommand.
     *
     * @param name Card name to be deleted.
     */
    public DeleteCardCommand(String name) {
        this.name = name;
    }

    /**
     * Executes the function to delete a card from the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws CardException If card does not exist.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws CardException {
        profile.profileDeleteCard(this.name, ui);
        return this.isExit;
    }
}
