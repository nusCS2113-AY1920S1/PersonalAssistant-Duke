package executor.command;

import executor.task.TaskList;
import ui.Wallet;

public class CommandHelp extends Command {

    /**
     * Constructor to provide the user with the details about the commands available.
     */
    public CommandHelp() {
        this.description = "Provides the user with all the available commands and descriptions.";
    }

    @Override
    public void execute(TaskList taskList) {
        for (String s : CommandType.getNames()) {
            CommandType commandType = CommandType.valueOf(s);
            Command c = Executor.createCommand(commandType, "null");
            String commandDesc = c.getDescription();

            System.out.println(s.toUpperCase() + " - " + commandDesc);
        }
    }

    @Override
    public void execute(Wallet wallet) {
    }
}