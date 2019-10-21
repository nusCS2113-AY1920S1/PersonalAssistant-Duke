package owlmoney.logic.command.find;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class FindBankOrCardCommand extends Command {

    private final String accName;
    private final String type;

    /**
     * Constructor to create an instance of AddExpenditureCommand.
     *
     * @param name        Bank account name.
     * @param type        Represents type of expenditure to be added.
     */
    public FindBankOrCardCommand(String name, String type) {
        this.accName = name;
        this.type = type;
    }

    /**
     * Executes the function to add a new expenditure to the bank account.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If bank amount becomes negative after adding expenditure.
     * @throws CardException If the credit card name cannot be found.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, CardException {
        profile.findBankOrCard(this.accName, this.type, ui);
        return this.isExit;
    }
}
