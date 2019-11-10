package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.constant.ConstantHelper.ASSIGN_TASKS_INSUFFICIENT_PARAMS_MESSAGE;
import static util.constant.ConstantHelper.ASSIGN_TASKS_NO_VALID_MEMBERS_MESSAGE;
import static util.constant.ConstantHelper.ASSIGN_TASKS_NO_VALID_TASKS_MESSAGE;

import java.util.ArrayList;
import models.project.Project;
import models.member.Member;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Test;

//@@author sinteary
public class AssignmentControllerTest {
    private final Project project;
    private final Member member1;
    private final Member member2;
    private final Member member3;
    private String simulatedUserInput;
    private String actualOutput;
    private String expectedOutput;
    private String[] actualOutputArray;
    private String[] expectedOutputArray;

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
    void testSetupOfProjectAndMembers() {
        assertEquals("Test Project", project.getName());
        assertEquals("1. Tom (Phone: NIL | Email: NIL | Role: member)",
            member1.getDetails());
        assertEquals("2. Dick (Phone: NIL | Email: NIL | Role: member)",
            member2.getDetails());
        assertEquals("3. Harry (Phone: NIL | Email: NIL | Role: member)",
            member3.getDetails());
        assertEquals(3, project.getNumOfMembers());
        assertEquals("Tom", project.getMember(1).getName());
        assertEquals("Dick", project.getMember(2).getName());
        assertEquals("Harry", project.getMember(3).getName());
        assertEquals(1, project.getNumOfTasks());
    }

    @Test
    void testAssignAndUnassign_validInput_executionSuccess() {
        AssignmentController assignmentController = new AssignmentController(project);
        simulatedUserInput = "assign task -i 1 -to 1 2";
        assignmentController.assignAndUnassign(simulatedUserInput);
        assertTrue(project.containsAssignment(project.getTask(1), member1));
        assertTrue(project.containsAssignment(project.getTask(1), member2));
        assertFalse(project.containsAssignment(project.getTask(1), member3));

        assignmentController = new AssignmentController(project);
        simulatedUserInput = "assign task -i 1 -to 3 -rm 1 ";
        assignmentController.assignAndUnassign(simulatedUserInput);
        assertFalse(project.containsAssignment(project.getTask(1), member1));
        assertTrue(project.containsAssignment(project.getTask(1), member2));
        assertTrue(project.containsAssignment(project.getTask(1), member3));
    }

    @Test
    void testAssignAndUnassign_insufficientInputs_executionFail() {
        AssignmentController assignmentController = new AssignmentController(project);
        simulatedUserInput = "assign task";
        assignmentController.assignAndUnassign(simulatedUserInput);
        actualOutputArray = assignmentController.getErrorMessages().toArray(new String[0]);
        expectedOutputArray = ASSIGN_TASKS_INSUFFICIENT_PARAMS_MESSAGE;
        assertEquals(expectedOutput, actualOutput);

        simulatedUserInput = "assign task -i";
        assignmentController = new AssignmentController(project);
        assignmentController.assignAndUnassign(simulatedUserInput);
        actualOutputArray = assignmentController.getErrorMessages().toArray(new String[0]);
        expectedOutputArray = ASSIGN_TASKS_NO_VALID_TASKS_MESSAGE;
        assertEquals(expectedOutput, actualOutput);

        simulatedUserInput = "assign task -i 1 -to";
        assignmentController = new AssignmentController(project);
        assignmentController.assignAndUnassign(simulatedUserInput);
        actualOutputArray = assignmentController.getErrorMessages().toArray(new String[0]);
        expectedOutputArray = ASSIGN_TASKS_NO_VALID_MEMBERS_MESSAGE;
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testAssignAndUnassign_invalidIndexNumbers_executionFail() {
        AssignmentController assignmentController = new AssignmentController(project);
        simulatedUserInput = "assign task -i 5 -to 1";
        assignmentController.assignAndUnassign(simulatedUserInput);
        ArrayList<String> errorMessages = assignmentController.getErrorMessages();
        actualOutput = errorMessages.get(0);
        expectedOutput = "Task with index 5 does not exist.";
        assertEquals(expectedOutput, actualOutput);

        assignmentController = new AssignmentController(project);
        simulatedUserInput = "assign task -i 1 -to 5";
        assignmentController.assignAndUnassign(simulatedUserInput);
        errorMessages = assignmentController.getErrorMessages();
        actualOutput = errorMessages.get(0);
        expectedOutput = "Member with index 5 does not exist.";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testAssignAndUnassign_repeatedAssignment_executionFail() {
        //assigning task to someone who is already assigned
        AssignmentController assignmentController = new AssignmentController(project);
        simulatedUserInput = "assign task -i 1 -to 1";
        assignmentController.assignAndUnassign(simulatedUserInput);
        assertTrue(project.containsAssignment(project.getTask(1), member1));

        simulatedUserInput = "assign task -i 1 -to 1";
        assignmentController = new AssignmentController(project);
        assignmentController.assignAndUnassign(simulatedUserInput);
        ArrayList<ArrayList<String>> successMessages = assignmentController.getSuccessMessages();
        ArrayList<String> taskMessages = successMessages.get(0);
        actualOutput = taskMessages.get(0);
        expectedOutput = "For task 1 (Test task):";
        assertEquals(expectedOutput, actualOutput);
        actualOutput = taskMessages.get(1);
        expectedOutput = "Task has already been assigned to member 1 (Tom).";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testAssignAndUnassign_invalidUnassignment_executionFail() {
        //unassigning task to someone who has not been assigned yet
        AssignmentController assignmentController = new AssignmentController(project);
        simulatedUserInput = "assign task -i 1 -rm 1";
        assignmentController = new AssignmentController(project);
        assignmentController.assignAndUnassign(simulatedUserInput);
        ArrayList<ArrayList<String>> successMessages = assignmentController.getSuccessMessages();
        ArrayList<String> taskMessages = successMessages.get(0);
        actualOutput = taskMessages.get(0);
        expectedOutput = "For task 1 (Test task):";
        assertEquals(expectedOutput, actualOutput);
        actualOutput = taskMessages.get(1);
        expectedOutput = "Task cannot be unassigned from member 1 (Tom) as it was not assigned in the first place!";
        assertEquals(expectedOutput, actualOutput);
    }

}
