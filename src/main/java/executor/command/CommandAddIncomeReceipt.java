package executor.command;

import executor.task.TaskList;
import ui.IncomeReceipt;
import ui.Ui;
import ui.Wallet;

import java.time.LocalDate;
import java.util.ArrayList;

public class CommandAddIncomeReceipt extends CommandAddReceipt {

    /**
     * Constructor for CommandAddIncomeReceipt subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandAddIncomeReceipt(String userInput) {
        this.commandType = CommandType.IN;
        this.userInput = userInput;
        this.cash = extractIncome(this.commandType, this.userInput);
        this.date = extractDate(this.userInput);
        this.tags = extractTags(this.userInput);
        this.description = "You can add a new income receipt. \n"
                + "FORMAT : in <value> /date <YYYY-MM-DD> /tags <tag>";
    }

    @Override
    public void execute(TaskList taskList) {
    }

    @Override
    public void execute(Wallet wallet) {
        IncomeReceipt r = new IncomeReceipt(this.cash, this.date, this.tags);
        wallet.addReceipt(r);
        Ui.dukeSays("Added Receipt: $" + r.getCashGained().toString() + "with tags: " + r.getTags().toString());
    }
}
