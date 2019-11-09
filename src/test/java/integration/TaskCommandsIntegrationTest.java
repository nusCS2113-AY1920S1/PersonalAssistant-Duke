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

import spinbox.exceptions.InputException;
import spinbox.exceptions.InvalidIndexException;
import spinbox.exceptions.StorageException;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.ScheduleDateException;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskCommandsIntegrationTest {
    private ModuleContainer testContainer;
    private Module testModule;
    private TaskList taskList;
    private ArrayDeque<String> pageTrace;
    private Command command;
    private Ui ui;
  
    @Test
    public void loadDataSuccessful_AddTasksThenManualClear_successfulRepopulationOfData() throws
            StorageException, InvalidIndexException, ScheduleDateException {
        Module testModuleOne = new Module("testMod6", "Engineering Principles & Practice IV");

        taskList = testModuleOne.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Todo("Test 1");
        Task testTask2 = new Deadline("Test 2", new DateTime("01/01/2029 10:00"));
        Task testTask3 = new Event("Test 3", new DateTime("01/02/2029 10:00"),
                new DateTime("01/02/2029 12:00"));
        Task testTask4 = new Exam("Test 4", new DateTime("01/03/2029 10:00"),
                new DateTime("01/03/2029 12:00"));
        Task testTask5 = new Lab("Test 5", new DateTime("01/04/2029 10:00"),
                new DateTime("01/04/2029 12:00"));
        Task testTask6 = new Lecture("Test 6", new DateTime("01/05/2029 10:00"),
                new DateTime("01/05/2029 12:00"));
        Task testTask7 = new Tutorial("Test 7", new DateTime("01/06/2029 10:00"),
                new DateTime("01/06/2029 12:00"));

        taskList.add(testTask1);
        taskList.add(testTask2);
        taskList.add(testTask3);
        taskList.add(testTask4);
        taskList.add(testTask5);
        taskList.add(testTask6);
        taskList.add(testTask7);
        taskList.getList().clear();
        taskList.loadData();

        assertEquals(taskList.get(0).toString(), "[D][NOT DONE] Test 2 (by: 01/01/2029 10:00)");
        assertEquals(taskList.get(1).toString(), "[E][NOT DONE] Test 3 (at: 01/02/2029 10:00"
                + " to 01/02/2029 12:00)");
        assertEquals(taskList.get(2).toString(), "[EXAM][NOT DONE] Test 4 (at: 01/03/2029 10:00"
                + " to 01/03/2029 12:00)");
        assertEquals(taskList.get(3).toString(), "[LAB][NOT DONE] Test 5 (at: 01/04/2029 10:00"
                + " to 01/04/2029 12:00)");
        assertEquals(taskList.get(4).toString(), "[LEC][NOT DONE] Test 6 (at: 01/05/2029 10:00"
                + " to 01/05/2029 12:00)");
        assertEquals(taskList.get(5).toString(), "[TUT][NOT DONE] Test 7 (at: 01/06/2029 10:00"
                + " to 01/06/2029 12:00)");
        assertEquals(taskList.get(6).toString(), "[T][NOT DONE] Test 1");
    }

    @Test
    public void setNameSuccessful_setNameOfTaskToANewName_taskNameSuccessfullySet() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Todo("Test 1");
        Task testTask2 = new Lab("Test 2", new DateTime("01/04/2029 10:00"),
                new DateTime("01/04/2029 12:00"));
        Task testTask3 = new Tutorial("Test 3", new DateTime("01/05/2030 10:00"),
                new DateTime("01/05/2030 12:00"));
        Task testTask4 = new Event("Test 4", new DateTime("01/05/2031 10:00"),
                new DateTime("01/05/2031 12:00"));
        Task testTask5 = new Exam("Test 5", new DateTime("01/03/2032 10:00"),
                new DateTime("01/03/2032 12:00"));

        taskList.add(testTask1);
        taskList.add(testTask2);
        taskList.add(testTask3);
        taskList.add(testTask4);
        taskList.add(testTask5);

        pageTrace = new ArrayDeque<>();
        pageTrace.add("main");
        ui = new Ui(true);

        String setNameForTask1 = "set-name TESTMOD / task 1 to: Lab";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setNameForTask1);
        command.execute(testContainer, pageTrace, ui, false);

        String setNameForTask3 = "set-name TESTMOD / task 2 to: Tutorial";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setNameForTask3);
        command.execute(testContainer, pageTrace, ui, false);

        String setNameForTask4 = "set-name TESTMOD / task 3 to: Event";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setNameForTask4);
        command.execute(testContainer, pageTrace, ui, false);

        String setNameForTask5 = "set-name TESTMOD / task 4 to: Exam";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setNameForTask5);
        command.execute(testContainer, pageTrace, ui, false);

        String setNameForTask2 = "set-name TESTMOD / task 5 to: Todo";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setNameForTask2);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(taskList.get(0).toString(), "[LAB][NOT DONE] Lab (at: 01/04/2029 10:00"
                + " to 01/04/2029 12:00)");
        assertEquals(taskList.get(1).toString(), "[TUT][NOT DONE] Tutorial (at: 01/05/2030 10:00"
                + " to 01/05/2030 12:00)");
        assertEquals(taskList.get(2).toString(), "[E][NOT DONE] Event (at: 01/05/2031 10:00"
                + " to 01/05/2031 12:00)");
        assertEquals(taskList.get(3).toString(), "[EXAM][NOT DONE] Exam (at: 01/03/2032 10:00"
                + " to 01/03/2032 12:00)");
        assertEquals(taskList.get(4).toString(), "[T][NOT DONE] Todo");
        testContainer.removeModule(testModule.getModuleCode(),testModule);
    }

    @Test
    public void setNameUnsuccessful_invalidIndexUsed_exceptionThrown() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Todo("Test 1");

        taskList.add(testTask1);

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        try {
            String setNameForTask1 = "set-name TESTMOD / task 3 to: Lab";
            Parser.setPageTrace(pageTrace);
            command = Parser.parse(setNameForTask1);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InvalidIndexException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nYou have entered an invalid index.", e.getMessage());
        }
    }

    @Test
    public void setDateSuccessful_setDateOfSchedulableTaskToANewDate_taskDateSuccessfullySet() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Deadline("Test 1", new DateTime("01/01/2029 10:00"));
        Task testTask2 = new Exam("Test 2", new DateTime("12/12/2029 10:00"),
                new DateTime("01/03/2030 12:00"));
        Task testTask3 = new Lab("Test 3", new DateTime("01/01/2030 10:00"),
                new DateTime("01/01/2030 12:00"));
        Task testTask4 = new Tutorial("Test 4", new DateTime("01/01/2031 10:00"),
                new DateTime("01/01/2031 12:00"));

        taskList.add(testTask1);
        taskList.add(testTask2);
        taskList.add(testTask3);
        taskList.add(testTask4);
  
        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        String setDateForTask1 = "set-date TESTMOD / task 1 to: 02/01/2029 12:00";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setDateForTask1);
        command.execute(testContainer, pageTrace, ui, false);

        String setDateForTask2 = "set-date TESTMOD / task 2 to: 02/05/2029 16:00 to 02/05/2029 19:00";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setDateForTask2);
        command.execute(testContainer, pageTrace, ui, false);

        String setDateForTask3 = "set-date TESTMOD / task 3 to: 03/02/2030 16:00 to 03/02/2030 19:00";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setDateForTask3);
        command.execute(testContainer, pageTrace, ui, false);

        String setDateForTask4 = "set-date TESTMOD / task 4 to: 01/02/2031 16:00 to 01/02/2031 19:00";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setDateForTask4);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(taskList.get(0).toString(), "[D][NOT DONE] Test 1 (by: 02/01/2029 12:00)");
        assertEquals(taskList.get(1).toString(), "[EXAM][NOT DONE] Test 2 (at: 02/05/2029 16:00"
                + " to 02/05/2029 19:00)");
        assertEquals(taskList.get(2).toString(), "[LAB][NOT DONE] Test 3 (at: 03/02/2030 16:00 "
                + "to 03/02/2030 19:00)");
        assertEquals(taskList.get(3).toString(), "[TUT][NOT DONE] Test 4 (at: 01/02/2031 16:00 "
                + "to 01/02/2031 19:00)");
        testContainer.removeModule(testModule.getModuleCode(),testModule);
    }
      
    @Test
    public void setDateUnsuccessful_invalidIndexUsed_exceptionThrown() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Todo("Test 1");

        taskList.add(testTask1);

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        try {
            String setDateForTask1 = "set-date TESTMOD / task 2 to: 01/03/2029 15:00 to 01/03/2029 19:00";
            Parser.setPageTrace(pageTrace);
            command = Parser.parse(setDateForTask1);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InvalidIndexException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nYou have entered an invalid index.", e.getMessage());
        }
    }

    @Test
    public void setDateUnsuccessful_setDateUsedOnNonSchedulableItems_exceptionThrown() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Todo("Test 1");

        taskList.add(testTask1);

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        try {
            String setDateForTask1 = "set-date TESTMOD / task 1 to: 01/03/2029 15:00 to 01/03/2029 19:00";
            Parser.setPageTrace(pageTrace);
            command = Parser.parse(setDateForTask1);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InputException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nSorry, set-date is not available for a To-Do task.\n"
                    + "Set-date is only available for a Deadline/Event/Exam/Lab/Lecture/Tutorial.", e.getMessage());
        }
    }

    @Test
    public void removeTasksSuccessful_removeOneSingleTaskAndMultipleTasks_tasksSuccessfullyRemoved() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Deadline("Test 1", new DateTime("01/01/2029 10:00"));
        Task testTask2 = new Event("Test 2", new DateTime("01/02/2029 10:00"),
                new DateTime("01/02/2029 12:00"));
        Task testTask3 = new Exam("Test 3", new DateTime("01/03/2029 10:00"),
                new DateTime("01/03/2029 12:00"));
        Task testTask4 = new Lab("Test 4", new DateTime("01/04/2029 10:00"),
                new DateTime("01/04/2029 12:00"));
        Task testTask5 = new Lecture("Test 5", new DateTime("01/05/2029 10:00"),
                new DateTime("01/05/2029 12:00"));
        Task testTask6 = new Tutorial("Test 6", new DateTime("01/06/2029 10:00"),
                new DateTime("01/06/2029 12:00"));

        taskList.add(testTask1);
        taskList.add(testTask2);
        taskList.add(testTask3);
        taskList.add(testTask4);
        taskList.add(testTask5);
        taskList.add(testTask6);

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        String removeOneTask = "remove TESTMOD / task 1";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(removeOneTask);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(taskList.size(), 5);

        String removeMultipleTasks = "remove-* TESTMOD / task 1,2,3,4,5";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(removeMultipleTasks);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(taskList.size(), 0);
        testContainer.removeModule(testModule.getModuleCode(),testModule);
    }

    @Test
    public void removeMultipleTasksUnsuccessful_onlyOneIndexProvided_exceptionThrown() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Event("Test 1", new DateTime("01/02/2029 10:00"),
                new DateTime("01/02/2029 12:00"));
        Task testTask2 = new Lecture("Test 2", new DateTime("01/03/2030 10:00"),
                new DateTime("01/03/2030 12:00"));

        taskList.add(testTask1);
        taskList.add(testTask2);

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        try {
            String updateTask = "remove-* TESTMOD / task 1";
            Parser.setPageTrace(pageTrace);
            command = Parser.parse(updateTask);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InputException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nTo remove a single task, provide the input in this "
                    + "format instead: remove <pageContent> / <type> <one index in integer form>", e.getMessage());
        }
    }

    @Test
    public void updateTasksSuccessful_updateOneSingleTaskAndMultipleTasks_tasksSuccessfullyUpdated() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Event("Test 1", new DateTime("01/02/2029 10:00"),
                new DateTime("01/02/2029 12:00"));
        Task testTask2 = new Exam("Test 2", new DateTime("01/03/2029 10:00"),
                new DateTime("01/03/2029 12:00"));
        Task testTask3 = new Lab("Test 3", new DateTime("01/04/2029 10:00"),
                new DateTime("01/04/2029 12:00"));
        Task testTask4 = new Lecture("Test 4", new DateTime("01/05/2029 10:00"),
                new DateTime("01/05/2029 12:00"));
        Task testTask5 = new Tutorial("Test 5", new DateTime("01/06/2029 10:00"),
                new DateTime("01/06/2029 12:00"));

        taskList.add(testTask1);
        taskList.add(testTask2);
        taskList.add(testTask3);
        taskList.add(testTask4);
        taskList.add(testTask5);

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        String updateOneTask = "update TESTMOD / task 1 done";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(updateOneTask);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(taskList.get(4).toString(), "[E][DONE] Test 1 (at: 01/02/2029 10:00 to 01/02/2029 12:00)");

        String updateMultipleTasks = "update-* TESTMOD / task 1,2,3,4 done";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(updateMultipleTasks);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(taskList.get(1).toString(), "[EXAM][DONE] Test 2 (at: 01/03/2029 10:00"
                + " to 01/03/2029 12:00)");
        assertEquals(taskList.get(2).toString(), "[LAB][DONE] Test 3 (at: 01/04/2029 10:00"
                + " to 01/04/2029 12:00)");
        assertEquals(taskList.get(3).toString(), "[LEC][DONE] Test 4 (at: 01/05/2029 10:00"
                + " to 01/05/2029 12:00)");
        assertEquals(taskList.get(4).toString(), "[TUT][DONE] Test 5 (at: 01/06/2029 10:00"
                + " to 01/06/2029 12:00)");

        String updateOneTaskNotDone = "update TESTMOD / task 1 notdone";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(updateOneTaskNotDone);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(taskList.get(0).toString(), "[E][NOT DONE] Test 1 (at: 01/02/2029 10:00 to "
                + "01/02/2029 12:00)");

        String updateMultipleTasksNotDone = "update-* TESTMOD / task 2,3,4,5 notdone";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(updateMultipleTasksNotDone);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(taskList.get(1).toString(), "[EXAM][NOT DONE] Test 2 (at: 01/03/2029 10:00"
                + " to 01/03/2029 12:00)");
        assertEquals(taskList.get(2).toString(), "[LAB][NOT DONE] Test 3 (at: 01/04/2029 10:00"
                + " to 01/04/2029 12:00)");
        assertEquals(taskList.get(3).toString(), "[LEC][NOT DONE] Test 4 (at: 01/05/2029 10:00"
                + " to 01/05/2029 12:00)");
        assertEquals(taskList.get(4).toString(), "[TUT][NOT DONE] Test 5 (at: 01/06/2029 10:00"
                + " to 01/06/2029 12:00)");
        testContainer.removeModule(testModule.getModuleCode(),testModule);
    }

    @Test
    public void updateTasksUnsuccessful_noBooleanValueProvided_exceptionThrown() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Event("Test 1", new DateTime("01/02/2029 10:00"),
                new DateTime("01/02/2029 12:00"));

        taskList.add(testTask1);

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        try {
            String updateTask = "update TESTMOD / task 1";
            Parser.setPageTrace(pageTrace);
            command = Parser.parse(updateTask);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InputException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nPlease provide an index of item to be updated.", e.getMessage());
        }
    }

    @Test
    public void updateMultipleTasksUnsuccessful_onlyOneIndexProvided_exceptionThrown() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        taskList = testModule.getTasks();
        while (taskList.getList().size() > 0) {
            taskList.getList().remove(0);
        }
        Task testTask1 = new Event("Test 1", new DateTime("01/02/2029 10:00"),
                new DateTime("01/02/2029 12:00"));
        Task testTask2 = new Lecture("Test 2", new DateTime("01/03/2030 10:00"),
                new DateTime("01/03/2030 12:00"));

        taskList.add(testTask1);
        taskList.add(testTask2);

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        try {
            String updateTask = "update-* TESTMOD / task 1 done";
            Parser.setPageTrace(pageTrace);
            command = Parser.parse(updateTask);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InputException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nTo update a single task, provide the input in this "
                    + "format instead: update <pageContent> / <type> <one index in integer form> "
                    + "<done status>", e.getMessage());
        }
    }
}