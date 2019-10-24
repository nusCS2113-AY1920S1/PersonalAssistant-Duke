package owlmoney.logic.command.find;

import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

public class FindTransactionCommand extends Command {
    private final String name;
    private final String fromDate;
    private final String toDate;
    private final String description;
    private final String category;
    private final String type;

    /**
     * Constructor to create an instance of AddExpenditureCommand.
     *
     * @param name        Bank account name.
     * @param from        Represents type of expenditure to be added.
     */
    public FindTransactionCommand(String name, String fromDate, String toDate, String description,
            String category, String type) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.description = description;
        this.category = category;
        this.type = type;
    }

    /**
     * Executes the function to add a new expenditure to the bank account.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If bank amount becomes negative after adding expenditure.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, TransactionException, CardException {
        profile.findTransaction(this.name, this.fromDate, this.toDate, this.description, this.category, this.type, ui);
        return this.isExit;
    }
}
