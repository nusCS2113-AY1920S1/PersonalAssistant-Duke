package owlmoney.logic.command.card;

import static owlmoney.commons.log.LogsCenter.getLogger;

import java.util.logging.Logger;

import owlmoney.logic.command.Command;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes DeleteCardCommand to delete a card object.
 */
public class DeleteCardCommand extends Command {
    private final String name;
    private static final Logger logger = getLogger(DeleteCardCommand.class);


    /**
     * Creates an instance the DeleteCardCommand.
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
        logger.info("Successful execution of deleting a card");
        return this.isExit;
    }
}
