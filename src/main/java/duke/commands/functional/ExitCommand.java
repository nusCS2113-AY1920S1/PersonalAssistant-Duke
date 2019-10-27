package duke.commands.functional;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.TaskManager;
import duke.util.Ui;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;

/**
 * Represents a command to exit Duke. The command.ExitCommand class
 * extends from the Command class for the user to quit the
 * program
 */
public class ExitCommand implements Command {

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
    public void execute(AssignedTaskManager patientTask, TaskManager tasks,
                        PatientManager patientList, Ui ui, StorageManager storageManager) throws DukeException {
        ui.exitInformation();
    }
}