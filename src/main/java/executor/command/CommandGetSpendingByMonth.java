package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.ReceiptTracker;
import ui.Ui;
import ui.Wallet;

public class CommandGetSpendingByMonth extends Command {
    protected String userInput;

    /**
     * Constructor to explain about the command.
     * @param userInput is the input from the user
     */
    public CommandGetSpendingByMonth(String userInput) {
        this.commandType = CommandType.EXPENDED;
        this.userInput = userInput;
        this.description = "Provides the user the total expenditure for the month stated. "
                + "FORMAT: expended <month> /year<year>";
    }

    @Override
    public void execute(Wallet wallet) {
        ReceiptTracker receiptsInMonth = new ReceiptTracker();
        String mon = Parser.parseForPrimaryInput(CommandType.EXPENDED, userInput);
        int month = monthStr(mon);
        if (month != 0) {
            int year = Integer.parseInt(Parser.parseForFlag("year", userInput));
            receiptsInMonth = wallet.getReceipts().findReceiptByMonthYear(month, year);
            Double totalMoney = receiptsInMonth.getTotalCashSpent();
            Ui.dukeSays("The total amount of money spent in " + mon + " " + year + " : " + totalMoney);
        } else {
            Ui.dukeSays("Invalid input, CORRECT FORMAT : expended <month> <year> ");
        }
    }

    /**
     * Returns the corresponding month value.
     * @param month is the name of month from the userInput
     * @return the value of month, eg: march --> 3.
     */
    public int monthStr(String month) {
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

    @Override
    public void execute(TaskList taskList) {

    }


}
