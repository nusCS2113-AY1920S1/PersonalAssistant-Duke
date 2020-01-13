//@@author HUANGXUANKUN

package duke;

import duke.commands.Command;
import duke.commands.CommandManager;
import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.counter.Counter;
import duke.models.patients.Patient;
import duke.models.patients.PatientManager;
import duke.models.tasks.Task;
import duke.models.tasks.TaskManager;
import duke.storages.StorageManager;
import duke.util.DukeUi;
import duke.util.StartUpData;
import duke.util.mementopattern.Memento;
import duke.util.mementopattern.MementoManager;
import duke.util.mementopattern.MementoParser;

/**
 * Represents Duke, a Personal Assistant to help
 * users tracking their progress.
 */
public class Duke {
    private StorageManager storageManager;
    private AssignedTaskManager assignedTaskManager;
    private TaskManager taskManager;
    private PatientManager patientManager;
    private MementoManager mementoManager;
    private Counter counter;
    private DukeUi dukeUi;
    private Command command;

    /**
     * Constructs a Duke object with a relative file path.
     * Initialize the user interface and reads tasks from the specific text file.
     *
     * @param filePath A string that represents the path of the local file
     *                 used for storing tasks.
     */
    public Duke(String filePath) throws DukeException {
        storageManager = new StorageManager(filePath);
        mementoManager = new MementoManager();
        dukeUi = new DukeUi();

        try {
            assignedTaskManager = new AssignedTaskManager(storageManager.loadAssignedTasks());
            taskManager = new TaskManager(storageManager.loadTasks());
            patientManager = new PatientManager(storageManager.loadPatients());
            counter = new Counter(storageManager.loadCommandFrequency());

        } catch (DukeException e) {
            dukeUi.showLoadingError();
            System.out.println(e.getMessage());
            taskManager = new TaskManager();
        }

        if ((assignedTaskManager.getAssignTasks().size() <= 0)
            && (taskManager.getTaskList().size() <= 0)
            && (patientManager.getPatientList().size() <= 0)) {
            try {
                StartUpData dataAdder = new StartUpData();
                for (Patient patient : dataAdder.getPatients()) {
                    patientManager.addPatient(patient);
                }
                for (Task task : dataAdder.getTasks()) {
                    taskManager.addTask(task);
                }
                for (AssignedTask assignedTask : dataAdder.getAssignedTasks()) {
                    assignedTaskManager.addPatientTask(assignedTask);
                }
                storageManager.savePatients(patientManager.getPatientList());
                storageManager.saveTasks(taskManager.getTaskList());
                storageManager.saveAssignedTasks(assignedTaskManager.getAssignTasks());
            } catch (Exception e) {
                throw new DukeException("Failed to generate pre-made data.");
            }
        }
    }

    /**
     * Runs the Duke program.
     * Reads user input until a "bye" message is received.
     *
     * @param userInput Full command entered by user
     * @return Duke's reply to the user entered command
     * @throws DukeException throw when execution of the command fails
     */
    public String run(String userInput) throws DukeException {
        try {
            dukeUi.readUserInputFromGui(userInput);
            dukeUi.showLine();
            command = CommandManager.manageCommand(userInput);
            if (MementoParser.getSaveFlag(command).equals("save")) {
                Memento newMem = mementoManager.saveDukeStateToMemento(taskManager, assignedTaskManager,
                    patientManager);
                command.execute(assignedTaskManager, taskManager, patientManager,
                    dukeUi, storageManager);
                mementoManager.add(newMem);
            } else if (MementoParser.getSaveFlag(command).equals("pop")) {
                Memento newMem = mementoManager.pop();
                this.assignedTaskManager = newMem.getPatientTaskState();
                this.taskManager = newMem.getTaskState();
                this.patientManager = newMem.getPatientState();
                command.execute(assignedTaskManager, taskManager, patientManager,
                    dukeUi, storageManager);
            } else {
                command.execute(assignedTaskManager, taskManager, patientManager,
                    dukeUi, storageManager);
            }
            counter.runCommandCounter(command, storageManager, counter);
            return dukeUi.getDukeResponses();
        } catch (DukeException e) {
            dukeUi.clearResponses();
            throw e;
        }
    }

    /**
     * Return the current running command.
     */
    public Command getRunningCommand() {
        return command;
    }

    /**
     * It clears all duke's dialog.
     */
    public void clearDukeResponses() {
        dukeUi.clearResponses();
    }

    /**
     * It returns manager of all patient models.
     *
     * @return An manager which manipulates all patient models.
     */
    public PatientManager getPatientManager() {
        return patientManager;
    }

    /**
     * It returns manager of all task models.
     *
     * @return An manager which manipulates all task models.
     */
    public TaskManager getTaskManager() {
        return taskManager;
    }

    /**
     * It returns manager of all storage.
     *
     * @return An manager which manipulates all storage.
     */
    public StorageManager getStorageManager() {
        return storageManager;
    }

    /**
     * It returns manager of all assignedTask models.
     *
     * @return An manager which manipulates all assignedTask models.
     */
    public AssignedTaskManager getAssignedTaskManager() {
        return assignedTaskManager;
    }

}
