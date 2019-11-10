package duke.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

//@@author HUANGXUANKUN
/**
 * Junit test for TypoCorrector class.
 *
 * @author HUANG XUAN KUN
 * @version 1.4
 */
public class TypoCorrectorTest {

    //ArrayList of pairs <command, commandToBeCorrected>
    private static final ArrayList<String[]> COMMANDS_TO_BE_CORRECTED = new ArrayList<String[]>(
        Arrays.asList(
            new String[]{"b y e", "bye"},
            new String[]{"d uke ", "duke"},
            new String[]{"lsit patant", "list patients"},
            new String[]{"DEe lte Pti ents:#12", "delete patient:#12"},
            new String[]{"deleot tasksa :task description", "delete task:task description"},
            new String[]{"delette   tasks:#1", "delete task:#1"},
            new String[]{"addd patents:name :nric :room :remark", "add patient:name :nric :room :remark"},
            new String[]{"dad tsak:a very long task name", "add task:a very long task name"},
            new String[]{"addd task :abc", "add task:abc"},
            new String[]{"asgn deadli task: #1 :12 :12/12/2019 1645",
                "assign deadline task: #1 :12 :12/12/2019 1645"},
            new String[]{"asSGn perio tk :#1 :12 :12/12/2019 1645 :12/12/2020 1645",
                "assign period task:#1 :12 :12/12/2019 1645 :12/12/2020 1645"},
            new String[]{"fi n d p atin t: #1", "find patient: #1"},
            new String[]{"u p da te pa i t en s :#12:Room:2A", "update patient:#12:Room:2A"}
        ));

    private static final String[] NOT_CORRECTED_COMMANDS = {
        "", " ", "list patients", "an invalid command"
    };

    /**
     * Test commands which should be corrected.
     */
    @Test
    void commandCorrectionTest() {
        for (String[] testPair : COMMANDS_TO_BE_CORRECTED) {
            TypoCorrector typoCorrector = new TypoCorrector(testPair[0]);
            assertTrue(typoCorrector.isCommandCorrected(),
                "The command < " + testPair[0] + " > should not be corrected to < " + testPair[1] + " >");
            String correctedOutput = typoCorrector.getCorrectedCommand();
            System.out.println("Input Command:     " + testPair[0]);
            System.out.println("Expected Command:  " + testPair[1]);
            System.out.println("Corrected Command: " + correctedOutput + "\n");
            assertEquals(testPair[1], correctedOutput);
        }
    }


    /**
     * Test commands which should not be corrected.
     * Use test cases which contain unrecognised or valid command.
     */
    @Test
    void unCorrectedCommandTest() {
        for (String command : NOT_CORRECTED_COMMANDS) {
            TypoCorrector typoCorrector = new TypoCorrector(command);
            assertFalse(typoCorrector.isCommandCorrected(),
                "The command: " + command + " should not be corrected");
            String correctedOutput = typoCorrector.getCorrectedCommand();
            System.out.println("Input Command:     " + command);
            System.out.println("Expected Command:  " + command);
            System.out.println("Corrected Command: " + correctedOutput);
            System.out.println();
            assertEquals(command, correctedOutput);
        }
    }


}
