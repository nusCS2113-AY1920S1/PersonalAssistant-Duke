package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

public class CommandMarkDone extends Command {
    private String userInput;

    /**
     * Constructor for CommandMarkDone subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandMarkDone(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Marks a certain task as done \n"
                + "FORMAT: Done";
        this.commandType = CommandType.DONE;
    }

    @Override
    public void execute(StorageManager storageManager) {
        String outputStr;
        try {
            int index = Integer.parseInt(Parser.removeStr("done", this.userInput)) - 1;
            storageManager.markTaskDoneByIndex(index);
            storageManager.loadQueuedTasksByIndex(index);
            outputStr = genMarkDoneReply(index, storageManager);
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        } catch (Exception e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid 'done' statement. Please indicate the index of"
                    + " the task you wish to mark done.");
            return;
        }
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr(outputStr);
    }

    /**
     * Generates the standard duke reply to inform user that the Task is marked done.
     * @param index The index of the Task in the TaskList
     * @param storageManager StorageManager containing the TaskList with all tasks
     * @return Standard duke reply for user
     */
    private String genMarkDoneReply(int index, StorageManager storageManager) throws DukeException {
        return "Alrighty, I've marked task '"
                + (index + 1)
                + ") "
                + storageManager.getTaskNameByIndex(index)
                + "' as done!\n";
    }
}
