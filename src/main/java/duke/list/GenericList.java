package duke.list;

import duke.task.Task;
import duke.task.TaskList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class GenericList<T> {
    protected List<T> genList;

    /**
     * The constructor method(1) for TaskList.
     */
    public GenericList(List<T> genList) {
        this.genList = genList;
    }

    /**
     * The constructor method(2) for TaskList.
     */
    public GenericList() {
        this.genList = new ArrayList<>();
    }

    /**
     * Adds a task to the {@link GenericList}.
     * @param entry {@link T} to be added to the list
     */
    public void addEntry(T entry) {
        genList.add(entry);
    }

    /**
     * Returns the number of {@link Task}s in the {@link TaskList} so far.
     * @return an integer indicating the size of the list of {@link Task}s stored
     */
    public int size() {
        return genList.size();
    }



    /**
     * Returns the {@link Task} at the position indicated by the taskNb.
     * @param taskNb the position of the {@link Task} requested in the {@link TaskList}
     * @return the requested {@link Task}
     */
    public T getEntry(int taskNb) {
        return genList.get(taskNb);
    }

    /**
     * Returns a list of all the {@link Task}s in the {@link TaskList}.
     * @return  {@link ArrayList} of {@link Task}
     */
    public List<T> getAllEntries() {
        return genList;
    }

    /**
     * Returns the removed {@link Task} from position taskNb in the {@link TaskList}.
     * @param taskNb the position of the {@link Task} to be removed from the {@link TaskList}
     * @return Task the task that was removed
     */
    public T removeEntry(int taskNb) {
        return genList.remove(taskNb);
    }
    public boolean removeEntry(T entry) {
        return genList.remove(entry);
    }

    public boolean isEmpty(){
        return genList.isEmpty();
    }

    public T getEntry(T entry){
        for(T e: genList)
            if(e.equals(entry))
                return e;
        return null;
    }

    public void sort(Comparator comparator){
        genList.sort(comparator);
    }

    public void clearList() {
        genList.clear();
    }

}
