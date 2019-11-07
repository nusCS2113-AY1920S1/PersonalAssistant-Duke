package executor.command;

import interpreter.Parser;
import storage.StorageManager;
import ui.UiCode;

import java.time.Year;

public class CommandGetSpendingByMonth extends Command {
    protected String userInput;

    /**
     * Constructor to explain about the command.
     * @param userInput is the input from the user
     */
    public CommandGetSpendingByMonth(String userInput) {
        super();
        this.commandType = CommandType.EXPENDEDMONTH;
        this.userInput = userInput;
        this.description = "Provides the user the total expenditure for the month stated. \n"
                + "FORMAT: expendedmonth <month> /year <year>";
    }

    @Override
    public void execute(StorageManager storageManager) {
        try {
            String monthStr = (Parser.parseForPrimaryInput(CommandType.EXPENDEDMONTH, userInput)).toLowerCase();
            if (monthStr.isEmpty()) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr("No month input detected. FORMAT : expendedmonth <month> /year <year>");
                return;
            }

            int month = monthStrToInt(monthStr);
            if (month == 0) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr("Wrong month input. Check Spelling");
                return;
            }

            String yearStr = Parser.parseForFlag("year", userInput);
            if (yearStr.isEmpty()) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr("No year input detected. FORMAT : expendedmonth <month> /year <year>");
                return;
            }

            if (yearStr.length() != 4) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr("Year input contains lesser/extra number of variables. "
                        + "\nFORMAT : expendedmonth <month> /year <year>");
                return;
            }

            try {
                int year = Integer.parseInt(yearStr);
                if (year > Year.now().getValue()) {
                    this.infoCapsule.setCodeError();
                    this.infoCapsule.setOutputStr("Future year entered is invalid!");
                    return;
                }

                if (year < 1800) {
                    this.infoCapsule.setCodeError();
                    this.infoCapsule.setOutputStr("Year is too far back into the past");
                    return;
                }
                Double totalMoney = storageManager.getReceiptsByMonthDate(month, year).getTotalCashSpent();
                this.infoCapsule.setUiCode(UiCode.CLI);
                this.infoCapsule.setOutputStr("The total amount of money spent in "
                        + monthStr
                        + " "
                        + year
                        + " : $"
                        + totalMoney
                        + "\n");

            } catch (Exception e) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr("Year input is either a double or contains String values.\n "
                        + "FORMAT : expendedmonth <month> /year <year>\n");
                return;
            }
        } catch (Exception e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Wrong format! FORMAT : expendedmonth <month> /year <year>");
            return;
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
