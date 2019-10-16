package models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import controllers.AssignmentControllerUtil;
import java.util.ArrayList;
import models.data.Project;
import models.member.Member;
import models.task.Task;
import models.task.TaskState;
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
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
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
        String projectCommand = "assign task i/1 to/ 1 2 3";
        AssignmentControllerUtil assignmentController = new AssignmentControllerUtil();
        String[] args = projectCommand.substring(12).split(" ");
        assignmentController.manageAssignment(project,
            args, this.consoleView);
        assertEquals(3, assignmentController.getAssigneesIndex().size());
        assertEquals(0, assignmentController.getAssigneesIndex().size());
    }

}
