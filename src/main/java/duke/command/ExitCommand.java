package duke.command;

import duke.patient.PatientList;
import duke.storage.PatientStorage;
import duke.storage.Storage;
import duke.storage.TaskStorage;
import duke.task.TaskList;
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
     * @return A boolean. True if the command tells Duke to exit, false
     *         otherwise.
     */
    @Override
    public boolean isExit() {
        return true;
    }

    /**
     * run the command with the respect TaskList, UI, and storage.
     *
     * @param tasks   The task list where tasks are saved.
     * @param ui      The user interface.
     * @param patientList object that handles local text file update
     */
    public void execute(TaskList tasks, PatientList patientList, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) {
        ui.exitInformation();
    }
}