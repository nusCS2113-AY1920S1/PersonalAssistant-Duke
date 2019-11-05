package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.ReceiptTracker;
import ui.Ui;
import ui.Wallet;

import java.time.LocalDate;
import java.time.Year;

public class CommandGetSpendingByYear extends Command {
    private String userInput;

    /**
     * Constructor to explain about the method.
     * @param userInput userInput from CLI
     */
    public CommandGetSpendingByYear(String userInput) {
        this.commandType = CommandType.EXPENDEDYEAR;
        this.userInput = userInput;
        this.description = "Provides the user the total expenditure for the year stated. \n"
                + "FORMAT : expendedyear <year>";
    }

    @Override
    public void execute(Wallet wallet) {
        ReceiptTracker receiptsInYear = new ReceiptTracker();
        String yearStr = Parser.parseForPrimaryInput(CommandType.EXPENDEDYEAR, userInput);
        try {
            if (yearStr.isEmpty()) {
                Ui.dukeSays("No year input detected. FORMAT : expendedyear 2019");
                return;
            }

            if (yearStr.length() != 4) {
                Ui.dukeSays("Year input contains extra number of variables. FORMAT : expendedyear 2019");
                return;
            }


            int year = Integer.parseInt(yearStr);

            if (year > Year.now().getValue()) {
                Ui.dukeSays("Future year entered is invalid!");
                return;
            }

            if (year < 1000) {
                Ui.dukeSays("Year is too far back into the past");
                return;
            }
            receiptsInYear = wallet.getReceipts().findReceiptByYear(year);
            Double totalMoney = receiptsInYear.getTotalCashSpent();
            Ui.dukeSays("The total amount of money spent in " + year + " : $" + totalMoney);

        } catch (Exception e) {
            Ui.dukeSays("Year input is either a double or contains String values. FORMAT : expendedyear 2019");
        }

    }

    @Override
    public void execute(TaskList taskList) {
    }

}

