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
    private StorageManager storageManager;
    private AssignedTaskManager assignedTaskManager;
    private TaskManager taskManager;
    private PatientManager patientManager;
    private Counter counter;
    private Ui ui;
    private MementoManager mementoManager;

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
     * Runs the Duke program.
     * Reads user input until a "bye" message is received.
     */
    public String run(String userInput) throws DukeException {
        try {
            ui.readUserInputFromGui(userInput);
            ui.showLine();
            Command c = CommandManager.manageCommand(userInput);
            if (MementoParser.getSaveFlag(c).equals("save")) {
                Memento newMem = saveDukeStateToMemento();
                mementoManager.add(newMem);
            } else if (MementoParser.getSaveFlag(c).equals("pop")) {
                getDukeStateFromMemento(mementoManager.pop());
            }
            c.execute(assignedTaskManager, taskManager, patientManager,
                ui, storageManager);
            counter.runCommandCounter(c, storageManager, counter);
            return ui.getDukeResponses();
        } catch (DukeException e) {
            ui.clearResponses();
            throw e;
        }
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
     * @return .
     */
    public PatientManager getPatientManager() {
        return patientManager;
    }

    /**
     * .
     *
     * @return .
     */
    public TaskManager getTaskManager() {
        return taskManager;
    }

    /**
     * .
     *
     * @return .
     */
    public AssignedTaskManager getAssignedTaskManager() {
        return assignedTaskManager;
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
     *
     * @return .
     */
    public Memento saveDukeStateToMemento() {
        return new Memento(new TaskManager(taskManager.getTaskList()),
            new AssignedTaskManager(assignedTaskManager.getAssignTasks()),
            new PatientManager(patientManager.getPatientList()));
    }
}
