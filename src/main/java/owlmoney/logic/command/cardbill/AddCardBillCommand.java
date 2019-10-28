package owlmoney.logic.command.cardbill;

import java.time.YearMonth;
import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.Deposit;
import owlmoney.model.transaction.Expenditure;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * AddCardBillCommand class which contains the functions to add a new card bill object.
 */
public class AddCardBillCommand extends Command {
    private final String card;
    private final YearMonth cardDate;
    private final Date expDate;
    private final String bank;
    private final String type;
    private final String expDescription;
    private final String category;

    /**
     * Creates an instance of AddExpenditureCommand.
     *
     * @param card  Credit card name of bill to be paid.
     * @param date  Month and year of bill to be paid.
     * @param bank  Bank account name to charge the credit card bill to.
     */
    public AddCardBillCommand(String card, YearMonth date, String bank) {
        this.card = card;
        this.cardDate = date;
        this.expDate = new Date();
        this.bank = bank;
        this.type = "bank";
        this.expDescription = "Payment for Credit Card Bill - " + card + " " + date;
        this.category = "Credit Card";
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
            throw new CardException("You have no expenditures for " + card + " for the month of "
                    + cardDate + "!");
        }
    }

    /**
     * Executes the function to add a new credit card bill in bank expenditure, card rebate in bank deposit,
     * and transfers the card expenditures from unpaid to paid transaction list.
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
        String depDescription = "Rebate for Credit Card (" + profile.getCardRebateAmount(card) + ") - "
                + card + " " + cardDate;
        double billAmount = profile.getCardUnpaidBillAmount(card, cardDate);
        double rebateAmount = profile.getCardRebateAmount(card) * billAmount;
        checkBillAmountZero(billAmount, card, cardDate);
        Expenditure newExpenditure =
                new Expenditure(this.expDescription, billAmount, this.expDate, this.category);
        Deposit newDeposit = new Deposit(depDescription, rebateAmount, this.expDate, this.category);
        profile.payCardBill(card, bank, newExpenditure, newDeposit, cardDate, ui, type);
        return this.isExit;
    }
}
