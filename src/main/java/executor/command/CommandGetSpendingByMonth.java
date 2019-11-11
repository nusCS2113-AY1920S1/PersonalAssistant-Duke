package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import ui.UiCode;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

public class CommandGetSpendingByMonth extends Command {
    protected String userInput;
    private String monthStr;
    private int month;
    private int year;
    private int daysRemaining;
    private Double totalMoney;
    private String yearStr;
    private boolean isThisMonth = false;

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
            checkMonthInput();
            checkIfYearIsCorrect();
            outputOfExpenditure(storageManager);
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
        } catch (Exception e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Wrong format! FORMAT : expendedmonth <month> /year <year>");
        }
    }

    /**
     * Returns the corresponding month value.
     * @param month is the name of month from the userInput
     * @return the value of month, eg: march --> 3.
     */
    private int monthStrToInt(String month) {
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

    /**
     * Function to output the expenditure.
     * @param storageManager is the class that contains all the getter functions for the wallet
     * @throws DukeException is the error message
     */
    private void outputOfExpenditure(StorageManager storageManager) throws DukeException {
        try {
            year = Integer.parseInt(yearStr);
            checkCredibilityOfYear();
            totalMoney = storageManager.getReceiptsByMonthDate(month, year).getTotalExpenses();
            if (isThisMonth) {
                this.infoCapsule.setUiCode(UiCode.CLI);
                this.infoCapsule.setOutputStr(outputMessage() + "Number of day(s) left in this month is/are "
                        + daysRemaining);
            } else {
                this.infoCapsule.setUiCode(UiCode.CLI);
                this.infoCapsule.setOutputStr(outputMessage());
            }
        } catch (DukeException f) {
            throw f;
        } catch (Exception e) {
            throw new DukeException("Year input is either a double or contains String values.\n "
                    + "FORMAT : expendedmonth <month> /year <year>\n");
        }
    }

    private String outputMessage() {
        return "The total amount of money spent in "
                + monthStr
                + " "
                + year
                + " : $"
                + totalMoney
                + "."
                + "\n";
    }

    /**
     * Function to check if the month input is correct.
     * @throws DukeException is the error message
     */
    private void checkMonthInput() throws DukeException {
        monthStr = (Parser.parseForPrimaryInput(CommandType.EXPENDEDMONTH, userInput)).toLowerCase();
        if (monthStr.isEmpty()) {
            throw new DukeException("No month input detected. FORMAT : expendedmonth <month> /year <year>");
        }

        if (monthStr.equals(LocalDate.now().getMonth().toString().toLowerCase())) {
            isThisMonth = true;
            YearMonth yearMonthObject = YearMonth.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
            int daysInMonth = yearMonthObject.lengthOfMonth();
            int currday = LocalDate.now().getDayOfMonth();
            daysRemaining = daysInMonth - currday;
        }

        month = monthStrToInt(monthStr);
        if (month == 0) {
            throw new DukeException("Wrong month input. Check Spelling");
        }
    }

    /**
     * Function to check if the year input is correct.
     * @throws DukeException is the error message
     */
    private void checkIfYearIsCorrect() throws DukeException {
        yearStr = Parser.parseForFlag("year", userInput);
        if (yearStr.isEmpty()) {
            throw new DukeException("No year input detected. FORMAT : expendedmonth <month> /year <year>");
        }

        if (yearStr.length() != 4) {
            throw new DukeException("Year input contains lesser/extra number of variables. "
                    + "\nFORMAT : expendedmonth <month> /year <year>");
        }
    }

    /**
     * Function to check if the year input taken in is credible.
     * @throws DukeException is the error message
     */
    private void checkCredibilityOfYear() throws DukeException {
        if (year > Year.now().getValue()) {
            throw new DukeException("Future year entered is invalid!");
        }

        if (year < 1800) {
            throw new DukeException("Year is too far back into the past");
        }
    }
}
