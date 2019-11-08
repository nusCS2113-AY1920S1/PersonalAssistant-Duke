package owlmoney.logic.command.find;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes FindBankOrCardCommand to find bank or card.
 */
public class FindBankOrCardCommand extends Command {

    private final String name;
    private final String type;

    /**
     * Creates an instance of FindBankOrCardCommand.
     *
     * @param name The name of either bank or card.
     * @param type The type of object to find.
     */
    public FindBankOrCardCommand(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Executes the function to find either bank or card.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If there is no matches for saving or investment object.
     * @throws CardException If the credit card name cannot be found.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, CardException {
        profile.findBankOrCard(this.name, this.type, ui);
        return this.isExit;
    }
}
