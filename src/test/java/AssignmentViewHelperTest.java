import static org.junit.jupiter.api.Assertions.assertEquals;

import controllers.AssignmentViewHelper;
import java.util.ArrayList;
import java.util.Arrays;
import models.data.Project;
import models.member.Member;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Test;

public class AssignmentViewHelperTest {
    private final Project project;
    private final Member member1;
    private final Member member2;
    private final Member member3;

    /**
     * Test for AssignmentViewHelper. Initialise dummy project and members with assignments upon
     * creation.
     */
    public AssignmentViewHelperTest() {
        this.project = new Project("Test Project");
        this.member1 = new Member("Tom", "--", "--", 1, "member");
        this.member2 = new Member("Dick", "--", "--", 2, "member");
        this.member3 = new Member("Harry", "--", "--", 3, "member");

        Task task = new Task("Documentation", 0,null, 0, TaskState.OPEN, new ArrayList<>());
        Task task2 = new Task("JUnit tests", 0,null, 0, TaskState.OPEN, new ArrayList<>());
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
        assertEquals("Test Project", project.getDescription());
        assertEquals("1. Tom (Phone: -- | Email: -- | Role: member)",
            member1.getDetails());
        assertEquals("2. Dick (Phone: -- | Email: -- | Role: member)",
            member2.getDetails());
        assertEquals("3. Harry (Phone: -- | Email: -- | Role: member)",
            member3.getDetails());
        assertEquals(3, project.getNumOfMembers());
        assertEquals("Tom", project.getMembers().getMember(1).getName());
        assertEquals("Dick", project.getMembers().getMember(2).getName());
        assertEquals("Harry", project.getMembers().getMember(3).getName());
        assertEquals(2, project.getTasks().getSize());
        assertEquals("Documentation", project.getTask(1).getTaskName());
        assertEquals("JUnit tests", project.getTask(2).getTaskName());
    }

    @Test
    public void testGetMemberOutput() {
        ArrayList<Integer> validMembersIndexes = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
        AssignmentViewHelper assignmentViewHelper = new AssignmentViewHelper();
        ArrayList<String> memberOutput = AssignmentViewHelper.getMemberOutput(validMembersIndexes,
            project);
        assertEquals("Here are each member's tasks:", memberOutput.get(0));
        assertEquals("Tasks assigned to Tom", memberOutput.get(1));
        assertEquals("1. Documentation | Priority: 0 | Due: -- | Credit: 0 | State: OPEN",
            memberOutput.get(2));
        assertEquals("Tasks assigned to Dick", memberOutput.get(3));
        assertEquals("1. Documentation | Priority: 0 | Due: -- | Credit: 0 | State: OPEN",
            memberOutput.get(4));
        assertEquals("2. JUnit tests | Priority: 0 | Due: -- | Credit: 0 | State: OPEN",
            memberOutput.get(5));
        assertEquals("Tasks assigned to Harry", memberOutput.get(6));
        assertEquals("1. JUnit tests | Priority: 0 | Due: -- | Credit: 0 | State: OPEN",
            memberOutput.get(7));
    }

    @Test
    public void testGetTaskOutput() {
        ArrayList<Integer> validTasksIndexes = new ArrayList<Integer>(Arrays.asList(1, 2));
        AssignmentViewHelper assignmentViewHelper = new AssignmentViewHelper();
        ArrayList<String> taskOutput = AssignmentViewHelper.getTaskOutput(validTasksIndexes,
            project);
        assertEquals("Here are the members assigned to each task:", taskOutput.get(0));
        assertEquals("Documentation | Priority: 0 | Due: -- | Credit: 0 | State: OPEN", taskOutput.get(1));
        assertEquals("Members assigned to task 1 (Documentation | Priority: 0 | Due: -- | Credit: 0 | State: OPEN)",
            taskOutput.get(2));
        assertEquals("1. Tom", taskOutput.get(3));
        assertEquals("2. Dick", taskOutput.get(4));
        assertEquals("JUnit tests | Priority: 0 | Due: -- | Credit: 0 | State: OPEN",
            taskOutput.get(5));
        assertEquals("Members assigned to task 2 (JUnit tests | Priority: 0 | Due: -- | Credit: 0 | State: OPEN)",
            taskOutput.get(6));
        assertEquals("1. Dick", taskOutput.get(7));
        assertEquals("2. Harry", taskOutput.get(8));
    }

}