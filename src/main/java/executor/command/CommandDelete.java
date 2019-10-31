package executor.command;

import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

public class CommandDelete extends Command {
    protected String userInput;

    // Constructor
    /**
     * Constructor for CommandDelete subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandDelete(String userInput) {
        this.userInput = userInput;
        this.description = "Deletes the specific entry that the user wants to remove. FORMAT: delete <Index_of_Entry>";
        this.commandType = CommandType.DELETE;
    }

    @Override
    public void execute(TaskList taskList) {
        try {
            int index = Integer.valueOf(userInput.replace("delete", "").trim()) - 1;
            Ui.dukeSays("Task '"
                    + String.valueOf(index + 1)
                    + ") "
                    + taskList.getList().get(index).getTaskName()
                    + "' deleted"
            );
            taskList.deleteTaskByIndex(index);
        } catch (Exception e) {
            Ui.dukeSays("Invalid 'delete' statement. Please indicate the index of the task you wish to mark delete.");
        }
    }

    @Override
    public void execute(Wallet wallet) {

    }
}