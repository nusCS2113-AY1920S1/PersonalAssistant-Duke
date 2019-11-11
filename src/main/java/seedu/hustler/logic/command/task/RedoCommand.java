package seedu.hustler.logic.command.task;

import seedu.hustler.data.MemoryManager;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.OneWordAnomaly;
import seedu.hustler.ui.Ui;

import java.io.IOException;

public class RedoCommand extends Command {

    private final String[] userInput;
    private OneWordAnomaly redoAnomaly = new OneWordAnomaly();

    public RedoCommand(String[] userInput) {
        this.userInput = userInput;
    }
    
    /**
     * Undoes a an undo command.
     */
    public void execute() throws IOException {
        Ui ui = new Ui();
        try {
            redoAnomaly.detect(userInput);
            MemoryManager memorymanager = new MemoryManager();
            memorymanager.redo();
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }

    }
}
