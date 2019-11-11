//@@author LongLeCE

package planner.credential.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.logic.exceptions.legacy.ModInvalidTimeException;
import planner.logic.modules.TaskList;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;
import planner.logic.modules.module.ModuleTask;

import java.util.Comparator;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TaskListsTest {

    private TaskLists taskLists;
    private TaskList<Cca> ccas;
    private TaskList<ModuleTask> modules;

    @DisplayName("TaskLists setTask and getModule Test")
    @Test
    public void setTaskShouldSetModulesCorrectly() {
        taskLists = new TaskLists();

        assertNull(taskLists.getModules());
        modules = new TaskList<>();
        modules.add(new ModuleTask("test", null));
        taskLists.setTask(modules);
        assertSame(modules, taskLists.getModules());
    }

    @DisplayName("TaskLists setCca and getCca Test")
    @Test
    public void setCcaShouldSetCcasCorrectly() throws ModInvalidTimeException {
        taskLists = new TaskLists();

        assertNull(taskLists.getCcas());
        ccas = new TaskList<>();
        ccas.add(new Cca("test", "3", "5", "monday"));
        taskLists.setCcas(ccas);
        assertSame(ccas, taskLists.getCcas());
    }

    @DisplayName("TaskLists getAllTasks Test")
    @Test
    public void getAllTasksShouldReturnCombinedTaskList() throws ModInvalidTimeException {
        taskLists = new TaskLists();

        assertNull(taskLists.getModules());
        modules = new TaskList<>();
        modules.add(new ModuleTask("test", null));
        taskLists.setTask(modules);

        assertNull(taskLists.getCcas());
        ccas = new TaskList<>();
        ccas.add(new Cca("test", "3", "5", "monday"));
        taskLists.setCcas(ccas);

        TaskList<TaskWithMultipleWeeklyPeriod> allTasks = taskLists.getAllTasks();
        allTasks.sort(Comparator.comparing(TaskWithMultipleWeeklyPeriod::getName));

        HashSet<TaskWithMultipleWeeklyPeriod> taskSet = new HashSet<>();
        taskSet.addAll(modules);
        taskSet.addAll(ccas);

        TaskList<TaskWithMultipleWeeklyPeriod> combinedTaskList = new TaskList<>(taskSet);
        combinedTaskList.sort(Comparator.comparing(TaskWithMultipleWeeklyPeriod::getName));

        assertIterableEquals(combinedTaskList, allTasks);
    }
}
