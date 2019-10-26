package duke.commands;

import duke.exceptions.DukeException;
import duke.models.tasks.TaskManager;
import duke.util.Ui;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;

/**
 * Represents a command class received from user. It is an abstract
 * class that can not be instantiated, its child class represents different kind
 * of user command
 */
public interface Command {

    /**
     * .
     *
     * @param patientTask    .
     * @param tasks          .
     * @param patientList    .
     * @param ui             .
     * @param storageManager .
     * @throws DukeException .
     */
    void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientList, Ui ui,
                 StorageManager storageManager) throws DukeException;

    /**
     * Decide whether duke should exist.
     *
     * @return A boolean. True if the command tells Duke to exit, false
     */
    boolean isExit();

}