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
        String taskString = Parser.parseForFlag("task", this.userInput);
        if (taskString == null) {
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
        int mainTaskIndex = Integer.parseInt(Parser.parseForPrimaryInput(this.commandType, this.userInput));
        storageManager.initializeQueueByIndex(mainTaskIndex);
        String outputStr;
        try {
            storageManager.queueTaskByIndex(mainTaskIndex, newTaskCommandType, taskString);
            outputStr = "New Task Queued after :" + storageManager.getTaskNameByIndex(mainTaskIndex);
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr(outputStr);
    }
}
