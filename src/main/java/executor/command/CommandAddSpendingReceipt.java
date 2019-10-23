package executor.command;

import executor.task.TaskList;
import ui.Receipt;
import ui.Wallet;

import java.time.LocalDate;
import java.util.ArrayList;

public class CommandAddSpendingReceipt extends CommandAddReceipt {

    /**
     * Constructor for CommandAddSpendingReceipt subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandAddSpendingReceipt(String userInput) {
        this.commandType = CommandType.OUT;
        this.userInput = userInput;
        this.cash = extractIncome(this.commandType, this.userInput);
        this.date = extractDate(this.userInput);
        this.tags = extractTags(this.userInput);
        this.description = "You can add a new spendings receipt in format of 'Out $5.00 /date 2019-02-01 /tags tag'.";;
    }

    @Override
    public void execute(TaskList taskList) {
    }

    @Override
    public void execute(Wallet wallet) {
        Receipt r = new Receipt(this.cash, this.date, this.tags);
        wallet.addReceipt(r);
    }

}
