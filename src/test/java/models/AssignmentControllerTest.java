package models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import controllers.AssignmentControllerUtil;
import java.util.ArrayList;
import models.data.Project;
import models.member.Member;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import views.CLIView;

public class AssignmentControllerTest {
    private Project project;
    private Member member1;
    private Member member2;
    private Member member3;
    private Task task;
    private CLIView consoleView;

    /*
    Set up a dummy project for the test. It is assumed that all other components are working.
    Some tests for other classes are included in this test to ensure proper functioning before
    testing.
     */
    AssignmentControllerTest() {
        project = new Project("New project");
        member1 = new Member("Tom", "NIL", "NIL", 1);
        member2 = new Member("Dick", "NIL","NIL", 2);
        member3 = new Member("Harry", "NIL", "NIL", 3);
        CLIView consoleView = new CLIView();
        task = new Task("Test task", 0,
            null, 0, TaskState.OPEN, new ArrayList<String>());
        project.addMember(member1);
        project.addMember(member2);
        project.addMember(member3);
        project.addTask(task);
        this.consoleView = new CLIView();
    }

    @Test
    @Order(1)
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    @Order(2)
    public void testSetupOfProjectAndMembers() {
        assertEquals("1. Tom (Phone: NIL | Email: NIL)",
            member1.getDetails());
        assertEquals("2. Dick (Phone: NIL | Email: NIL)",
            member2.getDetails());
        assertEquals("3. Harry (Phone: NIL | Email: NIL)",
            member3.getDetails());
        assertEquals(3, project.getNumOfMembers());
        assertEquals("Tom", project.getMembers().getMember(1).getName());
        assertEquals("Dick", project.getMembers().getMember(2).getName());
        assertEquals("Harry", project.getMembers().getMember(3).getName());
        assertEquals(1, project.getTasks().getSize());
        assertEquals("Test task", project.getTask(1).getTaskName());
    }

    @Test
    public void testManageAssignment() {
        assertEquals(1, project.getTasks().getSize());
        assertEquals("Test task", project.getTask(1).getTaskName());
        String projectCommand = "assign task i/1 to/ 1 3 rm/ 2 4";
        AssignmentControllerUtil assignmentController = new AssignmentControllerUtil();
        assignmentController.manageAssignment(project,
            projectCommand.substring(12).split(" "), this.consoleView);
        assertEquals(2, assignmentController.getAssigneesIndex().size());
        assertEquals(0, assignmentController.getUnassigneesIndex().size());
        assertEquals(2,
            project.getTask(1).getAssignedMembers().getNumberOfAssignees());
        assertEquals("Tom",
            project.getTask(1).getAssignedMembers().getMember(1).getName());
        assertEquals("Harry",
            project.getTask(1).getAssignedMembers().getMember(2).getName());
        assertEquals("Cannot unassign member with index 2 (Dick) "
            + "because they are not assigned the task yet!",
            assignmentController.getMessages().get(0));
        assertEquals("Member with index number 4 does not exist!",
            assignmentController.getMessages().get(1));

        projectCommand = "assign task i/1 to/ 1 rm/ 3";
        assignmentController = new AssignmentControllerUtil();
        assignmentController.manageAssignment(project,
            projectCommand.substring(12).split(" "), consoleView);
        assertEquals(0, assignmentController.getAssigneesIndex().size());
        assertEquals(1, assignmentController.getUnassigneesIndex().size());
        assertEquals("Member with index 1 (Tom) has already been assigned this task!",
            assignmentController.getMessages().get(0));
        assertEquals(1,
            project.getTask(1).getAssignedMembers().getNumberOfAssignees());
        assertEquals("Tom",
            project.getTask(1).getAssignedMembers().getMember(1).getName());
    }

    @Test
    public void testManageAssignment_invalidTaskNumber() {
        assertEquals(1, project.getTasks().getSize());
        assertEquals("Test task", project.getTask(1).getTaskName());
        String projectCommand = "assign task i/200 to/ 1";
        AssignmentControllerUtil assignmentController = new AssignmentControllerUtil();
        String[] args = projectCommand.substring(12).split(" ");
        assignmentController.manageAssignment(project,
            args, this.consoleView);
        assertEquals(0, assignmentController.getAssigneesIndex().size());
        assertEquals(0, assignmentController.getUnassigneesIndex().size());
        assertEquals("The task you wish to assign (task index 200 ) does not exist!",
            assignmentController.getMessages().get(0));
        assertEquals("Please check the index number of the task and try again.",
            assignmentController.getMessages().get(1));
    }
}
