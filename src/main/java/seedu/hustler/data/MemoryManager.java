package seedu.hustler.data;

import java.io.IOException;

import seedu.hustler.Hustler;
import seedu.hustler.ui.Ui;

public class MemoryManager {

    public MemoryManager() {

    }

    /**
     * This method creates a backup of the Hustler program's initial
     * data state upon first start. This backup will be used when the
     * undo command is called.
     */
    public void createBackup() {
        Ui ui = new Ui();
        TaskStorage taskStorage = new TaskStorage();

        try {
            taskStorage.createBackup(Hustler.list.returnList());
            AvatarStorage.createBackup(Hustler.avatar);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * When the undo command is called, the Hustler program reloads the
     * backup of the initial data state. This is what this reloadBackup()
     * function does.
     * @param numberOfCommandsToUndo is the number of commands the user wishes
     *     to undo.
     */
    public void reloadBackup(int numberOfCommandsToUndo) throws IOException {
        if (!CommandLog.isUndoUnderflow(numberOfCommandsToUndo)) {
            Hustler.reloadBackup();
        }
        CommandLog.restoreData(numberOfCommandsToUndo);
    }

    public void redo() throws IOException {
        CommandLog.redo();
    }
}
