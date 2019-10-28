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
    private final int expno;
    private final int depno;

    /**
     * Constructor to create an instance of DeleteCardBillCommand.
     *
     * @param card  Credit card name of bill to be paid.
     * @param date  Month and year of bill to be paid.
     * @param bank  Bank account name to charge the credit card bill to.
     * @param expno Transaction number of the credit card bill expenditure to be deleted.
     * @param depno Transaction number of the credit card bill deposit to be deleted.
     */
    public DeleteCardBillCommand(String card, YearMonth date, String bank, int expno, int depno) {
        this.card = card;
        this.cardDate = date;
        this.bank = bank;
        this.expno = expno;
        this.depno = depno;
        this.type = "bank";

    }

    /**
     * Throws an exception if the credit card bill amount for the specified YearMonth date is zero.
     *
     * @param amount    The credit card bill amount.
     * @param card      The credit card name where is bill is from.
     * @param cardDate  The credit card YearMonth date of the bill.
     * @throws CardException    If the credit card bill amount for the specified YearMonth date is zero.
     */
    private void checkBillAmountZero(double amount, String card, YearMonth cardDate) throws CardException {
        if (amount == 0) {
            throw new CardException("You have no paid expenditures for " + card + " for the month of "
                    + cardDate + "!");
        }
    }

    /**
     * Throws an exception if the entered bill amount does not match with the actual bill amount in the bank.
     *
     * @param profile   Profile of the user.
     * @param bank      The bank account of the bill amount to be checked.
     * @param amount    The bill amount from the credit card to be checked.
     * @param expno     The transaction number of the bank expenditure to be checked.
     * @throws CardException        If card does not exist.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If transaction is not an expenditure.
     */
    private void checkBillAmountMatch(Profile profile, String bank, double amount, int expno)
            throws CardException, BankException, TransactionException {
        double amountInBank = profile.getBankExpAmountById(bank, expno);
        if (amountInBank != amount) {
            throw new CardException("Expenditure #" + expno + " amount does not match amount in card!");
        }
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
        profile.checkCardExists(card);
        double billAmountInCard = profile.getCardPaidBillAmount(card, cardDate);
        checkBillAmountZero(billAmountInCard, card, cardDate);
        checkBillAmountMatch(profile, bank, billAmountInCard, expno);
        profile.profileDeleteDeposit(depno, bank, ui);
        profile.profileDeleteExpenditure(expno, bank, ui, type);
        profile.unpayCardBill(card, cardDate, ui, type);
        return this.isExit;
    }
}
