package command;

import command.Storage;
import common.AlphaNUSException;
import project.ProjectManager;

public class BeforeAfterCommand {
    /**
     * Stores the projectmap before the current command is executed.
     * @param storage Storage that stores the projectmap before the current command is executed.
     * @throws AlphaNUSException if the file cannot be written to.
     */
    public static void beforedoCommand(Storage storage, ProjectManager projectManager) throws AlphaNUSException {
        storage.writeToUndoFile(projectManager.projectmap);
    }

    /**
     * Stores the projectmap after the current command is executed.
     * @param storage Storage that stores the projectmap after the current command is executed.
     * @throws AlphaNUSException if the file cannot be written to.
     */
    public static void afterCommand(Storage storage, ProjectManager projectManager) throws AlphaNUSException {
        storage.writeToRedoFile(projectManager.projectmap);
    }
}

