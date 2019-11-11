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
            new String[]{"by e", "bye"},
            new String[]{"list patant", "list patients"},
            new String[]{"DE lte Ptients:#12", "delete patient:#12"},
            new String[]{"deleot tasksa :task description", "delete task:task description"},
            new String[]{"delette   tasks:#1", "delete task:#1"},
            new String[]{"addd patents:name :nric :room :remark", "add patient:name :nric :room :remark"},
            new String[]{"addd task :abc", "add task:abc"},
            new String[]{"asgn deadli task: #1 :12 :12/12/2019 1645",
                "assign deadline task: #1 :12 :12/12/2019 1645"},
            new String[]{"asSGn perio tk :#1 :12 :12/12/2019 1645 :12/12/2020 1645",
                "assign period task:#1 :12 :12/12/2019 1645 :12/12/2020 1645"},
            new String[]{"fi n d p atin t: #1", "find patient: #1"},
            new String[]{"up date pait ent :#12:Room:2A", "update patient:#12:Room:2A"}
        ));

    private static final String[] NOT_CORRECTED_COMMANDS = {
        "", " ", "list patients", "an invalid command", "b yyy e"
    };

    /**
     * Test commands which should be corrected.
     */
    @Test
    void commandCorrectionTest() {
        for (String[] testPair : COMMANDS_TO_BE_CORRECTED) {
            TypoCorrector typoCorrector = new TypoCorrector(testPair[0]);
            assertTrue(typoCorrector.isCommandCorrected(),
                "The command < " + testPair[0] + " > should be corrected to < " + testPair[1] + " >");
            String correctedOutput = typoCorrector.getCorrectedCommand();
            assertEquals(testPair[1], correctedOutput);

            //System.out.println for debugging purposes in test
            System.out.println("Input Command:     " + testPair[0]);
            System.out.println("Expected Command:  " + testPair[1]);
            System.out.println("Corrected Command: " + correctedOutput + "\n");
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
            assertEquals(command, correctedOutput);

            //System.out.println for debugging purposes in test
            System.out.println("Input Command:     " + command);
            System.out.println("Expected Command:  " + command);
            System.out.println("Corrected Command: " + correctedOutput);
            System.out.println();
        }
    }
}
