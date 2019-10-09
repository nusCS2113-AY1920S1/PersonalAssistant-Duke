package seedu.hustler.command;

import seedu.hustler.data.MemoryManager;
import seedu.hustler.ui.Ui;

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
        if (this.taskInfo.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
            return;
        }

        this.numberOfCommandsToUndo = Integer.parseInt(taskInfo[1]);

        MemoryManager memorymanager = new MemoryManager();
        memorymanager.reloadBackup(numberOfCommandsToUndo);
    }
}
