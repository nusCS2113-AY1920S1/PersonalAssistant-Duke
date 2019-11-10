//@@author HUANGXUANKUN

package duke.commands.gui;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.patients.PatientManager;
import duke.models.tasks.TaskManager;
import duke.storages.StorageManager;
import duke.util.DukeUi;

/**
 * An command class to control GUI components.
 */
public class ShowAssignedTasksCommand implements Command {


    /**
     * Show assigned tasks in table form at the center of the GUI.
     *
     * @param patientTask    contains the information between all the tasks and patients.
     * @param tasks          contains information of all the tasks.
     * @param patientManager contains information of all the patients.
     * @param dukeUi         interacts with user.
     * @param storageManager save the changes in csv file.
     * @throws DukeException if there is error during saving the internal states.
     */
    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {

        dukeUi.printDukeResponse("Got it! Show all patient assigned tasks in the center table.");
    }

    /**
     * Decides whether duke should exit.
     *
     * @return A boolean. True if the command will cause duke to exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
