package owlmoney.logic.command.find;

import static owlmoney.commons.log.LogsCenter.getLogger;

import java.util.logging.Logger;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Executes FindTransactionCommand to find transaction.
 */
public class FindTransactionCommand extends Command {
    private final String name;
    private final String fromDate;
    private final String toDate;
    private final String description;
    private final String category;
    private final String type;
    private static final Logger logger = getLogger(FindTransactionCommand.class);


    /**
     * Creates an instance of FindTransactionCommand.
     *
     * @param name        The name of bank or card depending on the search.
     * @param fromDate    The date to search from.
     * @param toDate      The date to search until.
     * @param description The description keyword to search for.
     * @param category    The category keyword to search for.
     * @param type        The type of object to search for such as bank or card object.
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
     * Executes the function to find the transaction.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException        If bank name specified does not exist.
     * @throws TransactionException If parsing of date fails.
     * @throws CardException        If card with the name does not exist.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, TransactionException, CardException {
        profile.findTransaction(this.name, this.fromDate, this.toDate, this.description, this.category, this.type, ui);
        logger.info("Successful execution of finding transactions");
        return this.isExit;
    }
}
