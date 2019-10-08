package duke;


import duke.command.Command;
import duke.core.DukeException;
import duke.core.CommandManager;
import duke.patient.PatientList;
import duke.storage.PatientStorage;
import duke.storage.TaskStorage;
import duke.task.TaskList;
import duke.core.Ui;

/**
 * Represents Duke, a Personal Assistant to help
 * users tracking their progress.
 */
public class Duke implements Runnable {
    /**
     * A Storage object that handles reading tasks from a local
     * file and saving them to the same file.
     */
    private TaskStorage taskStorage;
    private PatientStorage patientStorage;
    /**
     * A TaskList object that deals with add, delete, mark as done,
     * find functions of a list of tasks.
     */
    private TaskList taskList;
    private PatientList patientList;
    /**
     * A Ui object that deals with interactions with the user.
     */
    private Ui ui;
    /**
     * Constructs a Duke object with a relative file path.
     * Initialize the user interface and reads tasks from the specific text file.
     * @param filePath A string that represents the path of the local file
     *          used for storing tasks.
     */
    public Duke(String filePath) {
        ui = new Ui();
        taskStorage = new TaskStorage(filePath + "/data.txt");
        patientStorage = new PatientStorage(filePath + "/patients.csv");

        try {
            taskList = new TaskList(taskStorage.load());
            patientList = new PatientList(patientStorage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            taskList = new TaskList();
        }
    }

    /**
     * Runs the Duke program.
     * Reads user input until a "bye" message is received.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = CommandManager.manageCommand(fullCommand);
                c.execute(taskList,patientList, ui, taskStorage, patientStorage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        System.exit(0);
    }
    /**
     * Starts the Duke thread and Reminder thread concurrently
     * by passing a filepath to duke and a global ui object&
     * task list to Reminder
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new Duke("./data").run();
    }
}
