package modeltests.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import models.project.Project;
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
    private final Task task;

    /*
    Set up a dummy project for the test. It is assumed that all other components are working.
    Some tests for other classes are included in this test to ensure proper functioning before
    testing. Testing:
     */
    TaskAssignmentTest() {
        this.project = new Project("New project");
        this.member1 = new Member("Tom", "NIL", "NIL", 1, "member");
        this.member2 = new Member("Dick", "NIL","NIL", 2, "member");
        this.member3 = new Member("Harry", "NIL", "NIL", 3, "member");
        this.task = new Task("Test task", 0,null, 0, TaskState.OPEN, new ArrayList<>());
        this.project.addMember(member1);
        this.project.addMember(member2);
        this.project.addMember(member3);
        this.project.addTask(task);
    }

    @Test
    @Order(1)
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    @Order(2)
    void testSetupOfProjectAndMembers() {
        assertEquals("1. Tom (Phone: NIL | Email: NIL | Role: member)",
            member1.getDetails());
        assertEquals("2. Dick (Phone: NIL | Email: NIL | Role: member)",
            member2.getDetails());
        assertEquals("3. Harry (Phone: NIL | Email: NIL | Role: member)",
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
        assertTrue(project.containsAssignment(task, member1));
        assertTrue(project.containsAssignment(task, member2));
        assertTrue(project.containsAssignment(task, member3));

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
