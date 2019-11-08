//@@author LongLeCE

package planner.credential.user;

import planner.logic.modules.TaskList;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.module.ModuleTask;

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
}
