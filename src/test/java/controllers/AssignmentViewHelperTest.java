package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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

    public AssignmentViewHelperTest() {
        this.project = new Project("Test Project");
        this.member1 = new Member("Tom", "--", "--", 1);
        this.member2 = new Member("Dick", "--", "--", 2);
        this.member3 = new Member("Harry", "--", "--", 3);

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
        assertEquals("1. Tom (Phone: -- | Email: --)",
            member1.getDetails());
        assertEquals("2. Dick (Phone: -- | Email: --)",
            member2.getDetails());
        assertEquals("3. Harry (Phone: -- | Email: --)",
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
    public void testParseTask() {
        String projectCommand = "view assignments -t all";
        AssignmentViewHelper assignmentViewHelper = new AssignmentViewHelper();
        ArrayList<Integer> parsedTaskIndexes = assignmentViewHelper.parseTasks(projectCommand.substring(20), project);
        assertEquals(2, parsedTaskIndexes.size());
        assertEquals(1, parsedTaskIndexes.get(0));
        assertEquals(2, parsedTaskIndexes.get(1));

        assignmentViewHelper = new AssignmentViewHelper();
        projectCommand = "view assignments -t 2 abc 3";
        parsedTaskIndexes = assignmentViewHelper.parseTasks(projectCommand.substring(20), project);
        assertEquals("Could not recognise task abc, please ensure it is an integer.",
            assignmentViewHelper.getErrorMessages().get(0));
        assertEquals("Task with index 3 does not exist.", assignmentViewHelper.getErrorMessages().get(1));
        assertEquals(1, parsedTaskIndexes.size());
        assertEquals(2, parsedTaskIndexes.get(0));
    }

    @Test
    public void testParseMembers() {
        String projectCommand = "view assignments -m all";
        AssignmentViewHelper assignmentViewHelper = new AssignmentViewHelper();
        ArrayList<Integer> parsedMemberIndexes = assignmentViewHelper.parseMembers(projectCommand.substring(20), project);
        assertEquals(3, parsedMemberIndexes.size());
        assertEquals(1, parsedMemberIndexes.get(0));
        assertEquals(2, parsedMemberIndexes.get(1));
        assertEquals(3, parsedMemberIndexes.get(2));

        assignmentViewHelper = new AssignmentViewHelper();
        projectCommand = "view assignments -t 1 3 hello 7";
        parsedMemberIndexes = assignmentViewHelper.parseMembers(projectCommand.substring(20), project);
        assertEquals("Could not recognise member hello, please ensure it is an integer.",
            assignmentViewHelper.getErrorMessages().get(0));
        assertEquals("Member with index 7 does not exist.", assignmentViewHelper.getErrorMessages().get(1));
        assertEquals(2, parsedMemberIndexes.size());
        assertEquals(1, parsedMemberIndexes.get(0));
        assertEquals(3, parsedMemberIndexes.get(1));
    }

}
