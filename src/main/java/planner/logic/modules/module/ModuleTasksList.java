package planner.logic.modules.module;

import java.util.ArrayList;
import java.util.List;

public class ModuleTasksList {

    /**
     * Task list where active tasks are stored.
     */
    private List<ModuleTask> tasks;

    /**
     * Constructor for TaskList class.
     */
    public ModuleTasksList() {
        tasks = new ArrayList<>();
    }

    /**
     * Returns list of tasks which have the search
     * keyword included in their task name.
     * @param input Parsed keyword of the task name to be searched.
     * @return Returns the taskList where each task contains the search keyword.
     */
    public List<ModuleTask> find(String input) {
        List<ModuleTask> temp = new ArrayList<>();
        for (ModuleTask hold : tasks) {
            if (hold.getTask().contains(input)) {
                temp.add(hold);
            }
        }
        return temp;
    }

    public void setTasks(List<ModuleTask> tasks) {
        this.tasks = tasks;
    }

    public List<ModuleTask> getTasks() {
        return tasks;
    }

    public void delete(int index) {
        tasks.remove(index);
    }

    public void clear() {
        tasks.clear();
    }

    public int getSize() {
        return tasks.size();
    }

}
