package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Expenditure;
import money.Instalment;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

/**
 * This command automatically updates the payment make by the user monthly.
 */
public class AutoUpdateInstalmentCommand extends MoneyCommand {
    private LocalDate currDate = LocalDate.now();

    //@@author ChenChao19
    /**
     * Default Constructor for AutoUpdateInstalmentCommand.
     */
    public AutoUpdateInstalmentCommand() {
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the AutoUpdateInstalmentCommand. Automatically updates the user
     * when he needs to pay for and instalment and automatically add it into his expenditure list.
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws ParseException If invalid date is parsed
     * @throws DukeException When the command is invalid
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        for (Instalment ins : account.getInstalments()) {
            if (ins.getFullyPaid()) {
                continue;
            }
            Period diff = Period.between(ins.getDateBoughtDate(), currDate);
            int paymentsMade = diff.getMonths() + diff.getYears() * 12;
            ins.percentPay(paymentsMade);
            if (diff.getDays() != 0) {
                ins.isNotPayTheMonth();
            }
            if (diff.getDays() == 0 && !ins.getPayForTheMonth()) {
                Expenditure e = new Expenditure(ins.equalMonthlyInstalment(), ins.getDescription(),
                        ins.getCategory(), currDate);
                account.getExpListTotal().add(e);
                ins.isPayTheMonth();
                paymentsMade += 1;
                ins.percentPay(paymentsMade);
            }
            if (ins.getNumOfPayments() == ins.getPaymentsMade()) {
                ins.setFullyPaid();
            }
        }
    }

    public void setCurrDate(LocalDate date) {
        currDate = date;
    }

    public LocalDate getCurrDate() {
        return currDate;
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}