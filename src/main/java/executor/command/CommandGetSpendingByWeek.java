package executor.command;

import interpreter.Parser;
import storage.StorageManager;
import ui.ReceiptTracker;
import ui.UiCode;
import ui.Wallet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class CommandGetSpendingByWeek extends Command {
    protected  String userInput;

    /**
     * Constructor to explain about the command.
     * @param userInput is the user's input
     */
    public CommandGetSpendingByWeek(String userInput) {
        super();
        this.commandType = CommandType.EXPENDEDWEEK;
        this.userInput = userInput;
        this.description = "Provides the user the total expenditure for the current week. \n"
                + "FORMAT : expendedweek";
    }

    @Override
    public void execute(StorageManager storageManager) {

        String checker = Parser.parseForPrimaryInput(CommandType.HELP, userInput);
        if (!checker.isEmpty()) {
            this.infoCapsule.setCodeToast();
            this.infoCapsule.setOutputStr("Extra variables added. FORMAT : expendedweek");
        }
        String day = LocalDate.now().getDayOfWeek().toString().toLowerCase();
        String dayDate = LocalDate.now().toString();
        ArrayList<String> dateList = new ArrayList<String>();
        Double total = 0.0;
        int dayInt = dayStrToInt(day);
        if (dayInt > 0 && dayInt < 8) {
            LocalDate dateTracker = LocalDate.parse(dayDate);
            for (int i = 0; i < dayInt; i++) {
                dateList.add(dateTracker.minusDays(i).toString());
            }
        }
        for (String a : dateList) {
            try {
                total += storageManager.getReceiptsByDate(a).getTotalExpenses();
            } catch (Exception e) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr(e.getMessage());
            }
        }
        int remainingDaysOfWeek = 7 - dayInt;
        String out = total.toString();
        this.infoCapsule.setUiCode(UiCode.CLI);
        this.infoCapsule.setOutputStr("The total amount spent this week is $"
                + out
                + " and there is/are "
                + remainingDaysOfWeek
                + "day(s) to end of week");
    }

    /**
     * Function to get the number of day in the week according to the day.
     * @param day is the string input of the current day of the week
     * @return is the number value of the day of the week
     */
    public int dayStrToInt(String day) {
        switch (day) {
        case "monday":
            return 1;
        case "tuesday":
            return 2;
        case "wednesday":
            return 3;
        case "thursday":
            return 4;
        case "friday":
            return 5;
        case "saturday":
            return 6;
        case "sunday":
            return 7;
        default:
            return 0;
        }
    }
}
