package moneycommands;

import controlpanel.MoneyStorage;
import money.Account;
import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;

import java.text.ParseException;

public class ViewPastMonthIncome extends MoneyCommand {
    private String inputString;
    private int month;
    private int year;

    public ViewPastMonthIncome(String command) {
        inputString = command.replaceFirst("check income ", "");
        String[] splitStr = inputString.split(" ");
        month = Integer.parseInt(splitStr[0]);
        year = Integer.parseInt(splitStr[1]);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        if (month < 1 || month > 12) {
            throw new DukeException("Month is invalid! Please pick a month from 1-12");
        }

    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) { return; }
}
