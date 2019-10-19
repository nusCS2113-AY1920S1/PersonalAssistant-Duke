package duke.command;

import duke.core.CommandManager;
import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.statistic.CommandCounter;
import duke.storage.CounterStorage;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.task.TaskManager;

/**
 * Represents a command class received from user. It is an abstract
 * class that can not be instantiated, its child class represents different kind
 * of user command
 */
public abstract class Command {

    protected boolean hasBeenAddedBefore = false;

    /**
     * .
     *
     * @param patientTask        .
     * @param tasks              .
     * @param patientList        .
     * @param ui                 .
     * @param patientTaskStorage .
     * @param taskStorage        .
     * @param patientStorage     .
     * @throws DukeException .
     */
    public abstract void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientList, Ui ui,
                                 PatientTaskStorage patientTaskStorage, TaskStorage taskStorage,
                                 PatientStorage patientStorage, CounterStorage counterStorage,
                                 CommandCounter commandCounter) throws DukeException;



    /**
     * Decide whether duke should exist.
     *
     * @return A boolean. True if the command tells Duke to exit, false
     */
    public abstract boolean isExit();

}