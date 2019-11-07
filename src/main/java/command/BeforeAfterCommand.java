package command;

import command.Storage;
import common.AlphaNUSException;
import project.Fund;
import project.Project;
import project.ProjectManager;

import java.util.LinkedHashMap;

public class BeforeAfterCommand {
    /**
     * Stores the projectmap before the current command is executed.
     * @param storage Storage that stores the projectmap before the current command is executed.
     * @throws AlphaNUSException if the file cannot be written to.
     */
    public static void beforedoCommand(Storage storage, ProjectManager projectManager) throws AlphaNUSException {
        storage.writeToUndoFile(projectManager.projectmap);
    }
    public static void undoforfund(Storage storage, Fund fund) throws AlphaNUSException {
        storage.writeToundoFundFile(fund);
    }
    /**
     * Stores the projectmap after the current command is executed.
     * @param storage Storage that stores the projectmap after the current command is executed.
     * @throws AlphaNUSException if the file cannot be written to.
     */
    public static void afterCommand(Storage storage, ProjectManager projectManager) throws AlphaNUSException {
        storage.writeToRedoFile(projectManager.projectmap);
    }
    public static void redoforfund(Storage storage, Fund fund) throws AlphaNUSException {
        storage.writeToredoFundFile(fund);
    }
}

