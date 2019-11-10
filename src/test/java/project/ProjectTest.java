package project;

import models.member.IMember;
import models.member.Member;
import models.project.Project;
import models.task.ITask;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void setNameTest() {
        Project testProject = new Project("Test Project");
        testProject.setName("New Test Name");
        String actualOutput = testProject.getName();
        String expectedOutput = "New Test Name";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void taskExistsTest() {
        Project testProject = new Project("Test Project");
        Task testTask = new Task("Test Task", 1, null, 1, TaskState.OPEN, null);
        testProject.addTask(testTask);
        assertTrue(testProject.taskExists(testTask));
    }

    @Test
    void memberExistsTest() {
        Project testProject = new Project("Test Project");
        Member testMember = new Member("Test Member", "--", "--", 1, "member");
        testProject.addMember(testMember);
        assertTrue(testProject.memberExists(testMember));
    }

    @Test
    void getTaskIndexNameTest() {
        Project testProject = new Project("Test Project");
        Task testTask = new Task("Test Task", 1, null, 1, TaskState.OPEN, null);
        testProject.addTask(testTask);
        String actualOutput = testProject.getTaskIndexName(1);
        String expectedOutput = "Test Task";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getCreditsTest_oneMemberOneAssignmentNotDone() {
        Project testProject = new Project("Test Project");
        Task testTask = new Task("Test Task", 1, null, 1, TaskState.OPEN, null);
        testProject.addTask(testTask);
        Member testMember = new Member("Test Member", "--", "--", 1, "member");
        testProject.addMember(testMember);
        testProject.createAssignment(testTask, testMember);
        ArrayList<String> actualOutput = testProject.getCredits();
        String[] expectedOutput = new String[] {
            "1. Test Member: 0.0 credits",
            "   Progress: .................... (0%)"
        };
        assertEquals(expectedOutput[0], actualOutput.get(0));
        assertEquals(expectedOutput[1], actualOutput.get(1));
        assertEquals(expectedOutput.length, actualOutput.size());
    }

    @Test
    void getCreditsTest_noMember() {
        Project testProject = new Project("Test Project");
        ArrayList<String> actualOutput = testProject.getCredits();
        assertEquals(0, actualOutput.size());
    }

    @Test
    void getCreditsTest_oneMemberOneAssignmentDone() {
        Project testProject = new Project("Test Project");
        Task testTask = new Task("Test Task", 1, null, 1, TaskState.DONE, null);
        testProject.addTask(testTask);
        Member testMember = new Member("Test Member", "--", "--", 1, "member");
        testProject.addMember(testMember);
        testProject.createAssignment(testTask, testMember);
        ArrayList<String> actualOutput = testProject.getCredits();
        String[] expectedOutput = new String[] {"1. Test Member: 1.0 credits",
            "   Progress: #################### (100%)"};
        assertEquals(expectedOutput[0], actualOutput.get(0));
        assertEquals(expectedOutput[1], actualOutput.get(1));
        assertEquals(expectedOutput.length, actualOutput.size());
    }

    @Test
    void getCreditTest_twoMembersOneDoneAssignmentOneUndoneAssignment() {
        Project testProject = new Project("Test Project");
        Task testTask = new Task("Test Task", 1, null, 10, TaskState.OPEN, null);
        testProject.addTask(testTask);
        Task testTask2 = new Task("Test Task 2", 1, null, 20, TaskState.DONE, null);
        testProject.addTask(testTask2);
        Member testMember = new Member("Test Member", "--", "--", 1, "member");
        testProject.addMember(testMember);
        Member testMember2 = new Member("Test Member 2", "--", "--", 2, "member");
        testProject.addMember(testMember2);
        testProject.createAssignment(testTask, testMember);
        testProject.createAssignment(testTask2, testMember);
        testProject.createAssignment(testTask, testMember2);
        ArrayList<String> actualOutput = testProject.getCredits();
        String[] expectedOutput = new String[] {
            "1. Test Member: 10.0 credits",
            "   Progress: #############....... (65%)",
            "2. Test Member 2: 0.0 credits",
            "   Progress: .................... (0%)"};
        assertArrayEquals(expectedOutput, actualOutput.toArray(new String[0]));
    }

    @Test
    void getMemberFromIDTest_memberExist() {
        Project testProject = new Project("Test Project");
        Member testMember = new Member("Test Member", "--", "--", 1, "member");
        testProject.addMember(testMember);
        String memberID = testMember.getMemberID();
        Member outputMember = (Member) testProject.getMemberFromID(memberID);
        assertEquals(testMember, outputMember);
    }

    @Test
    void getMemberFromIDTest_memberDoesNotExist() {
        Project testProject = new Project("Test Project");
        Member testMember = new Member("Test Member", "--", "--", 1, "member");
        testProject.addMember(testMember);
        String memberID = "";
        IMember outputMember = testProject.getMemberFromID(memberID);
        assertNotEquals(testMember, outputMember);
    }

    @Test
    void getTaskFromIDTest_taskExists() {
        Project testProject = new Project("Test Project");
        Task testTask = new Task("Test Task", 1, null, 10, TaskState.OPEN, null);
        testProject.addTask(testTask);
        String taskID = testTask.getTaskID();
        ITask outputTask = testProject.getTaskFromID(taskID);
        assertEquals(testTask, outputTask);
    }

    @Test
    void getTaskFromIDTest_taskDoesNotExist() {
        Project testProject = new Project("Test Project");
        Task testTask = new Task("Test Task", 1, null, 10, TaskState.OPEN, null);
        testProject.addTask(testTask);
        String taskID = "";
        ITask outputTask = testProject.getTaskFromID(taskID);
        assertNotEquals(testTask, outputTask);
    }
}
