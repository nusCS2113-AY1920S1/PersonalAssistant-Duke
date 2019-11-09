package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import models.project.Project;
import models.member.Member;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Test;

public class AssignmentControllerTest {
    private final Project project;
    private final Member member1;
    private final Member member2;
    private final Member member3;

    AssignmentControllerTest() {
        this.project = new Project("Test Project");
        this.member1 = new Member("Tom", "NIL", "NIL", 1, "member");
        this.member2 = new Member("Dick", "NIL", "NIL", 2, "member");
        this.member3 = new Member("Harry", "NIL", "NIL", 3, "member");

        Task task = new Task("Test task", 0,null, 0, TaskState.OPEN, new ArrayList<>());
        this.project.addMember(member1);
        this.project.addMember(member2);
        this.project.addMember(member3);
        this.project.addTask(task);
    }

    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testSetupOfProjectAndMembers() {
        assertEquals("Test Project", project.getName());
        assertEquals("1. Tom (Phone: NIL | Email: NIL | Role: member)",
            member1.getDetails());
        assertEquals("2. Dick (Phone: NIL | Email: NIL | Role: member)",
            member2.getDetails());
        assertEquals("3. Harry (Phone: NIL | Email: NIL | Role: member)",
            member3.getDetails());
        assertEquals(3, project.getNumOfMembers());
        assertEquals("Tom", project.getMemberList().getMember(1).getName());
        assertEquals("Dick", project.getMemberList().getMember(2).getName());
        assertEquals("Harry", project.getMemberList().getMember(3).getName());
        assertEquals(1, project.getTaskList().getSize());
        assertEquals("Test task", project.getTask(1).getTaskName());
    }

    @Test
    void testAssignAndUnassign() {
        AssignmentController assignmentController = new AssignmentController(project);
        String assignCommand = "assign task -i 1 -to 1 2 -rm 3 4";
        assignmentController.assignAndUnassign(assignCommand);
        assertTrue(project.containsAssignment(project.getTask(1), member1));
        assertTrue(project.containsAssignment(project.getTask(1), member2));
        assertFalse(project.containsAssignment(project.getTask(1), member3));

        assignmentController = new AssignmentController(project);
        assignCommand = "assign task -i 1 -to 3 -rm 1 ";
        assignmentController.assignAndUnassign(assignCommand);
        assertFalse(project.containsAssignment(project.getTask(1), member1));
        assertTrue(project.containsAssignment(project.getTask(1), member2));
        assertTrue(project.containsAssignment(project.getTask(1), member3));
    }

}
