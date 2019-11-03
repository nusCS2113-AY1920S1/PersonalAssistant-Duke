package owlmoney.logic.command.cardbill;

import java.time.YearMonth;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * DeleteCardBillCommand class which contains the functions to delete a new card bill object.
 */
public class DeleteCardBillCommand extends Command {
    private final String card;
    private final YearMonth cardDate;
    private final String bank;
    private final String type;

    /**
     * Creates an instance of DeleteCardBillCommand.
     *
     * @param card  Credit card name of bill to be paid.
     * @param date  Month and year of bill to be paid.
     * @param bank  Bank account name to charge the credit card bill to.
     */
    public DeleteCardBillCommand(String card, YearMonth date, String bank) {
        this.card = card;
        this.cardDate = date;
        this.bank = bank;
        this.type = "bank";
    }

    /**
     * Executes the function to delete a credit card bill in bank expenditure, card rebate in bank deposit,
     * and transfers the card expenditures from paid to unpaid transaction list.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return        False so OwlMoney will not terminate yet.
     * @throws CardException        If credit card does not exist.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If invalid transaction when deleting.
     */
    public boolean execute(Profile profile, Ui ui) throws CardException, BankException, TransactionException {
        profile.deleteCardBill(card, cardDate, bank, ui, type);
        return this.isExit;
    }
}
