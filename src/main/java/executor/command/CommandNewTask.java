package executor.command;

import duke.exception.DukeException;
import storage.task.TaskType;
import interpreter.Parser;
import storage.StorageManager;

public class CommandNewTask extends Command {
    protected String userInput;
    protected TaskType taskType;

    /**
     * Constructor for the CommandNewTask subCommand Class.
     * @param userInput The user input from the CLI.
     */
    public CommandNewTask(String userInput) {
        super();
        this.userInput = userInput;
        this.commandType = CommandType.TASK;
        this.taskType = extractTaskType();
        this.description = "Adds a new " + this.taskType.toString().toLowerCase() + " to your task list.\n"
                + "FORMAT: " + this.taskType.toString() + " <name> /<tag> <desc>";
    }

    @Override
    public void execute(StorageManager storageManager) {
        String outputStr;
        try {
            checkForwardSlash(this.userInput);
            storageManager.createTask(this.taskType, this.userInput);
            outputStr = "I've added "
                    + storageManager.getPrintableLatestTask()
                    + " to your private list("
                    + storageManager.getTaskListSize()
                    + ").\n"
            ;
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr(outputStr);
    }

    /**
     * Throws an exception when there is no '/' in the user input.
     * @param input this is the user's input
     * @throws DukeException this shows the error message and gives the format to follow
     */
    private void checkForwardSlash(String input) throws DukeException {
        if (this.taskType.equals(TaskType.FDURATION)) {
            if (!Parser.containsForwardSlash(input)) {
                throw new DukeException("Check your format!!! Correct format is: fduration <description> / <time>\n");
            }
        }
    }

    private TaskType extractTaskType() {
        CommandType specificCommandType = Parser.parseForCommandType(this.userInput);
        if (specificCommandType == null) {
            return TaskType.BLANK;
        } else {
            return TaskType.valueOf(specificCommandType.toString());
        }
    }
}
