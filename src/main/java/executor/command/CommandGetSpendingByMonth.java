package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.ReceiptTracker;
import ui.Ui;
import ui.Wallet;

import java.time.Year;

public class CommandGetSpendingByMonth extends Command {
    protected String userInput;

    /**
     * Constructor to explain about the command.
     * @param userInput is the input from the user
     */
    public CommandGetSpendingByMonth(String userInput) {
        this.commandType = CommandType.EXPENDEDMONTH;
        this.userInput = userInput;
        this.description = "Provides the user the total expenditure for the month stated. \n"
                + "FORMAT: expendedmonth <month> /year <year>";
    }
    
    @Override
    public void execute(TaskList taskList) {
    }

    @Override
    public void execute(Wallet wallet) {
        try {
            ReceiptTracker receiptsInMonth = new ReceiptTracker();
            String monthStr = (Parser.parseForPrimaryInput(CommandType.EXPENDEDMONTH, userInput)).toLowerCase();

            if (monthStr.isEmpty()) {
                Ui.dukeSays("No month input detected. FORMAT : expendedmonth <month> /year <year>");
                return;
            }

            int month = monthStrToInt(monthStr);

            if (month == 0) {
                Ui.dukeSays("Wrong month input. Check Spelling");
                return;
            }

            String yearStr = Parser.parseForFlag("year", userInput);

            if (yearStr.isEmpty()) {
                Ui.dukeSays("No year input detected. FORMAT : expendedmonth <month> /year <year>");
                return;
            }

            if (yearStr.length() != 4) {
                Ui.dukeSays("Year input contains extra number of variables. FORMAT : expendedmonth <month> /year <year>");
                return;
            }

            try {
                int year = Integer.parseInt(yearStr);

                if (year > Year.now().getValue()) {
                    Ui.dukeSays("Future year entered is invalid!");
                    return;
                }

                if (year < 1000) {
                    Ui.dukeSays("Year is too far back into the past");
                    return;
                }
                receiptsInMonth = wallet.getReceipts().findReceiptByMonthYear(month, year);
                Double totalMoney = receiptsInMonth.getTotalCashSpent();
                Ui.dukeSays("The total amount of money spent in " + monthStr + " " + year + " : $" + totalMoney);

            } catch (Exception e) {
                Ui.dukeSays("Year input is either a double or contains String values. FORMAT : expendedmonth <month> /year <year>");
            }
        } catch (Exception e) {
            Ui.dukeSays("Wrong format! FORMAT : expendedmonth <month> /year <year>");
        }
    }

    /**
     * Returns the corresponding month value.
     * @param month is the name of month from the userInput
     * @return the value of month, eg: march --> 3.
     */
    public int monthStrToInt(String month) {
        switch (month) {
        case "january":
            return 1;
        case "february":
            return 2;
        case "march":
            return 3;
        case "april":
            return 4;
        case "may":
            return 5;
        case "june":
            return 6;
        case "july":
            return 7;
        case "august":
            return 8;
        case "september":
            return 9;
        case "october":
            return 10;
        case "november":
            return 11;
        case "december":
            return 12;
        default:
            return 0;
        }
    }
}
