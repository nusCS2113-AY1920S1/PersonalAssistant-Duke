package moneycommands;

import controlpanel.MoneyStorage;
import money.Account;
import controlpanel.DukeException;
;import controlpanel.Ui;
import money.Expenditure;

import java.text.ParseException;

public class ViewPastMonthExpenditure extends MoneyCommand {
    private int month;
    private int year;

    public ViewPastMonthExpenditure(String command) {
        String inputString = command.replaceFirst("check expenditure ", "");
        String[] splitStr = inputString.split(" ");
        month = Integer.parseInt(splitStr[0]);
        year = Integer.parseInt(splitStr[1]);
    }

    private String getMonthName(int month) {
        switch (month) {
            case 1: {
                return "January";
            }
            case 2: {
                return "February";
            }
            case 3: {
                return "March";
            }
            case 4: {
                return "April";
            }
            case 5: {
                return "May";
            }
            case 6: {
                return "June";
            }
            case 7: {
                return "July";
            }
            case 8: {
                return "August";
            }
            case 9: {
                return "September";
            }
            case 10: {
                return "October";
            }
            case 11: {
                return "November";
            }
            case 12: {
                return "December";
            }
            default:
                return null;
        }
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

        float totalMonthExpenditure = 0;
        int counter = 1;
        for (Expenditure i : account.getExpListTotal()) {
            if (i.getDateBoughtTime().getMonthValue() == month && i.getDateBoughtTime().getYear() == year) {
                ui.appendToOutput(" " + counter + "." + i.toString() + "\n");
                counter++;
                totalMonthExpenditure += i.getPrice();
            }
        }

        ui.appendToOutput("Total expenditure for " + getMonthName(month) + " of " + year + " : $");
        ui.appendToOutput(totalMonthExpenditure + "\n");

    }
}

