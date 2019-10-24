package modeltests.task;

import controllers.AssignmentController;
import models.data.Project;
import models.member.Member;
import models.task.Task;
import models.task.TaskList;
import models.task.TaskState;
import org.junit.jupiter.api.Test;
import util.date.DateTimeHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskListTest {
    private Project project;
    private DateTimeHelper dateTimeHelper;

    TaskListTest() {
        this.project = new Project("Infinity_Gauntlet");
        this.dateTimeHelper = new DateTimeHelper();
    }

    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testAddTask() {
        ArrayList<String> taskRequirements = new ArrayList<>();
        Task task = new Task("task", 5, null,100, TaskState.OPEN,taskRequirements);
        TaskList taskList = new TaskList();
        taskList.addTask(task);
        assertEquals("task | Priority: 5 | Due: -- | Credit: 100 | State: OPEN",taskList.getTask(1).getDetails());
    }

    @Test
    void testRemoveTask() {
        ArrayList<String> taskRequirements = new ArrayList<>();
        Task task = new Task("task", 5, null,100, TaskState.OPEN,taskRequirements);
        TaskList taskList = new TaskList();
        taskList.addTask(task);
        taskList.removeTask(1);
        assertEquals(0,taskList.getSize());
    }

    @Test
    void testGetAllTaskDetails() {
        TaskList taskList = new TaskList();
        ArrayList<String> taskRequirements = new ArrayList<>();
        Task task1 = new Task("task1", 1, null, 100, TaskState.OPEN, taskRequirements);
        Task task2 = new Task("task2", 2, null, 100, TaskState.OPEN, taskRequirements);
        Task task3 = new Task("task3", 3, null, 100, TaskState.OPEN, taskRequirements);
        Task task4 = new Task("task4", 4, null, 100, TaskState.OPEN, taskRequirements);
        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);
        taskList.addTask(task4);
        assertNotNull(taskList.getTaskList());

        String testTaskListString = "[1. task4 | Priority: 4 | Due: -- | Credit: 100 | State: OPEN, "
                + "2. task3 | Priority: 3 | Due: -- | Credit: 100 | State: OPEN, "
                + "3. task2 | Priority: 2 | Due: -- | Credit: 100 | State: OPEN, "
                + "4. task1 | Priority: 1 | Due: -- | Credit: 100 | State: OPEN]";
        assertEquals(testTaskListString,taskList.getAllTaskDetails(project.getTasksAndAssignedMembers()).toString());

        String testRemoveTaskListString = "[1. task4 | Priority: 4 | Due: -- | Credit: 100 | State: OPEN, "
                + "2. task2 | Priority: 2 | Due: -- | Credit: 100 | State: OPEN, "
                + "3. task1 | Priority: 1 | Due: -- | Credit: 100 | State: OPEN]";
        taskList.removeTask(2);
        assertEquals(testRemoveTaskListString,taskList.getAllTaskDetails(
                project.getTasksAndAssignedMembers()).toString());
    }

    @Test
    void testGetAllSortedTaskDetails() {
        TaskList taskList = new TaskList();
        ArrayList<String> taskRequirements = new ArrayList<>();
        try {
            DateTimeHelper dateTimeHelper = new DateTimeHelper();
            Date dueDate1 = dateTimeHelper.formatDate("31/10/2019");
            Date dueDate2 = dateTimeHelper.formatDate("21/09/2019");
            Date dueDate3 = dateTimeHelper.formatDate("21/01/2020");
            Date dueDate4 = dateTimeHelper.formatDate("21/12/1920");

            Task task1 = new Task("taskOne", 1, dueDate1, 100, TaskState.OPEN, taskRequirements);
            Task task2 = new Task("taskTwo", 2, dueDate2, 90, TaskState.DONE, taskRequirements);
            Task task3 = new Task("taskEight", 3, dueDate3, 80, TaskState.OPEN, taskRequirements);
            Task task4 = new Task("taskSix", 4, dueDate4, 70, TaskState.DONE, taskRequirements);
            Task task5 = new Task("taskFive", 5, null, 60, TaskState.OPEN, taskRequirements);
            taskList.addTask(task1);
            taskList.addTask(task2);
            taskList.addTask(task3);
            taskList.addTask(task4);
            taskList.addTask(task5);
            assertNotNull(taskList.getTaskList());

            String testPriorityTaskListString = "["
                    + "1. taskFive | Priority: 5 | Due: -- | Credit: 60 | State: OPEN, "
                    + "2. taskSix | Priority: 4 | Due: 21 Dec 1920" + dateTimeHelper.getDifferenceDays(dueDate4)
                    + " | Credit: 70 | State: DONE, "
                    + "3. taskEight | Priority: 3 | Due: 21 Jan 2020" + dateTimeHelper.getDifferenceDays(dueDate3)
                    + " | Credit: 80 | State: OPEN, "
                    + "4. taskTwo | Priority: 2 | Due: 21 Sep 2019" + dateTimeHelper.getDifferenceDays(dueDate2)
                    + " | Credit: 90 | State: DONE, "
                    + "5. taskOne | Priority: 1 | Due: 31 Oct 2019" + dateTimeHelper.getDifferenceDays(dueDate1)
                    + " | Credit: 100 | State: OPEN"
                    + "]";
            assertEquals(testPriorityTaskListString,taskList.getAllSortedTaskDetails(
                    project.getTasksAndAssignedMembers(),"/PRIORITY").toString());

            String testNameTaskListString = "["
                    + "1. taskEight | Priority: 3 | Due: 21 Jan 2020" + dateTimeHelper.getDifferenceDays(dueDate3)
                    + " | Credit: 80 | State: OPEN, "
                    + "2. taskFive | Priority: 5 | Due: -- | Credit: 60 | State: OPEN, "
                    + "3. taskOne | Priority: 1 | Due: 31 Oct 2019" + dateTimeHelper.getDifferenceDays(dueDate1)
                    + " | Credit: 100 | State: OPEN, "
                    + "4. taskSix | Priority: 4 | Due: 21 Dec 1920" + dateTimeHelper.getDifferenceDays(dueDate4)
                    + " | Credit: 70 | State: DONE, "
                    + "5. taskTwo | Priority: 2 | Due: 21 Sep 2019" + dateTimeHelper.getDifferenceDays(dueDate2)
                    + " | Credit: 90 | State: DONE"
                    + "]";
            assertEquals(testNameTaskListString,taskList.getAllSortedTaskDetails(
                    project.getTasksAndAssignedMembers(),"/NAME").toString());

            String testDueDateTaskListString = "["
                    + "1. taskSix | Priority: 4 | Due: 21 Dec 1920" + dateTimeHelper.getDifferenceDays(dueDate4)
                    + " | Credit: 70 | State: DONE, "
                    + "2. taskTwo | Priority: 2 | Due: 21 Sep 2019" + dateTimeHelper.getDifferenceDays(dueDate2)
                    + " | Credit: 90 | State: DONE, "
                    + "3. taskOne | Priority: 1 | Due: 31 Oct 2019" + dateTimeHelper.getDifferenceDays(dueDate1)
                    + " | Credit: 100 | State: OPEN, "
                    + "4. taskEight | Priority: 3 | Due: 21 Jan 2020" + dateTimeHelper.getDifferenceDays(dueDate3)
                    + " | Credit: 80 | State: OPEN"
                    + "]";
            assertEquals(testDueDateTaskListString,taskList.getAllSortedTaskDetails(
                    project.getTasksAndAssignedMembers(),"/DATE").toString());

            String testCreditTaskListString = "["
                    + "1. taskOne | Priority: 1 | Due: 31 Oct 2019" + dateTimeHelper.getDifferenceDays(dueDate1)
                    + " | Credit: 100 | State: OPEN, "
                    + "2. taskTwo | Priority: 2 | Due: 21 Sep 2019" + dateTimeHelper.getDifferenceDays(dueDate2)
                    + " | Credit: 90 | State: DONE, "
                    + "3. taskEight | Priority: 3 | Due: 21 Jan 2020" + dateTimeHelper.getDifferenceDays(dueDate3)
                    + " | Credit: 80 | State: OPEN, "
                    + "4. taskSix | Priority: 4 | Due: 21 Dec 1920" + dateTimeHelper.getDifferenceDays(dueDate4)
                    + " | Credit: 70 | State: DONE, "
                    + "5. taskFive | Priority: 5 | Due: -- | Credit: 60 | State: OPEN"
                    + "]";
            assertEquals(testCreditTaskListString,taskList.getAllSortedTaskDetails(
                    project.getTasksAndAssignedMembers(),"/CREDIT").toString());

            String testKanbanTaskListString = "["
                    + "2. taskTwo | Priority: 2 | Due: 21 Sep 2019" + dateTimeHelper.getDifferenceDays(dueDate2)
                    + " | Credit: 90 | State: DONE, "
                    + "4. taskSix | Priority: 4 | Due: 21 Dec 1920" + dateTimeHelper.getDifferenceDays(dueDate4)
                    + " | Credit: 70 | State: DONE"
                    + "]";
            assertEquals(testKanbanTaskListString,taskList.getAllSortedTaskDetails(
                    project.getTasksAndAssignedMembers(),"/KANBAN-DONE").toString());

            project.addTask(new Task("task2",5, null,10,
                    TaskState.TODO, taskRequirements));
            project.addTask(new Task("task1",10, null,10,
                    TaskState.TODO, taskRequirements));
            project.addMember(new Member("Dillen", "9999", "dillen@gmail.com",1));
            project.addMember(new Member("Jerry", "9999", "jerryn@gmail.com",2));
            AssignmentController assignmentController = new AssignmentController(project);
            assignmentController.assignAndUnassign("-i 1 -to 1 2");
            AssignmentController assignmentController2 = new AssignmentController(project);
            assignmentController2.assignAndUnassign("-i 2 -to 1");
            String testMemberTaskListString = "[1. task2 | Priority: 5 | Due: -- | Credit: 10 | State: TODO]";
            String testMemberTaskListString2 = "[1. task1 | Priority: 10 | Due: -- | Credit: 10 | "
                    + "State: TODO, 2. task2 | Priority: 5 | Due: -- | Credit: 10 | State: TODO]";
            assertEquals(testMemberTaskListString,
                    project.getTasks().getAllSortedTaskDetails(
                            project.getTasksAndAssignedMembers(),"/WHO-Jerry").toString());
            assertEquals(testMemberTaskListString2,
                    project.getTasks().getAllSortedTaskDetails(
                            project.getTasksAndAssignedMembers(),"/WHO-Dillen").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}