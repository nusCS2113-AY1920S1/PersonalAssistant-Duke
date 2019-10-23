package executor.command;

import executor.task.TaskList;
import ui.Wallet;
import ui.Ui;

public class CommandGetUserBalance extends Command {

    /**
     * Constructor for the CommandUpdateBalance class.
     */
    public CommandGetUserBalance() {

    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        try {
            Ui.dukeSays("Your current balance is " + wallet.getBalance() + " \n");
            wallet.getBalance();
            Ui.printSeparator();
        } catch (Exception e) {
            Ui.dukeSays("Setbalance before getbalance");
            Ui.printSeparator();
        }
    }
}
