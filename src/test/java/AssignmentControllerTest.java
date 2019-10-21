import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controllers.AssignmentController;
import java.util.ArrayList;
import models.data.Project;
import models.member.Member;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Test;
import views.CLIView;

public class AssignmentControllerTest {
    private final Project project;
    private final Member member1;
    private final Member member2;
    private final Member member3;
    private final CLIView consoleView;


    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    AssignmentControllerTest() {
        project = new Project("Test Project");
        member1 = new Member("Tom", "NIL", "NIL", 1);
        member2 = new Member("Dick", "NIL", "NIL", 2);
        member3 = new Member("Harry", "NIL", "NIL", 3);

        Task task = new Task("Test task", 0,null, 0, TaskState.OPEN, new ArrayList<>());
        project.addMember(member1);
        project.addMember(member2);
        project.addMember(member3);
        project.addTask(task);
        this.consoleView = new CLIView();
    }

    @Test
    void testSetupOfProjectAndMembers() {
        assertEquals("Test Project", project.getDescription());
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
    void testParseAssignmentInput() {
        AssignmentController assignmentController = new AssignmentController(project);
        String assignCommand = "assign task -i 1 -to 1 2 -rm 3 4";
        assignmentController.parseAssignmentInput(assignCommand.substring(12));
        assertEquals(2, assignmentController.getValidMembersToAssign().size());
        assertEquals(1, assignmentController.getValidMembersToAssign().get(0));
        assertEquals(2, assignmentController.getValidMembersToAssign().get(1));
        assertEquals(1, assignmentController.getValidMembersToUnassign().size());
        assertEquals(3, assignmentController.getValidMembersToUnassign().get(0));
        assertEquals(1, assignmentController.getValidTaskIndexes().size());
        assertEquals(1, assignmentController.getValidTaskIndexes().get(0));
    }

    @Test
    void testAssignAndUnassign() {
        AssignmentController assignmentController = new AssignmentController(project);
        String assignCommand = "assign task -i 1 -to 1 2 -rm 3 4";
        assignmentController.assignAndUnassign(assignCommand.substring(12));
        assertTrue(project.containsAssignment(project.getTask(1), member1));
        assertTrue(project.containsAssignment(project.getTask(1), member2));
        assertFalse(project.containsAssignment(project.getTask(1), member3));

        assignmentController = new AssignmentController(project);
        assignCommand = "assign task -i 1 -to 3 -rm 1 ";
        assignmentController.assignAndUnassign(assignCommand.substring(12));
        assertFalse(project.containsAssignment(project.getTask(1), member1));
        assertTrue(project.containsAssignment(project.getTask(1), member2));
        assertTrue(project.containsAssignment(project.getTask(1), member3));
    }

}
