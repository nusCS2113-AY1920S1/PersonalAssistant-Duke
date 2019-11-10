package helpertests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.constant.ConstantHelper.VIEW_ASSIGNMENTS_INSUFFICIENT_PARAMS_MESSAGE;
import static util.constant.ConstantHelper.VIEW_ASSIGNMENTS_INVALID_FLAG_MESSAGE;
import static util.constant.ConstantHelper.VIEW_ASSIGNMENTS_NO_MEMBERS_MESSAGE;
import static util.constant.ConstantHelper.VIEW_ASSIGNMENTS_NO_TASKS_MESSAGE;

import java.util.ArrayList;
import models.member.Member;
import models.project.Project;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Test;
import util.uiformatter.AssignmentViewHelper;

public class AssignmentViewHelperTest {
    private final Project project;
    private final Member member1;
    private final Member member2;
    private final Member member3;
    private String simulatedUserInput;
    private String[] expectedOutput;
    private String[] actualOutput;

    /**
     * Test for AssignmentViewHelper. Initialise dummy project and members with assignments upon
     * creation.
     */
    public AssignmentViewHelperTest() {
        this.project = new Project("Test Project");
        this.member1 = new Member("Tom", "--", "--", 1, "member");
        this.member2 = new Member("Dick", "--", "--", 2, "member");
        this.member3 = new Member("Harry", "--", "--", 3, "member");

        Task task = new Task("Documentation", 1,null, 10, TaskState.OPEN, new ArrayList<>());
        Task task2 = new Task("JUnit tests", 1,null, 10, TaskState.OPEN, new ArrayList<>());
        this.project.addMember(member1);
        this.project.addMember(member2);
        this.project.addMember(member3);
        this.project.addTask(task);
        this.project.addTask(task2);
        this.project.createAssignment(task, member1);
        this.project.createAssignment(task, member2);
        this.project.createAssignment(task2, member2);
        this.project.createAssignment(task2, member3);
    }

    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testSetupOfProjectAndMembers() {
        assertEquals("Test Project", project.getName());
        assertEquals("1. Tom (Phone: -- | Email: -- | Role: member)",
            member1.getDetails());
        assertEquals("2. Dick (Phone: -- | Email: -- | Role: member)",
            member2.getDetails());
        assertEquals("3. Harry (Phone: -- | Email: -- | Role: member)",
            member3.getDetails());
        assertEquals(3, project.getNumOfMembers());
        assertEquals("Tom", project.getMemberList().getMember(1).getName());
        assertEquals("Dick", project.getMemberList().getMember(2).getName());
        assertEquals("Harry", project.getMemberList().getMember(3).getName());
        assertEquals(2, project.getTaskList().getSize());
        assertEquals("Documentation", project.getTask(1).getTaskName());
        assertEquals("JUnit tests", project.getTask(2).getTaskName());
    }

    @Test
    public void testViewAssignments_validMemberInput_executionSuccess() {
        simulatedUserInput = "view assignments -m all";
        AssignmentViewHelper assignmentViewHelper = new AssignmentViewHelper();
        String[] actualMemberOutput = assignmentViewHelper.viewAssignments(simulatedUserInput, project);
        String[] expectedMemberOutput = new String[] {
            "+----------------------------------------------------------------------+",
            "|Here are each member's tasks:                                         |",
            "+----------------------------------------------------------------------+",
            "| +-------------------------------+ +-------------------------------+  |",
            "| |Tom                            | |Dick                           |  |",
            "| +-------------------------------+ +-------------------------------+  |",
            "| |1. Documentation (P: 1, D: --, | |1. Documentation (P: 1, D: --, |  |",
            "| |C: 10, S: OPEN)                | |C: 10, S: OPEN)                |  |",
            "| +-------------------------------+ |                               |  |",
            "| +-------------------------------+ |2. JUnit tests (P: 1, D: --,   |  |",
            "| |Harry                          | |C: 10, S: OPEN)                |  |",
            "| +-------------------------------+ +-------------------------------+  |",
            "| |1. JUnit tests (P: 1, D: --,   |                                    |",
            "| |C: 10, S: OPEN)                |                                    |",
            "| +-------------------------------+                                    |",
            "+----------------------------------------------------------------------+"};
        assertArrayEquals(expectedMemberOutput, actualMemberOutput);
    }

    @Test
    public void testViewAssignments_validTaskInput_executionSuccess() {
        simulatedUserInput = "view assignments -t all";
        AssignmentViewHelper assignmentViewHelper = new AssignmentViewHelper();
        String[] actualTaskOutput = assignmentViewHelper.viewAssignments(simulatedUserInput, project);
        String[] expectedTaskOutput = new String[] {
            "+----------------------------------------------------------------------+",
            "|Here are the members assigned to each task:                           |",
            "+----------------------------------------------------------------------+",
            "| +-------------------------------+ +-------------------------------+  |",
            "| |Documentation (P: 1, D: --, C: | |JUnit tests (P: 1, D: --, C:   |  |",
            "| |10, S: OPEN)                   | |10, S: OPEN)                   |  |",
            "| +-------------------------------+ +-------------------------------+  |",
            "| |1. Tom                         | |1. Dick                        |  |",
            "| |2. Dick                        | |2. Harry                       |  |",
            "| +-------------------------------+ +-------------------------------+  |",
            "+----------------------------------------------------------------------+"};
        assertArrayEquals(expectedTaskOutput, actualTaskOutput);
    }

    //@@author sinteary
    @Test
    public void testViewAssignments_invalidTaskInput_executionFailure() {
        simulatedUserInput = "view assignments -m";
        AssignmentViewHelper assignmentViewHelper = new AssignmentViewHelper();
        actualOutput = assignmentViewHelper.viewAssignments(simulatedUserInput, project);
        expectedOutput = VIEW_ASSIGNMENTS_INSUFFICIENT_PARAMS_MESSAGE;
        assertArrayEquals(expectedOutput, actualOutput);

        simulatedUserInput = "view assignments -a";
        actualOutput = assignmentViewHelper.viewAssignments(simulatedUserInput, project);
        expectedOutput = VIEW_ASSIGNMENTS_INVALID_FLAG_MESSAGE;

        simulatedUserInput = "view assignments";
        actualOutput = assignmentViewHelper.viewAssignments(simulatedUserInput, project);
        expectedOutput = VIEW_ASSIGNMENTS_INSUFFICIENT_PARAMS_MESSAGE;
    }

    @Test
    void testProjectViewAssignments_emptyProject_executionSuccess() {
        /**
         * Refer to AssignmentViewHelperTest for other tests regarding view assignments.
         */
        Project project = new Project("New project");
        AssignmentViewHelper assignmentViewHelper = new AssignmentViewHelper();

        //no members
        simulatedUserInput = "view assignments -m all";
        expectedOutput = VIEW_ASSIGNMENTS_NO_MEMBERS_MESSAGE;
        actualOutput = assignmentViewHelper.viewAssignments(simulatedUserInput, project);
        assertArrayEquals(expectedOutput, actualOutput);
        //no tasks
        simulatedUserInput = "view assignments -t all";
        expectedOutput = VIEW_ASSIGNMENTS_NO_TASKS_MESSAGE;
        actualOutput = assignmentViewHelper.viewAssignments(simulatedUserInput, project);
        assertEquals(expectedOutput, actualOutput);
    }
}