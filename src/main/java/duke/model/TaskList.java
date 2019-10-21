package duke.model;

import duke.commons.Messages;
import duke.commons.exceptions.DukeDuplicateTaskException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeTaskNotFoundException;
import duke.model.events.Event;
import duke.model.events.Task;
import duke.model.events.TaskWithDates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskList implements Iterable<Task> {
    private List<Task> list;

    public TaskList() {
        list = new ArrayList<>();
    }

    public Task get(int index) throws IndexOutOfBoundsException {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    /**
     * Returns true if the list contains an equivalent Task as the given argument.
     */
    public boolean contains(Task toCheck) {
        return list.stream().anyMatch(toCheck::isSameTask);
    }

    /**
     * Adds a Task to the list.
     * The Task must not already exist in the list.
     */
    public void add(Task toAdd) throws DukeException {
        if (contains(toAdd)) {
            throw new DukeDuplicateTaskException();
        } else if (hasAnomaly(toAdd)) {
            throw new DukeException(Messages.ANOMALY_FOUND);
        }
        list.add(toAdd);
    }

    /**
     * Checks if task clashes with other tasks.
     */
    private boolean hasAnomaly(Task toAdd) {
        if (toAdd instanceof TaskWithDates) {
            LocalDateTime dateTime = ((TaskWithDates) toAdd).getStartDate();
            if (dateTime != null) {
                for (Task t : getChronoList()) {
                    if (((TaskWithDates) t).getStartDate().isEqual(dateTime)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Replaces the Task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The Task identity of {@code editedTask} must not be the same as another existing Task in the list.
     */
    public void setTask(Task target, Task editedTask) throws DukeException {
        int index = list.indexOf(target);
        if (index == -1) {
            throw new DukeTaskNotFoundException();
        }

        if (!target.isSameTask(editedTask) && contains(editedTask)) {
            throw new DukeDuplicateTaskException();
        }

        list.set(index, editedTask);
    }

    /**
     * Removes the equivalent Task from the list.
     * The Task must exist in the list.
     */
    public void remove(Task toRemove) throws DukeException {
        if (!list.remove(toRemove)) {
            throw new DukeTaskNotFoundException();
        }
    }

    public Task remove(int index) throws IndexOutOfBoundsException {
        return list.remove(index);
    }

    public void setTasks(TaskList replacement) {
        list = replacement.list;
    }

    /**
     * Replaces the contents of this list with {@code Tasks}.
     * {@code Tasks} must not contain duplicate Tasks.
     */
    public void setTasks(List<Task> tasks) throws DukeException {
        if (!tasksAreUnique(tasks)) {
            throw new DukeDuplicateTaskException();
        }
        list = tasks;
    }

    @Override
    public Iterator<Task> iterator() {
        return list.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && list.equals(((TaskList) other).list));
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    /**
     * Returns true if {@code Tasks} contains only unique Tasks.
     */
    private boolean tasksAreUnique(List<Task> tasks) {
        for (int i = 0; i < tasks.size() - 1; i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                if (tasks.get(i).isSameTask(tasks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Task> getFilteredList() {
        return list.stream().filter((Task t) -> (t instanceof TaskWithDates)
                && (((TaskWithDates) t).getStartDate() != null)).collect(Collectors.toList());
    }

    public List<Task> getEventList() {
        return list.stream().filter((Task t) -> (t instanceof Event)).collect(Collectors.toList());
    }

    /**
     * Returns a copy of task with dates sorted in chronological order.
     */
    public List<Task> getChronoList() {
        List<Task> result = new ArrayList<>(getFilteredList());
        result.sort(Comparator.comparing((Task t) -> ((TaskWithDates) t).getStartDate()));
        return result;
    }
}

