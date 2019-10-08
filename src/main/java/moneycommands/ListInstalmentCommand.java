package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Instalment;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;

public class ListInstalmentCommand extends MoneyCommand {

    public ListInstalmentCommand(){
    }

    @Override
    public boolean isExit() { return false; }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        int counter = 1;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        for (Instalment i : account.getInstalments()) {
            ui.appendToOutput(" " + counter + ".[" + df.format(i.getPercentage()) + "%] " + i.getDescription() + " ($"
                    + df.format(i.EqualMonthlyInstalment()) + " per month until " + i.getDateEndDate() + ")\n");
            counter++;
        }
    }
}
