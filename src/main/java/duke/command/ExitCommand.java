package duke.command;

import duke.core.CommandManager;
import duke.core.DukeException;
import duke.patient.PatientManager;
import duke.storage.CmdFreqStorage;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.relation.PatientTaskList;
import duke.storage.TaskStorage;
import duke.task.TaskManager;
import duke.core.Ui;

/**
 * Represents a command to exit Duke. The command.ExitCommand class
 * extends from the Command class for the user to quit the
 * program
 */
public class ExitCommand extends Command {

    /**
     * Constructs a ExitCommand object.
     */
    public ExitCommand() {
        super();
    }

    /**
     * Indicates whether Duke should exist.
     *
     * @return A boolean. True if the command tells Duke to exit, false otherwise.
     */
    @Override
    public boolean isExit() {
        return true;
    }

    /**
     * run the command with the respect TaskList, UI, and storage.
     *
     * @param tasks       The task list where tasks are saved.
     * @param ui          The user interface.
     * @param patientList object that handles local text file update
     */
    public void execute(PatientTaskList patientTask, TaskManager tasks,
                        PatientManager patientList, Ui ui, PatientTaskStorage patientTaskStorage,
                        TaskStorage taskStorage, PatientStorage patientStorage, CmdFreqStorage cmdFreqStorage,
                        CommandManager commandManager) throws DukeException {
        ui.exitInformation();
    }
}