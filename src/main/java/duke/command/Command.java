package duke.command;

import duke.core.CommandManager;
import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.relation.PatientTaskList;
import duke.storage.CmdFreqStorage;
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
                                 PatientStorage patientStorage, CmdFreqStorage cmdFreqStorage,
                                 CommandManager commandManager) throws DukeException;
    /**
     * This function is used across all the child command for running of
     * Command Frequency Logic.
     * @param commandManager passing a CommandManager object to get the CmdFreqTable for use.
     */

    public void runCommandFrequencyLogic(CommandManager commandManager) {
        this.hasBeenAddedBefore = true;
        String commandName = this.getClass().getSimpleName();
        if (!hasBeenAddedBefore) {
            commandManager.getCmdFreqTable().put(commandName, 1);
        }
        int count = commandManager.getCmdFreqTable().containsKey(commandName)
                    ? commandManager.getCmdFreqTable().get(commandName) : 0;
        commandManager.getCmdFreqTable().put(commandName, count + 1);
    }

    /**
     * Decide whether duke should exist.
     *
     * @return A boolean. True if the command tells Duke to exit, false
     */
    public abstract boolean isExit();

}