//@@author LongLeCE

package planner.credential.user;

import planner.logic.modules.TaskList;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;
import planner.logic.modules.module.ModuleTask;

import java.lang.reflect.Field;
import java.util.HashSet;

public class TaskLists {

    private TaskList<ModuleTask> modules;
    private TaskList<Cca> ccas;

    public void setTask(TaskList<ModuleTask> modules) {
        this.modules = modules;
    }

    public void setCcas(TaskList<Cca> ccas) {
        this.ccas = ccas;
    }

    public TaskList<ModuleTask> getModules() {
        return modules;
    }

    public TaskList<Cca> getCcas() {
        return ccas;
    }

    /**
     * Get all tasks.
     * @return all tasks from all task lists
     */
    @SuppressWarnings("unchecked")
    public TaskList<TaskWithMultipleWeeklyPeriod> getAllTasks() {
        HashSet<TaskWithMultipleWeeklyPeriod> taskSet = new HashSet<>();
        TaskList<TaskWithMultipleWeeklyPeriod> dummyVar = new TaskList<>();
        try {
            for (Field field : TaskLists.class.getDeclaredFields()) {
                if (dummyVar.getClass().isAssignableFrom(field.get(this).getClass())) {
                    taskSet.addAll((TaskList<TaskWithMultipleWeeklyPeriod>) field.get(this));
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return new TaskList(taskSet);
    }
}
