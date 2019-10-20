package duke;

import duke.command.Command;
import duke.command.DukeCommand;
import duke.core.DukeException;
import duke.core.CommandManager;
import duke.core.ShortCutter;
import duke.patient.PatientManager;
import duke.statistic.Counter;
import duke.storage.CounterStorage;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.TaskManager;
import duke.core.Ui;

/**
 * Represents Duke, a Personal Assistant to help
 * users tracking their progress.
 */
public class Duke {
    /**
     * A Storage object that handles reading tasks from a local
     * file and saving them to the same file.
     */
    private TaskStorage taskStorage;
    private PatientStorage patientStorage;
    private PatientTaskStorage patientTaskStorage;
    private CounterStorage counterStorage;
    /**
     * A TaskList object that deals with add, delete, mark as done,
     * find functions of a list of tasks.
     */
    private PatientTaskList patientTaskList;
    private TaskManager taskManager;
    private PatientManager patientManager;
    private Counter counter;
    private ShortCutter shortCutter;

    /**
     * A Ui object that deals with interactions with the user.
     */
    private Ui ui = Ui.getUi();

    /**
     * Constructs a Duke object with a relative file path.
     * Initialize the user interface and reads tasks from the specific text file.
     *
     * @param filePath A string that represents the path of the local file
     *                 used for storing tasks.
     */
    public Duke(String filePath) {
        taskStorage = new TaskStorage(filePath + "/standardTasks.csv");
        patientStorage = new PatientStorage(filePath + "/patients.csv");
        patientTaskStorage = new PatientTaskStorage(filePath + "/patientsTasks.csv");
        counterStorage = new CounterStorage(filePath + "/counter.csv");

        try {
            patientTaskList = new PatientTaskList(patientTaskStorage.load());
            taskManager = new TaskManager(taskStorage.load());
            patientManager = new PatientManager(patientStorage.load());
            counter = new Counter(counterStorage.load());
            shortCutter = new ShortCutter(counter , ui);

        } catch (DukeException e) {
            ui.showLoadingError();
            System.out.println(e.getMessage());
            taskManager = new TaskManager();
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
                if (c instanceof DukeCommand) {
                    Command cmd = shortCutter.runShortCut();
                    cmd.execute(patientTaskList, taskManager, patientManager,
                            ui, patientTaskStorage, taskStorage, patientStorage);
                } else{
                    c.execute(patientTaskList, taskManager, patientManager,
                            ui, patientTaskStorage, taskStorage, patientStorage);
                    counter.runCommandCounter(c, counterStorage, counter);
                    isExit = c.isExit();
                }

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
     * task list to Reminder.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new Duke("./data").run();
    }
}
