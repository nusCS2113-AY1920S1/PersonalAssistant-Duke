package executor.command;

import executor.task.TaskList;
import ui.Wallet;

public class CommandUpdateBalance extends Command {

    private Double newBalance;

    /**
     * Constructor for the CommandUpdateBalance class.
     * @param userInput The user Input from the CLI
     */
    public CommandUpdateBalance(String userInput) {
        Double amount = Double.parseDouble(userInput.replace("setbalance", "").trim());
        this.newBalance = amount;
    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        wallet.setBalance(this.newBalance);
    }

}
