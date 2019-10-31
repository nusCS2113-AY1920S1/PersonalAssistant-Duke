package executor.command;

import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

import java.text.DecimalFormat;

public class CommandDisplayExpenditure extends Command {
    /**
     * Constructor for CommandDisplayExpenditure subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandDisplayExpenditure(String userInput) {
        this.userInput = userInput;
        this.description = "Shows the total amount of money spent";
        this.commandType = CommandType.EXPENSES;
    }

    @Override
    public void execute(TaskList taskList) {
    }

    @Override
    public void execute(Wallet wallet) {
        double totalExpenses = wallet.getTotalExpenses();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        System.out.println("Total Expenditure: $"
                + decimalFormat.format(totalExpenses)
        );
    }
}
