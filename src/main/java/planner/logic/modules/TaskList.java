package planner.logic.modules;

import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * @param keyword Parsed keyword of the cca to be searched.
     * @return Returns the cca list where each cca contains the search keyword.
     */
    public TaskList find(String keyword) {
        TaskList<TaskWithMultipleWeeklyPeriod> results = new TaskList<>();
        String[] keywordSplit = keyword.trim().split(" +");
        Set<String> ccaNameSplit;
        boolean match;
        for (TaskWithMultipleWeeklyPeriod task : this) {
            match = true;
            ccaNameSplit = new HashSet<>(Arrays.asList(task.getTask().trim().split(" +")));
            for (String keywordPart: keywordSplit) {
                if (!ccaNameSplit.contains(keywordPart)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                results.add(task);
            }
        }
        return results;
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

    public HashSet<TaskWithMultipleWeeklyPeriod> getSetModuleTask() {
        return new HashSet<>(this);
    }

    public void setTasks(List<E> tasks) {
        this.addAll(tasks);
    }
}
