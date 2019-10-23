package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import util.ParserHelper;

public class ParserHelperTest {
    @Test
    public void testParseAssignmentInputHelper() {
        String command = "-i 1 2 -to 3 4 -rm 5 6 7";
        ArrayList<ArrayList<String>> parsedCommands =
            new ParserHelper().parseAssignmentInputHelper(command);
        assertEquals(2, parsedCommands.get(0).size());
        assertEquals(3, parsedCommands.get(1).size());
        assertEquals(2, parsedCommands.get(2).size());
        assertTrue(parsedCommands.get(0).contains("3"));
        assertTrue(parsedCommands.get(0).contains("4"));
        assertTrue(parsedCommands.get(1).contains("5"));
        assertTrue(parsedCommands.get(1).contains("6"));
        assertTrue(parsedCommands.get(1).contains("7"));
        assertTrue(parsedCommands.get(2).contains("1"));
        assertTrue(parsedCommands.get(2).contains("2"));
    }

    @Test
    public void testParseMembersIndex() {
        String command = "0 1 2 abc 3 4";
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
    }

    @Test
    public void testParseTasksIndex() {
        String command = "0 1 2 def 3 4";
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
    }


}
