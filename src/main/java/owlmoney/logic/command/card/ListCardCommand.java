package owlmoney.logic.command.card;

import static owlmoney.commons.log.LogsCenter.getLogger;

import java.util.logging.Logger;

import owlmoney.logic.command.Command;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes ListCardCommand to list card objects.
 */
public class ListCardCommand extends Command {
    private static final Logger logger = getLogger(ListCardCommand.class);


    /**
     * Executes the function to list cards in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws CardException If CardList is empty.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws CardException {
        profile.profileListCards(ui);
        logger.info("Successful execution of listing the cards");
        return this.isExit;
    }
}
