package duke.model.lists;

import duke.commons.exceptions.DukeException;
import duke.model.planning.Day;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DayList implements Iterable<Day> {

    private List<Day> list;

    public DayList() {
        list = new ArrayList<>();
    }

    public int size() {
        return list.size();
    }

    public List<Day> getList(){
        return list;
    }


    /**
     * Adds a Day to the list.
     * The Task must not already exist in the list.
     */
    public void add(Day toAdd) throws DukeException {
//        if (contains(toAdd)) {
//            throw new DukeDuplicateTaskException();
//        } else if (hasAnomaly(toAdd)) {
//            throw new DukeException(Messages.ANOMALY_FOUND);
//        }
        list.add(toAdd);
    }

    /**
     * Replaces the Day {@code target} in the list with {@code editedDay}.
     * {@code target} must exist in the list.
     */
    public void setTask(Day target, Day editedDay) throws DukeException {
        int index = list.indexOf(target);
//        if (index == -1) {
//            throw new DukeTaskNotFoundException();
//        }
//
//        if (!target.isSameTask(editedTask) && contains(editedTask)) {
//            throw new DukeDuplicateTaskException();
//        }
        list.set(index, editedDay);
    }

    /**
     * Removes the equivalent Task from the list.
     * The Day must exist in the list.
     */
    public void remove(Day toRemove) {
        list.remove(toRemove);

    }

    public void setTasks(DayList replacement) {
        list = replacement.list;
    }

    /**
     * Replaces the contents of this list with {@code Days}.
     * {@code Tasks} must not contain duplicate Tasks.
     */
    public void setTasks(List<Day> days) throws DukeException {
        list = days;
    }

    @Override
    public Iterator<Day> iterator() {
        return list.listIterator();
    }
}
