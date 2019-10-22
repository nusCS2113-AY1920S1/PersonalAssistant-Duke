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

public class AutoUpdateInstalmentCommand extends MoneyCommand{
    private LocalDate currDate = LocalDate.now();

    //@@ ChenChao19
    public AutoUpdateInstalmentCommand() {}

    @Override
    public boolean isExit() { return false; }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        for(Instalment ins : account.getInstalments()) {
            Period diff = Period.between(ins.getDateBoughtDate(), currDate);
            int paymentsMade = diff.getMonths() + diff.getYears() * 12 + 1;
            ins.percentPay(paymentsMade);
            if(diff.getDays() != 0) {
                ins.isNotPayTheMonth();;
            }
            if(diff.getDays() == 0 && !ins.getPayForTheMonth()) {
                Expenditure e = new Expenditure(ins.EqualMonthlyInstalment(), ins.getDescription(),
                        ins.getCategory(), currDate);
                account.getExpListCurrMonth().add(e);
                account.getExpListTotal().add(e);
                ins.isPayTheMonth();
                ui.appendToOutput("You have paid " + ins.EqualMonthlyInstalment() + " for " +
                        ins.getDescription() + ". It is currently " + ins.getPercentage() + "% paid.");
            };
        }
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}