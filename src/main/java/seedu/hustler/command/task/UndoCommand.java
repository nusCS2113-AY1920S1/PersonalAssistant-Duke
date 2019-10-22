package seedu.hustler.command.task;

import seedu.hustler.command.Command;
import seedu.hustler.data.MemoryManager;

/**
 * Command that undoes the previous command.
 */
public class UndoCommand extends Command {
    /**
     * Contains task type and description.
     */
    private String[] taskInfo;

    /**
     * Contains the number of commands to undo..
     */
    private int numberOfCommandsToUndo;

    /**
     * Initializes taskInfo.
     *
     * @param taskInfo the number of user commands to undo.
     */
    public UndoCommand(String[] taskInfo) {
        this.taskInfo = taskInfo;
    }
    
    /**
     * Undoes a set number of user commands..
     */
    public void execute() {
        MemoryManager memorymanager = new MemoryManager();

        if (this.taskInfo.length == 1) {
            this.numberOfCommandsToUndo = 1;
            memorymanager.reloadBackup(numberOfCommandsToUndo);
        } else {
            this.numberOfCommandsToUndo = Integer.parseInt(taskInfo[1]);
            memorymanager.reloadBackup(numberOfCommandsToUndo);
        }
    }
}
