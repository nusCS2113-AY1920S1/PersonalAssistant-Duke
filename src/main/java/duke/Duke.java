package duke;

import duke.models.tasks.TaskManager;
import duke.util.mementopattern.Memento;
import duke.util.mementopattern.MementoManager;
import duke.util.mementopattern.MementoParser;
import duke.commands.Command;
import duke.commands.CommandManager;
import duke.exceptions.DukeException;
import duke.util.Ui;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.counter.Counter;
import duke.storages.StorageManager;

/**
 * Represents Duke, a Personal Assistant to help
 * users tracking their progress.
 */
public class Duke {

    /**
     * A Storage object that handles reading tasks from a local
     * file and saving them to the same file.
     */
    private StorageManager storageManager;

    /**
     * A TaskList object that deals with add, delete, mark as done,
     * find functions of a list of tasks.
     */
    private AssignedTaskManager assignedTaskManager;
    private TaskManager taskManager;
    private PatientManager patientManager;
    private Counter counter;
    private Ui ui;

    /**
     * A Ui object that deals with interactions with the user.
     */
    private MementoManager mementoManager;
    private MementoParser mementoParser;

    /**
     * .
     * @return .
     */
    public String getDukeResponses() {
        return ui.getDukeResponses();
    }

    /**
     * .
     */
    public void clearDukeResponses() {
        ui.clearResponses();
    }

    /**
     * .
     *
     * @param userInput .
     */
    public void readUserInputFromGui(String userInput) {
        ui.readUserInputFromGui(userInput);
    }

    /**
     * .
     * @return .
     */
    public PatientManager getPatientManager() {
        return patientManager;
    }

    /**
     * .
     * @return .
     */
    public TaskManager getTaskManager() {
        return taskManager;
    }

    /**
     * .
     * @return .
     */
    public AssignedTaskManager getAssignedTaskManager() {
        return assignedTaskManager;
    }


    /**
     * Constructs a Duke object with a relative file path.
     * Initialize the user interface and reads tasks from the specific text file.
     *
     * @param filePath A string that represents the path of the local file
     *                 used for storing tasks.
     */
    public Duke(String filePath) {
        storageManager = new StorageManager(filePath);
        mementoManager = new MementoManager();
        ui = new Ui();
        try {
            assignedTaskManager = new AssignedTaskManager(storageManager.loadAssignedTasks());
            taskManager = new TaskManager(storageManager.loadTasks());
            patientManager = new PatientManager(storageManager.loadPatients());
            counter = new Counter(storageManager.loadCommandFrequency());

        } catch (DukeException e) {
            ui.showLoadingError();
            System.out.println(e.getMessage());
            taskManager = new TaskManager();
        }
    }

    /**
     * .
     *
     * @param memento .
     */
    public void getDukeStateFromMemento(Memento memento) {
        taskManager = memento.getTaskState();
        assignedTaskManager = memento.getPatientTaskState();
        patientManager = memento.getPatientState();
    }

    /**
     * .
     * .
     *
     * @return .
     */
    public Memento saveDukeStateToMemento() {
        return new Memento(new TaskManager(taskManager.getTaskList()),
            new AssignedTaskManager(assignedTaskManager.getAssignTasks()),
            new PatientManager(patientManager.getPatientList()));
    }

    /**
     * Runs the Duke program.
     * Reads user input until a "bye" message is received.
     */
    public void run() {
        try {
            String fullCommand = ui.readUserInput();
            ui.showLine();
            Command c = CommandManager.manageCommand(fullCommand);
            if (MementoParser.getSaveFlag(c).equals("save")) {
                Memento newMem = saveDukeStateToMemento();
                mementoManager.add(newMem);
            } else if (MementoParser.getSaveFlag(c).equals("pop")) {
                getDukeStateFromMemento(mementoManager.pop());
            }
            c.execute(assignedTaskManager, taskManager, patientManager,
                ui, storageManager);
            counter.runCommandCounter(c, storageManager, counter);
        } catch (DukeException e) {
            ui.clearResponses();
            ui.showError(e.getMessage());
        }
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
