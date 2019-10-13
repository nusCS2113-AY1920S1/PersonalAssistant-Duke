package duke.command;

import duke.core.DukeException;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.relation.PatientTaskList;
import duke.storage.TaskStorage;
import duke.task.TaskManager;
import duke.core.Ui;

/**
 * Represents a command class received from user. It is an abstract
 * class that can not be instantiated, its child class represents different kind
 * of user command
 */
public abstract class Command {

    public abstract void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientList, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException;
    /**
     * Decide whether duke should exist.
     *
     * @return A boolean. True if the command tells Duke to exit, false
     *         otherwise.
     */
    public abstract boolean isExit();

}