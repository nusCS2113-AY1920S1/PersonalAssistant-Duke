package executor.command;

import executor.task.TaskList;
import ui.ReceiptTracker;
import ui.Ui;
import ui.Wallet;

import java.time.LocalDate;

public class CommandGetSpendingByDay extends Command {
    protected String userInput;

    /**
     * Constructor to show what the class is able to do.
     * @param userInput is the input from the user
     */
    public CommandGetSpendingByDay(String userInput) {
        this.commandType = CommandType.EXPENDEDDAY;
        this.userInput = userInput;
        this.description = "Provides the user the total expenditure for the day stated. "
                + "FORMAT: expendedday <day>";
    }

    @Override
    public void execute(Wallet wallet) {
        ReceiptTracker receiptsInDay = new ReceiptTracker();
        String currDate = LocalDate.now().toString();
        receiptsInDay = wallet.getReceipts().findReceiptsByDate(currDate);
        Double totalMoney = receiptsInDay.getTotalCashSpent();
        Ui.dukeSays("The total amount of money spent today is $" + totalMoney);
    }

    @Override
    public void execute(TaskList taskList) {

    }


}
