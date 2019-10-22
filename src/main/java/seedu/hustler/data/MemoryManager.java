package seedu.hustler.data;

import java.io.IOException;

import seedu.hustler.Hustler;
import seedu.hustler.ui.Ui;

public class MemoryManager {

    public MemoryManager() {

    }

    public void createBackup() {
        Ui ui = new Ui();
        Storage storage = new Storage();

        try {
            storage.createBackup(Hustler.list.return_list());
            AvatarStorage.createBackup(Hustler.avatar);
        } catch (IOException e) {

        }
    }

    public void reloadBackup(int numberOfCommandsToUndo) throws IOException {
        Hustler.reloadBackup();
        CommandLog.restoreData(numberOfCommandsToUndo);
    }

    public void redo() throws IOException {
        CommandLog.redo();
    }
}
