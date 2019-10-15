package duke.util.commons;

import duke.exceptions.ModInvalidTimeException;
import duke.exceptions.ModInvalidTimePeriodException;
import duke.modules.Task;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleTask;
import duke.util.Storage;

import java.util.ArrayList;
import java.util.List;

public class ModuleTasksList {

    /**
     * Task list where active tasks are stored.
     */
    private List<ModuleInfoDetailed> tasks;

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
    public List<ModuleInfoDetailed> find(String input) {
        List<ModuleInfoDetailed> temp = new ArrayList<>();
        for (ModuleInfoDetailed hold : tasks) {
            if (hold.getModuleCode().contains(input)) {
                temp.add(hold);
            }
        }
        return temp;
    }

    public void setTasks(List<ModuleInfoDetailed> tasks) {
        this.tasks = tasks;
    }

    public List<ModuleInfoDetailed> getTasks() {
        return tasks;
    }

    public void delete(int index) {
        tasks.remove(index);
    }

    public int getSize() {
        return tasks.size();
    }

}
