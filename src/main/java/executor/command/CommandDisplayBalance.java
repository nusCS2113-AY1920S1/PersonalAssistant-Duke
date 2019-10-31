package executor.command;

import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CommandDisplayBalance extends Command {

    // Constructor
    /**
     * Constructor for CommandDisplayBalance subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandDisplayBalance(String userInput) {
        this.userInput = userInput;
        this.description = "Shows the current balance available in the wallet";
        this.commandType = CommandType.BALANCE;
    }

    @Override
    public void execute(TaskList taskList) {
    }

    @Override
    public void execute(Wallet wallet) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        System.out.println("Your Balance: $"
                + decimalFormat.format(wallet.getBalance())
        );
    }
}
