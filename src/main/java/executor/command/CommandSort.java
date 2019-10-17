package executor.command;

import executor.task.TaskList;
import ui.Wallet;

public class CommandSort extends Command {

    protected String userInput;

    //constructor
    public CommandSort(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.SORT;
    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {

    }
}
