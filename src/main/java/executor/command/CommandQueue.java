package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

public class CommandQueue extends Command {
    protected String userInput;

    /**
     * Constructor for CommandQueue subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandQueue(String userInput) {
        super();
        this.userInput = userInput;
        this.commandType = CommandType.QUEUE;
        this.description = "Queues user-inputted task in the list. \n"
                + "FORMAT : queue <taskindex> /task <taskdescription>";
    }

    @Override
    public void execute(StorageManager storageManager) {
        int mainTaskIndex;
        try {
            mainTaskIndex = Integer.parseInt(Parser.parseForPrimaryInput(this.commandType, this.userInput)) - 1;
        } catch (Exception e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Please enter a valid task index. Use LIST to show all tasks.\n");
            return;
        }
        String taskString = Parser.removeStr(this.commandType.toString(), this.userInput).trim();
        taskString = Parser.removeStr(String.valueOf(mainTaskIndex + 1), taskString).trim();
        taskString = Parser.removeStr("/task", taskString).trim();
        if (taskString.equals("")) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("New Task to be queued was not specified.\n");
            return;
        }
        CommandType newTaskCommandType = Parser.parseForCommandType(taskString);
        if (newTaskCommandType == null) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Unable to decipher task to be queued.");
            return;
        }
        if (newTaskCommandType.getCommandClass() != CommandNewTask.class) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("No Task detected after 'Queue'.");
            return;
        }
        String outputStr;
        try {
            storageManager.initializeQueueByIndex(mainTaskIndex);
            storageManager.queueTaskByIndex(mainTaskIndex, newTaskCommandType, taskString);
            outputStr = "New Task Queued after " + storageManager.getTaskNameByIndex(mainTaskIndex);
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr(outputStr);
    }
}
