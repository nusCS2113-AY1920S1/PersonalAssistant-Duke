package owlmoney.logic.command.transaction;

import static owlmoney.commons.log.LogsCenter.getLogger;

import java.util.logging.Logger;

import owlmoney.logic.command.Command;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Executes DeleteExpenditureCommand to delete an expenditure.
 */
public class DeleteExpenditureCommand extends Command {
    private final int expNumber;
    private final String from;
    private final String type;
    private static final Logger logger = getLogger(DeleteExpenditureCommand.class);

    /**
     * Creates an instance of a DeleteExpenditureCommand.
     *
     * @param index       Transaction number.
     * @param accountName Card or Bank account name.
     * @param type        The type of account to delete expenditure from.
     */
    public DeleteExpenditureCommand(int index, String accountName, String type) {
        this.expNumber = index;
        this.from = accountName;
        this.type = type;
    }

    /**
     * Executes the function to delete an expenditure transaction.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If invalid transaction.
     * @throws CardException        If card does not exist.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException, TransactionException, CardException {
        profile.profileDeleteExpenditure(this.expNumber, this.from, ui, this.type, false);
        logger.info("Successful execution of DeleteExpenditureCommand");
        return this.isExit;
    }
}
