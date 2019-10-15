package duchess.storage;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import duchess.model.Module;
import duchess.model.calendar.CalendarManager;
import duchess.model.task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Store {
    private List<Task> taskList;
    private List<Module> moduleList;
    private CalendarManager calendarManager;

    /**
     * Initialises new taskList, moduleList and duchessCalendar.
     */
    public Store() {
        this.taskList = new ArrayList<>();
        this.moduleList = new ArrayList<>();
        this.calendarManager = new CalendarManager();
    }

    public boolean isClashing(Task newTask) {
        return this.taskList.stream()
                .anyMatch(task -> task.clashesWith(newTask));
    }

    public Optional<Module> findModuleByCode(String code) {
        return moduleList.stream().filter(module -> module.isOfCode(code)).findFirst();
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

    public void addToCalendar(Task task) {
        calendarManager.addEntry(task);
    }

    public void deleteFromCalendar(Task task) {
        calendarManager.deleteEntry(task);
    }
}
