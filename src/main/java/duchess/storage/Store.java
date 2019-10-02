package duchess.storage;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import duchess.model.task.Task;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Task> taskList;
    private List<Module> moduleList;

    public Store() {
        this.taskList = new ArrayList<>();
        this.moduleList = new ArrayList<>();
    }

    public boolean isClashing(Task newTask) {
        return this.taskList.stream()
                .anyMatch(task -> task.clashesWith(newTask));
    }

    @JsonGetter("taskList")
    public List<Task> getTaskList() {
        return taskList;
    }

    @JsonSetter("taskList")
    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @JsonGetter("moduleList")
    public List<Module> getModuleList() {
        return moduleList;
    }

    @JsonSetter("moduleList")
    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }
}
