package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.storage.StorageManager;
import duke.task.TaskManager;

/**
 * Represents a command class received from user. It is an abstract
 * class that can not be instantiated, its child class represents different kind
 * of user command
 */
public abstract class Command {

    //protected boolean hasBeenAddedBefore = false;

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
    public abstract void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientList, Ui ui,
                                 StorageManager storageManager) throws DukeException;


    /**
     * Decide whether duke should exist.
     *
     * @return A boolean. True if the command tells Duke to exit, false
     */
    public abstract boolean isExit();

}