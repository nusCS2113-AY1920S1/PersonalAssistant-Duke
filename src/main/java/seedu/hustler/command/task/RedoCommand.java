package seedu.hustler.command.task;

import seedu.hustler.command.Command;
import seedu.hustler.data.MemoryManager;
import seedu.hustler.ui.Ui;

import java.io.IOException;

public class RedoCommand extends Command {
    public RedoCommand() {
    }
    
    /**
     * Undoes a an undo command.
     */
    public void execute() throws IOException {
        MemoryManager memorymanager = new MemoryManager();
        memorymanager.redo();
    }
}
