package duke.tasks;

import duke.commons.DukeException;
import duke.commons.DuplicateTaskException;
import duke.commons.TaskNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

/**
 * A list of Tasks that enforces uniqueness between its elements and does not allow nulls.
 * A Task is considered unique by comparing using {@code Task#isSameTask(Task)}. As such, adding and updating of
 * Tasks uses Task#isSameTask(Task) for equality so as to ensure that the Task being added or updated is
 * unique in terms of identity in the UniqueTaskList. However, the removal of a Task uses Task#equals(Object) so
 * as to ensure that the Task with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#isSameTask(Task)
 */
public class UniqueTaskList implements Iterable<Task> {
    private ObservableList<Task> internalList;

    public UniqueTaskList() {
        internalList = FXCollections.observableArrayList();
    }

    public Task get(int index) throws IndexOutOfBoundsException {
        return internalList.get(index);
    }

    /**
     * Returns true if the list contains an equivalent Task as the given argument.
     */
    public boolean contains(Task toCheck) {
        return internalList.stream().anyMatch(toCheck::isSameTask);
    }

    /**
     * Adds a Task to the list.
     * The Task must not already exist in the list.
     */
    public void add(Task toAdd) throws DukeException {
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The Task identity of {@code editedTask} must not be the same as another existing Task in the list.
     */
    public void setTask(Task target, Task editedTask) throws DukeException {
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.isSameTask(editedTask) && contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, editedTask);
    }

    /**
     * Removes the equivalent Task from the list.
     * The Task must exist in the list.
     */
    public void remove(Task toRemove) throws DukeException {
        if (!internalList.remove(toRemove)) {
            throw new TaskNotFoundException();
        }
    }

    public Task remove(int index) throws IndexOutOfBoundsException {
        return internalList.remove(index);
    }

    public void setTasks(UniqueTaskList replacement) {
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Tasks}.
     * {@code Tasks} must not contain duplicate Tasks.
     */
    public void setTasks(List<Task> Tasks) throws DukeException {
        if (!TasksAreUnique(Tasks)) {
            throw new DuplicateTaskException();
        }

        internalList.setAll(Tasks);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Tasks} contains only unique Tasks.
     */
    private boolean TasksAreUnique(List<Task> Tasks) {
        for (int i = 0; i < Tasks.size() - 1; i++) {
            for (int j = i + 1; j < Tasks.size(); j++) {
                if (Tasks.get(i).isSameTask(Tasks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
