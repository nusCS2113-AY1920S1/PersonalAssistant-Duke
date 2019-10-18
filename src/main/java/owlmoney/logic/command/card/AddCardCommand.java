package owlmoney.logic.command.card;

import owlmoney.logic.command.Command;
import owlmoney.model.card.Card;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * AddCardCommand class which contains the functions to add a new card object.
 */
public class AddCardCommand extends Command {
    private final String name;
    private final double limit;
    private final double rebate;

    /**
     * Constructor that creates an instance of AddCardCommand.
     *
     * @param name   Credit card name of the new card object.
     * @param limit  Credit card monthly limit of the new card object.
     * @param rebate Credit card monthly rebate of the new card object.
     */
    public AddCardCommand(String name, double limit, double rebate) {
        this.name = name;
        this.limit = limit;
        this.rebate = rebate;
    }

    /**
     * Executes the function to create a new card in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws CardException If duplicate credit card name found.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws CardException {
        Card newCard = new Card(this.name, this.limit, this.rebate);
        profile.profileAddNewCard(newCard, ui);
        return this.isExit;
    }
}
