package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Instalment;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

public class AutoUpdateInstalmentCommand extends MoneyCommand{
    private LocalDate currDate = LocalDate.now();
    private boolean payForTheMonth;

    public AutoUpdateInstalmentCommand() {
        this.payForTheMonth = false;
    }

    @Override
    public boolean isExit() { return false; }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        for(Instalment ins : account.getInstalments()) {
            Period diff = Period.between(ins.getDateBoughtDate(), currDate);
            int paymentsMade = diff.getMonths() + diff.getYears() * 12; //rough algorithm
            ins.percentPay(paymentsMade);
            if(diff.getDays() != 0) {
                payForTheMonth = false;
            }
            if(diff.getDays() == 0 &&  !payForTheMonth) {
                //account.getExpListCurrMonth().add(ins);
                //account.getExpListTotal().add(ins);
                payForTheMonth = true;
            }
        }
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) { return; }
}
