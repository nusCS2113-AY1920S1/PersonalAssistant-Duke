package command;

import common.AlphaNUSException;
import project.Fund;
import project.Project;
import project.ProjectManager;

import java.util.LinkedHashMap;

/**@@author E03737902
 */

public class BeforeAfterCommand {
    /**
     * Stores the projectmap before the current command is executed.
     * @param storage Storage that stores the projectmap before the current command is executed.
     * @param projectManager ProjectManager that consists the projectmap.
     * @throws AlphaNUSException if the file cannot be written to.
     */

    public static void beforeCommand(ProjectManager projectManager, Storage storage) throws AlphaNUSException {
        storage.writeToUndoFile(projectManager.projectmap);
    }

    /**
     *Stores the fund before the current command is executed.
     * @param storage Storage that stores the fund before the current command is executed.
     * @param fund Fund before the current command is executed.
     * @throws AlphaNUSException
     */
    public static void undoforfund(Storage storage, Fund fund) throws AlphaNUSException {
        storage.writeToundoFundFile(fund);
    }
    /**
     * Stores the projectmap after the current command is executed.
     * @param storage Storage that stores the projectmap after the current command is executed.
     * @throws AlphaNUSException if the file cannot be written to.
     * @return
     */
    public static void afterCommand(ProjectManager projectManager, Storage storage) throws AlphaNUSException {
        storage.writeToRedoFile(projectManager.projectmap);
    }
    /**
     *Stores the fund after the current command is executed.
     * @param storage Storage that stores the fund after the current command is executed.
     * @param fund Fund after the current command is executed.
     * @throws AlphaNUSException
     */
    public static void redoforfund(Storage storage, Fund fund) throws AlphaNUSException {
        storage.writeToredoFundFile(fund);
    }

}


