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

    public AutoUpdateInstalmentCommand() {
    }

    @Override
    public boolean isExit() { return false; }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        for(Instalment ins : account.getInstalments()) {
            Period diff = Period.between(ins.getDateBoughtDate(), currDate);
            int paymentsMade = diff.getMonths() + diff.getYears() * 12; //rough algorithm
            ins.percentPay(paymentsMade);
        }
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
