package integration;

import org.junit.jupiter.api.Test;
import spinbox.DateTime;
import spinbox.Parser;
import spinbox.Ui;
import spinbox.commands.Command;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.TaskList;
import spinbox.entities.Module;
import spinbox.entities.items.tasks.Deadline;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Exam;
import spinbox.entities.items.tasks.Lab;
import spinbox.entities.items.tasks.Lecture;
import spinbox.entities.items.tasks.Task;
import spinbox.entities.items.tasks.Todo;
import spinbox.entities.items.tasks.Tutorial;

import spinbox.exceptions.InvalidIndexException;
import spinbox.exceptions.StorageException;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskCommandsIntegrationTest {
    @Test
    public void loadDataSuccessful_AddTasksThenManualClear_successfulRepopulationOfData() throws
            StorageException, InvalidIndexException {
        Module testModuleOne = new Module("testMod6", "Engineering Principles & Practice IV");

        TaskList taskList = testModuleOne.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Todo("Test 1");
        Task testTask2 = new Deadline("Test 2", new DateTime("01/01/2019 10:00"));
        Task testTask3 = new Event("Test 3", new DateTime("01/02/2019 10:00"),
                new DateTime("01/02/2019 12:00"));
        Task testTask4 = new Exam("Test 4", new DateTime("01/03/2019 10:00"),
                new DateTime("01/03/2019 12:00"));
        Task testTask5 = new Lab("Test 5", new DateTime("01/04/2019 10:00"),
                new DateTime("01/04/2019 12:00"));
        Task testTask6 = new Lecture("Test 6", new DateTime("01/05/2019 10:00"),
                new DateTime("01/05/2019 12:00"));
        Task testTask7 = new Tutorial("Test 7", new DateTime("01/06/2019 10:00"),
                new DateTime("01/06/2019 12:00"));


        taskList.add(testTask1);
        taskList.add(testTask2);
        taskList.add(testTask3);
        taskList.add(testTask4);
        taskList.add(testTask5);
        taskList.add(testTask6);
        taskList.add(testTask7);
        taskList.getList().clear();
        taskList.loadData();

        assertEquals(taskList.get(0).toString(), "[D][NOT DONE] Test 2 (by: 01/01/2019 10:00)");
        assertEquals(taskList.get(1).toString(), "[E][NOT DONE] Test 3 (at: 01/02/2019 10:00"
                + " to 01/02/2019 12:00)");
        assertEquals(taskList.get(2).toString(), "[EXAM][NOT DONE] Test 4 (at: 01/03/2019 10:00"
                + " to 01/03/2019 12:00)");
        assertEquals(taskList.get(3).toString(), "[LAB][NOT DONE] Test 5 (at: 01/04/2019 10:00"
                + " to 01/04/2019 12:00)");
        assertEquals(taskList.get(4).toString(), "[LEC][NOT DONE] Test 6 (at: 01/05/2019 10:00"
                + " to 01/05/2019 12:00)");
        assertEquals(taskList.get(5).toString(), "[TUT][NOT DONE] Test 7 (at: 01/06/2019 10:00"
                + " to 01/06/2019 12:00)");
        assertEquals(taskList.get(6).toString(), "[T][NOT DONE] Test 1");
    }

    @Test
    public void setNameSuccessful_setNameOfTaskToANewName_taskNameSuccessfullySet() throws
            SpinBoxException {
        ModuleContainer testContainer = new ModuleContainer();
        Module testModule = new Module("CG1112", "Engineering Principles & Practice III");
        testContainer.addModule(testModule);

        TaskList taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Todo("Test 1");
        Task testTask2 = new Lab("Test 2", new DateTime("01/04/2019 10:00"),
                new DateTime("01/04/2019 12:00"));

        taskList.add(testTask1);
        taskList.add(testTask2);

        ArrayDeque<String> pageTrace = new ArrayDeque<>();
        Ui ui = new Ui(true);

        String setNameForTask1 = "set-name CG1112 / task 1 to: Lab";
        Parser.setPageTrace(pageTrace);
        Command command = Parser.parse(setNameForTask1);
        command.execute(testContainer, pageTrace, ui, false);

        String setNameForTask2 = "set-name CG1112 / task 2 to: Todo";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setNameForTask2);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(taskList.get(0).toString(), "[LAB][NOT DONE] Lab (at: 01/04/2019 10:00"
                + " to 01/04/2019 12:00)");
        assertEquals(taskList.get(1).toString(), "[T][NOT DONE] Todo");
    }

    @Test
    public void setDateSuccessful_setDateOfSchedulableTaskToANewDate_taskDateSuccessfullySet() throws
            SpinBoxException {
        ModuleContainer testContainer = new ModuleContainer();
        Module testModule = new Module("CG1112", "Engineering Principles & Practice III");
        testContainer.addModule(testModule);

        TaskList taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Deadline("Test 1", new DateTime("01/01/2019 10:00"));
        Task testTask2 = new Exam("Test 2", new DateTime("12/12/2019 10:00"),
                new DateTime("01/03/2019 12:00"));

        taskList.add(testTask1);
        taskList.add(testTask2);

        ArrayDeque<String> pageTrace = new ArrayDeque<>();
        Ui ui = new Ui(true);

        String setDateForTask1 = "set-date CG1112 / task 1 to: 02/01/2019 12:00";
        Parser.setPageTrace(pageTrace);
        Command command = Parser.parse(setDateForTask1);
        command.execute(testContainer, pageTrace, ui, false);

        String setDateForTask2 = "set-date CG1112 / task 2 to: 02/05/2019 16:00 to 02/05/2019 19:00";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setDateForTask2);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(taskList.get(0).toString(), "[D][NOT DONE] Test 1 (by: 02/01/2019 12:00)");
        assertEquals(taskList.get(1).toString(), "[EXAM][NOT DONE] Test 2 (at: 02/05/2019 16:00"
                + " to 02/05/2019 19:00)");
    }
}