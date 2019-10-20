package seedu.hustler.command.taskCommand;

import seedu.hustler.command.Command;
import seedu.hustler.data.MemoryManager;
import seedu.hustler.ui.Ui;

public class RedoCommand extends Command {
    public RedoCommand() {
    }
    
    /**
     * Undoes a an undo command.
     */
    public void execute() {
        MemoryManager memorymanager = new MemoryManager();
        memorymanager.redo();
    }
}
