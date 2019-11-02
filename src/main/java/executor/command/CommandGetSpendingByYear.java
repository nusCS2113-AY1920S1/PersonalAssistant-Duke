package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.ReceiptTracker;
import ui.Ui;
import ui.Wallet;

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
                + "Format : expendedyear <year> \n";
    }

    @Override
    public void execute(Wallet wallet) {
        ReceiptTracker receiptsInYear = new ReceiptTracker();
        String yearStr = Parser.parseForPrimaryInput(CommandType.EXPENDEDYEAR, userInput);
        int year = Integer.parseInt(yearStr);
        receiptsInYear = wallet.getReceipts().findReceiptByYear(year);
        Double totalMoney = receiptsInYear.getTotalCashSpent();
        Ui.dukeSays("The total amount of money spent in " + year + " : " + totalMoney);
    }

    @Override
    public void execute(TaskList taskList) {
    }

}

