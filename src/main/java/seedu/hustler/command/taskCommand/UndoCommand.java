package seedu.hustler.command.taskCommand;

import seedu.hustler.command.Command;
import seedu.hustler.data.MemoryManager;
import seedu.hustler.ui.Ui;

import java.io.IOException;

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
    public void execute() throws IOException {
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
