package owlmoney.logic.command.cardbill;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.UUID;

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
    private final Date expenditureDate;
    private final String bank;
    private final String type;
    private final String expenditureDescription;
    private static final int PERCENTAGE_TO_DECIMAL = 100;
    private static final String BANK_TYPE = "bank";

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
        this.expenditureDate = getCurrentDate();
        this.bank = bank;
        this.type = BANK_TYPE;
        this.expenditureDescription = "Bill - " + card + " " + date;
    }

    /**
     * Returns current date.
     *
     * @return Current date.
     */
    private Date getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(new Date());
        Date currentDate = null;
        try {
            currentDate = dateFormat.parse(dateString);
        } catch (ParseException exceptionMessage) {
            // Error will never happen as there is no user input
        }
        return currentDate;
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
     * Throws CardException if card bill for a specified month has already been paid.
     *
     * @param profile   The user profile.
     * @param card      The card which the bill is going to be paid.
     * @param cardDate  The YearMonth date of the card bill.
     * @throws CardException    If card bill for the specified month has already been paid.
     */
    private void checkIfBillPaidBefore(Profile profile, String card, YearMonth cardDate)
            throws CardException {
        if (profile.getCardPaidBillAmount(card, cardDate) != 0) {
            throw new CardException("You cannot add a card bill for " + cardDate
            + " because you have already done so!");
        }
    }

    /**
     * Executes the function to add a new credit card bill in bank expenditure, card rebate in bank deposit,
     * and transfers the card expenditures from unpaid to paid transaction list.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return        False so OwlMoney will not terminate yet.
     * @throws CardException        If credit card does not exist, or error in paying of card bill.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If invalid transaction when deleting.
     */
    public boolean execute(Profile profile, Ui ui) throws CardException, BankException, TransactionException {
        profile.checkCardExists(card);
        checkIfBillPaidBefore(profile, card, cardDate);
        UUID cardId = profile.getCardId(card);
        String depDescription = "Rebate " + profile.getCardRebateAmount(card) + "% - "
                + card + " " + cardDate;
        double billAmount = profile.getCardUnpaidBillAmount(card, cardDate);
        double rebateAmount = (profile.getCardRebateAmount(card) / PERCENTAGE_TO_DECIMAL) * billAmount;
        checkBillAmountZero(billAmount, card, cardDate);
        Expenditure newExpenditure =
                new Expenditure(this.expenditureDescription, billAmount, this.expenditureDate,
                cardId, this.cardDate);
        Deposit newDeposit = new Deposit(depDescription, rebateAmount, this.expenditureDate, cardId,
                this.cardDate);
        profile.addCardBill(card, bank, newExpenditure, newDeposit, cardDate, ui, type);
        return this.isExit;
    }
}
