package seedu.hustler.logic.command.task;

import seedu.hustler.MainWindow;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.UndoAnomaly;
import seedu.hustler.data.MemoryManager;
import java.io.IOException;
import seedu.hustler.ui.Ui;


/**
 * Command that undoes the previous command.
 */
public class UndoCommand extends Command {
    /**
     * Contains task type and description.
     */
    private String[] taskInfo;

    /**
     * Detects anomalies for input.
     **/
    private UndoAnomaly anomaly = new UndoAnomaly();

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

        Ui ui = new Ui();
        MemoryManager memorymanager = new MemoryManager();

        if (this.taskInfo.length == 1) {
            this.numberOfCommandsToUndo = 1;
            MainWindow.offPrinting();
            memorymanager.reloadBackup(numberOfCommandsToUndo);
        } else {
            try {
                anomaly.detect(taskInfo);
                this.numberOfCommandsToUndo = Integer.parseInt(taskInfo[1]);
            } catch (CommandLineException e) {
                ui.showMessage(e.getMessage());
                return;
            }
            MainWindow.offPrinting();
            memorymanager.reloadBackup(numberOfCommandsToUndo);
        }
    }
}
