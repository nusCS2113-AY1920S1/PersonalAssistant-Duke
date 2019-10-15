package spinbox.lists;

import spinbox.DateTime;
import spinbox.items.tasks.Schedulable;
import spinbox.items.tasks.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskList extends SpinBoxList<Task> {
    public TaskList() {
        super();
    }

    public TaskList(List<Task> tasks) {
        super(tasks);
    }

    /**
     * Mark the task at index as done.
     * @param index index of element to be marked.
     * @return task that was marked as done.
     * @throws IndexOutOfBoundsException if index is invalid.
     */
    public Task mark(int index) throws IndexOutOfBoundsException {
        list.get(index).markDone();
        return list.get(index);
    }

    static class StartDateComparator implements Comparator<Task> {
        @Override
        public int compare(Task a, Task b) {
            DateTime startDateA = null;
            DateTime startDateB = null;

            if (a.isSchedulable()) {
                startDateA = ((Schedulable)a).getStartDate();
            }
            if (b.isSchedulable()) {
                startDateB = ((Schedulable)b).getStartDate();
            }

            if (startDateA == null && startDateB == null) {
                return 0;
            } else if (startDateA == null) {
                return 1;
            } else if (startDateB == null) {
                return -1;
            } else {
                return startDateA.compareTo(startDateB);
            }
        }
    }

    public void sort() {
        Collections.sort(list, new StartDateComparator());
    }
}
