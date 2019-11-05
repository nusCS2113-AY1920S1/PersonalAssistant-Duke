package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import models.member.Member;
import models.project.Project;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Test;
import util.ParserHelper;

public class ParserHelperTest {
    private final Project project;
    private final Member member1;
    private final Member member2;
    private final Member member3;

    //@@author sinteary
    ParserHelperTest() {
        this.project = new Project("Test Project");
        this.member1 = new Member("Tom", "NIL", "NIL", 1, "member");
        this.member2 = new Member("Dick", "NIL", "NIL", 2, "member");
        this.member3 = new Member("Harry", "NIL", "NIL", 3, "member");

        Task task1 = new Task("Test task 1", 0,null, 0, TaskState.OPEN, new ArrayList<>());
        Task task2 = new Task("Test task 2", 0,null, 0, TaskState.OPEN, new ArrayList<>());
        this.project.addMember(member1);
        this.project.addMember(member2);
        this.project.addMember(member3);
        this.project.addTask(task1);
        this.project.addTask(task2);
    }

    @Test
    public void testParseAssignmentParams() {
        String command = "-i 1 2 -to 3 4";
        ParserHelper parserHelper = new ParserHelper();
        ArrayList<ArrayList<Integer>> parsedCommands = parserHelper.parseAssignmentParams(command, project);
        assertEquals(2, parsedCommands.get(0).size());
        assertEquals(1, parsedCommands.get(1).size());
        assertEquals(0, parsedCommands.get(2).size());
        assertTrue(parsedCommands.get(0).contains(1));
        assertTrue(parsedCommands.get(0).contains(2));
        assertTrue(parsedCommands.get(1).contains(3));
        assertEquals(1, parserHelper.getErrorMessages().size());
        assertEquals("Member with index 4 does not exist.", parserHelper.getErrorMessages().get(0));

        command = "-i 3 -to 1 2 -rm 3";
        parsedCommands = parserHelper.parseAssignmentParams(command, project);
        assertEquals(0, parsedCommands.get(0).size());
        assertEquals(2, parsedCommands.get(1).size());
        assertEquals(1, parsedCommands.get(2).size());
        assertTrue(parsedCommands.get(1).contains(1));
        assertTrue(parsedCommands.get(1).contains(2));
        assertTrue(parsedCommands.get(2).contains(3));
        assertEquals("Task with index 3 does not exist.", parserHelper.getErrorMessages().get(0));

        command = "-i 1 -to 1 -rm 1";
        parsedCommands = parserHelper.parseAssignmentParams(command, project);
        assertEquals(1, parsedCommands.get(0).size());
        assertEquals(0, parsedCommands.get(1).size());
        assertEquals(0, parsedCommands.get(2).size());
        assertEquals("Cannot assign and unassign task to member 1 (Tom) at the same time",
            parserHelper.getErrorMessages().get(0));

        command = "-i abc -to 1 -rm 2";
        parsedCommands = parserHelper.parseAssignmentParams(command, project);
        assertEquals(0, parsedCommands.get(0).size());
        assertEquals(1, parsedCommands.get(1).size());
        assertEquals(1, parsedCommands.get(2).size());
        assertEquals("Could not recognise task abc, please ensure it is an integer.",
            parserHelper.getErrorMessages().get(0));

        command = "-i 1 -to ";
        parsedCommands = parserHelper.parseAssignmentParams(command, project);
        assertEquals(1, parsedCommands.get(0).size());
        assertEquals(0, parsedCommands.get(1).size());

        command = "-i 5 -to ";
        parsedCommands = parserHelper.parseAssignmentParams(command, project);
        assertEquals(0, parsedCommands.get(0).size());
        assertEquals(0, parsedCommands.get(1).size());
        assertEquals(0, parsedCommands.get(2).size());
        assertEquals("Task with index 5 does not exist.", parserHelper.getErrorMessages().get(0));

        command = "-i";
        parsedCommands = parserHelper.parseAssignmentParams(command, project);
        assertEquals(0, parsedCommands.get(0).size());
        assertEquals(0, parsedCommands.get(1).size());
        assertEquals(0, parsedCommands.get(2).size());
    }

    @Test
    public void testParseMembersIndex() {
        String command = "0 1 2 abc 3 4 -1 9999999999999999999";
        ParserHelper parserHelper = new ParserHelper();
        ArrayList<Integer> validMemberIndexes = parserHelper.parseMembersIndexes(command, 3);
        assertTrue(validMemberIndexes.size() == 3);
        assertTrue(validMemberIndexes.contains(1));
        assertTrue(validMemberIndexes.contains(2));
        assertTrue(validMemberIndexes.contains(3));
        ArrayList<String> errorMessages = parserHelper.getErrorMessages();
        assertTrue(errorMessages.contains("Could not recognise member abc, please ensure it is an integer."));
        assertTrue(errorMessages.contains("Member with index 0 does not exist."));
        assertTrue(errorMessages.contains("Member with index 4 does not exist."));
        assertTrue(errorMessages.contains("Member with index -1 does not exist."));
        assertTrue(errorMessages.contains("Could not recognise member 9999999999999999999, please ensure it is an integer."));
    }

    @Test
    public void testParseTasksIndex() {
        String command = "0 1 2 def 3 4 -1 9999999999999999999";
        ParserHelper parserHelper = new ParserHelper();
        ArrayList<Integer> validTaskIndexes = parserHelper.parseTasksIndexes(command, 3);
        assertTrue(validTaskIndexes.size() == 3);
        assertTrue(validTaskIndexes.contains(1));
        assertTrue(validTaskIndexes.contains(2));
        assertTrue(validTaskIndexes.contains(3));
        ArrayList<String> errorMessages = parserHelper.getErrorMessages();
        assertTrue(errorMessages.contains("Could not recognise task def, please ensure it is an integer."));
        assertTrue(errorMessages.contains("Task with index 0 does not exist."));
        assertTrue(errorMessages.contains("Task with index 4 does not exist."));
        assertTrue(errorMessages.contains("Could not recognise task 9999999999999999999, please ensure it is an integer."));
        assertTrue(errorMessages.contains("Task with index -1 does not exist."));
    }



}
