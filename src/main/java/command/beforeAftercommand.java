package command;
import command.Storage;
import common.AlphaNUSException;
import project.Project;

import java.util.LinkedHashMap;

public class beforeAftercommand {
    /**
     * Stores the projectmap before the current command is executed.
     * @param storage Storage that stores the projectmap before the current command is executed.
     * @throws AlphaNUSException if the file cannot be written to.
     */
    public static void beforedoCommand(Storage storage) throws AlphaNUSException {
        storage.writeToUndoFile(storage.readFromProjectsFile());
    }
    /**
     * Stores the projectmap after the current command is executed.
     * @param storage Storage that stores the projectmap after the current command is executed.
     * @throws AlphaNUSException if the file cannot be written to.
     */
    public static void afterCommand(Storage storage) throws AlphaNUSException {
        storage.writeToRedoFile(storage.readFromProjectsFile());
    }
}

