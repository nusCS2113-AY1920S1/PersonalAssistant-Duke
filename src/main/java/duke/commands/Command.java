package duke.commands;

import duke.exceptions.DukeException;
import duke.models.tasks.TaskManager;
import duke.util.DukeUi;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;

//@@author HUANGXUANKUN
/**
 * Represents a command class received from user.
 * It is an abstract class that can not be instantiated, its child class represents different kind
 * of user command.
 */
public interface Command {

    /**
     * It takes in model managers, and execute the commands by calling methods in the managers.
     *
     * @param patientTasks   Manager of model class AssignedTask
     * @param tasks          Manager of model class Task
     * @param patientList    Manager of model class Patient
     * @param dukeUi         Manager of all Duke Dialog
     * @param storageManager Manager of all storage, use for saving/loading data to/from local disk
     * @throws DukeException .
     */
    void execute(AssignedTaskManager patientTasks, TaskManager tasks, PatientManager patientList, DukeUi dukeUi,
                 StorageManager storageManager) throws DukeException;

    /**
     * Decide whether duke should exist.
     *
     * @return A boolean. True if the command tells Duke to exit, false
     */
    boolean isExit();

}