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
public class DeleteCardBillCommand extends Command {
    private final String card;
    private final YearMonth cardDate;
    private final String bank;
    private final String type;
    private final int expno;
    private final int depno;

    /**
     * Constructor to create an instance of AddExpenditureCommand.
     *
     * @param card  Credit card name of bill to be paid.
     * @param date  Month and year of bill to be paid.
     * @param bank    Bank account name to charge the credit card bill to.
     */
    public DeleteCardBillCommand(String card, YearMonth date, String bank, int expno, int depno) {
        this.card = card;
        this.cardDate = date;
        this.bank = bank;
        this.expno = expno;
        this.depno = depno;
        this.type = "bank";

    }

    private void checkBillAmountZero(double amount, String card, YearMonth cardDate) throws CardException {
        if (amount == 0) {
            throw new CardException("You have no paid expenditures for " + card + " for the month of "
                    + cardDate + "!");
        }
    }

    private void checkBillAmountMatch(Profile profile, String bank, double amount, int expno)
            throws CardException, BankException, TransactionException {
        double amountInBank = profile.getBankExpAmountById(bank, expno);
        if (amountInBank != amount) {
            throw new CardException("Expenditure #" + expno + " amount does not match amount in card!");
        }
    }

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
