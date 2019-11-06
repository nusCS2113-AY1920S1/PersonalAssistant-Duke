package sgtravel.model.lists;

import sgtravel.model.planning.Agenda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a list of Agendas (Things to do in a day) and contains its related accessor methods.
 */
public class AgendaList implements Iterable<Agenda> {

    private List<Agenda> list;

    public AgendaList() {
        list = new ArrayList<>();
    }

    public int size() {
        return list.size();
    }

    public List<Agenda> getList() {
        return list;
    }


    /**
     * Adds a Day to the list.
     * The Task must not already exist in the list.
     */
    public void add(Agenda toAdd) {
        //        if (contains(toAdd)) {
        //            throw new DuplicateTaskException();
        //        } else if (hasAnomaly(toAdd)) {
        //            throw new DukeException(Messages.ANOMALY_FOUND);
        //        }
        list.add(toAdd);
    }

    /**
     * Replaces the Day {@code target} in the list with {@code editedDay}.
     * {@code target} must exist in the list.
     */
    public void setTask(Agenda target, Agenda editedAgenda) {
        int index = list.indexOf(target);
        //        if (index == -1) {
        //            throw new DukeTaskNotFoundException();
        //        }
        //
        //        if (!target.isSameTask(editedTask) && contains(editedTask)) {
        //            throw new DuplicateTaskException();
        //        }
        list.set(index, editedAgenda);
    }

    /**
     * Removes the equivalent Task from the list.
     * The Day must exist in the list.
     */
    public void remove(Agenda toRemove) {
        list.remove(toRemove);

    }

    public void setTasks(AgendaList replacement) {
        list = replacement.list;
    }

    /**
     * Replaces the contents of this list with {@code Days}.
     * {@code Tasks} must not contain duplicate Tasks.
     */
    public void setTasks(List<Agenda> agenda) {
        list = agenda;
    }

    @Override
    public Iterator<Agenda> iterator() {
        return list.listIterator();
    }
}
