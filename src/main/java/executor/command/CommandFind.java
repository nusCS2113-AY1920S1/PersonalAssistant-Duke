package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;

public class CommandFind extends Command {
    protected String userInput;

    /**
     * Constructor for CommandFind subCommand Class.
     * @param userInput The User Input to be translated into a Command
     */
    public CommandFind(String userInput) {
        this.userInput = userInput;
        this.description = "Parses input and loops through list of entries and checks if input matches any of them";
        this.commandType = CommandType.FIND;
    }

    @Override
    public void execute(Wallet wallet) {

    }

    @Override
    public void execute(TaskList taskList) {
        try {
            String queryInput = Parser.removeStr("find", this.userInput);
            queryInput = queryInput.toLowerCase();
            Ui.dukeSays("Here are the Tasks matching your query '"
                    + queryInput
                    + "'."
            );
            findTasks(queryInput, taskList);
        } catch (Exception e) {
            Ui.dukeSays("No such task found.");
        }
        Ui.printSeparator();
    }

    /**
     * Finds and prints each task that contains the string.
     *
     * @param name     The substring to be found
     * @param taskList The TaskList containing the Tasks.
     */
    public void findTasks(String name, TaskList taskList) {
        for (int index = 0; index < taskList.getSize(); ++index) {
            try {
                if (taskList.getList().get(index).getTaskName().toLowerCase().contains(name)) {
                    taskList.printTaskByIndex(index);
                }
            } catch (Exception e) {
                System.out.println("Read invalid taskName");
            }
        }
    }
}
