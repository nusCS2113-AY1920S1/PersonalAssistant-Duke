package executor.command;

import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

public class CommandList extends Command {

    // Constructor
    /**
     * Constructor for CommandList subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandList(String userInput) {
        this.userInput = userInput;
        this.description = "Lists all tasks and receipts added by user";
        this.commandType = CommandType.LIST;
    }

    @Override
    public void execute(TaskList taskList) {
        Ui.dukeSays("You have ("
                + String.valueOf(taskList.getSize())
                + ") Tasks in your list!"
        );
        taskList.printTasks();
        Ui.printSeparator();
    }

    @Override
    public void execute(Wallet wallet) {
        Ui.dukeSays("You have ("
                + wallet.getReceipts().size()
                + ") receipts!"
        );
        wallet.getReceipts().printReceipts();
    }
}
