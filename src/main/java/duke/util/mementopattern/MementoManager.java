//@@author WEIFENG-NUSCEG

package duke.util.mementopattern;

import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.patients.PatientManager;
import duke.models.tasks.TaskManager;

import java.util.LinkedList;

/**
 * This class represents a list of memento objects to be saved base on the sequence of occurrence,
 * so that when an undo command is received, the MementoManger is able to pop the correct internal state
 * to restore.
 */
public class MementoManager {
    private LinkedList<Memento> mementos = new LinkedList<>();

    /**
     * Add a new memento object to the list.
     * @Param state of the current internal state.
     */
    public void add(Memento state) {
        if (mementos.size() < 10) {
            mementos.addLast(state);
        } else {
            mementos.pollFirst();
            mementos.addLast(state);
        }
    }



    /**
     * Pop the correct saved internal state from the list.
     * @return a Memento object.
     */
    public Memento pop() throws DukeException {
        if (!mementos.isEmpty()) {
            return mementos.pollLast();
        } else {
            throw new DukeException(MementoManager.class, "There are no more steps to undo!");
        }
    }

    /**
     * Save the current internal states into a new memento object.
     *
     * @param taskManager  a task manager to be saved
     * @param assignedTaskManager a assigned task manager to be saved
     * @param patientManager a patient manager to be saved
     * @return a new Memento object
     */
    public Memento saveDukeStateToMemento(TaskManager taskManager, AssignedTaskManager assignedTaskManager,
                                          PatientManager patientManager) {
        return new Memento(new TaskManager(taskManager.getTaskList()),
                new AssignedTaskManager(assignedTaskManager.getAssignTasks()),
                new PatientManager(patientManager.getPatientList()));
    }


}
