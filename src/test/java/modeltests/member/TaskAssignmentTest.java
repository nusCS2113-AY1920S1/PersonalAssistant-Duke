package modeltests.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import models.data.Project;
import models.member.Member;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class TaskAssignmentTest {
    private final Project project;
    private final Member member1;
    private final Member member2;
    private final Member member3;

    /*
    Set up a dummy project for the test. It is assumed that all other components are working.
    Some tests for other classes are included in this test to ensure proper functioning before
    testing. Testing:
     */
    TaskAssignmentTest() {
        project = new Project("New project");
        member1 = new Member("Tom", "NIL", "NIL", 1);
        member2 = new Member("Dick", "NIL","NIL", 2);
        member3 = new Member("Harry", "NIL", "NIL", 3);
        Task task = new Task("Test task", 0,
            null, 0, TaskState.OPEN, new ArrayList<>());
        project.addMember(member1);
        project.addMember(member2);
        project.addMember(member3);
        project.addTask(task);
    }

    @Test
    @Order(1)
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    @Order(2)
    void testSetupOfProjectAndMembers() {
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
    void testManageAssignment() {
        assertEquals(1, project.getTasks().getSize());
        assertEquals("Test task", project.getTask(1).getTaskName());
        project.createAssignment(project.getTask(1),member1);
        project.createAssignment(project.getTask(1),member2);
        project.createAssignment(project.getTask(1),member3);
        assertEquals("[Test task is assigned to: , Tom, Dick, Harry]", project.getAssignedTaskList().toString());
        /* assertEquals(0, projectInputController.getUnassigneesIndex().size());
        assertEquals(2,
            project.getTask(1).getAssignedMembers().getNumberOfAssignees());
        assertEquals("Tom",
            project.getTask(1).getAssignedMembers().getMember(1).getName());
        assertEquals("Harry",
            project.getTask(1).getAssignedMembers().getMember(2).getName());
        assertEquals("Cannot unassign member with index 2 (Dick) "
            + "because they are not assigned the task yet!",
            projectInputController.getMessageForView().get(0));
        assertEquals("Member with index number 4 does not exist!",
            projectInputController.getMessageForView().get(1));

        projectCommand = "assign task i/1 to/ 1 rm/ 3";
        projectInputController = new AssignmentController();
        projectInputController.manageAssignment(project,
            projectCommand.substring(12).split(" "), consoleView);
        assertEquals(0, projectInputController.getAssigneesIndex().size());
        assertEquals(1, projectInputController.getUnassigneesIndex().size());
        assertEquals("Member with index 1 (Tom) has already been assigned this task!",
            projectInputController.getMessageForView().get(0));
        assertEquals(1,
            project.getTask(1).getAssignedMembers().getNumberOfAssignees());
        assertEquals("Tom",
            project.getTask(1).getAssignedMembers().getMember(1).getName()); */
    }

    /*@Test
    void testManageAssignmentInvalidTaskNumber() {
        assertEquals(1, project.getTasks().getSize());
        assertEquals("Test task", project.getTask(1).getTaskName());
        String projectCommand = "assign task i/200 to/ 1";
        AssignmentController assignmentController = new AssignmentController();
        String[] args = projectCommand.substring(12).split(" ");
        assignmentController.manageAssignment(project,
                args, this.consoleView);
        assertEquals(0, assignmentController.getAssigneesIndex().size());
        assertEquals(0, assignmentController.getUnassigneesIndex().size());
        assertEquals("The task you wish to assign (task index 200 ) does not exist!",
                assignmentController.getMessageForView().get(0));
        assertEquals("Please check the index number of the task and try again.",
                assignmentController.getMessageForView().get(1));
    }*/
}
