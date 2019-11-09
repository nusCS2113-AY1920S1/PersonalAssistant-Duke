package planner.logic.modules;

import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class TaskList<E extends TaskWithMultipleWeeklyPeriod> extends ArrayList<E> {

    public TaskList() {
        super();
    }

    public TaskList(int initialCapacity) {
        super(initialCapacity);
    }

    public TaskList(Collection<? extends E> c) {
        super(c);
    }

    /**
     * Returns list of tasks which have the search
     * keyword included in their name.
     * @param keyword Parsed keyword of the cca to be searched
     * @param exact If true, whole name must match
     * @return Returns the cca list where each cca contains the search keyword
     */
    public TaskList<TaskWithMultipleWeeklyPeriod> find(String keyword, boolean exact) {
        TaskList<TaskWithMultipleWeeklyPeriod> results = new TaskList<>();
        for (TaskWithMultipleWeeklyPeriod task : this) {
            String taskName = task.getName();
            if (!exact && taskName.contains(keyword) || taskName.equals(keyword)) {
                results.add(task);
            }
        }
        return results;
    }

    public TaskList<TaskWithMultipleWeeklyPeriod> findExact(String keyword) {
        return this.find(keyword, true);
    }

    public TaskList<TaskWithMultipleWeeklyPeriod> find(String keyword) {
        return this.find(keyword, false);
    }

    /**
     * Set cca list to some other list.
     * @param tasks new cca list
     */
    public void set(Collection<E> tasks) {
        this.addAll(tasks);
    }

    /**
     * Check whether given task clashes with current tasks.
     * @param task given task
     * @return true if clashes else false
     */
    public boolean clashes(TaskWithMultipleWeeklyPeriod task) {
        for (TaskWithMultipleWeeklyPeriod currentTask: this) {
            if (task.isClashing(currentTask)) {
                return true;
            }
        }
        return false;
    }

    public HashSet<TaskWithMultipleWeeklyPeriod> getSetTasks() {
        return new HashSet<>(this);
    }

    public void setTasks(List<E> tasks) {
        this.addAll(tasks);
    }
}
