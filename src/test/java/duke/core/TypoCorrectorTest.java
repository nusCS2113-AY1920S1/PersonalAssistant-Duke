package duke.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Junit test for TypoCorrector class.
 *
 * @author HUANG XUAN KUN
 * @version 1.2
 */
public class TypoCorrectorTest {

    @Test
    void stringMatchTest() {
        ArrayList<String[]> testCases = new ArrayList<String[]>(
            Arrays.asList(
                new String[]{"b q e", "bye"},
                new String[]{"d uke ", "duke"},
                new String[]{"dbKe", "duke"},
                new String[]{"l si t patant", "list patients"},
                new String[]{"lsf vs ta kss", "list tasks"},
                new String[]{"DEe lte Pti ents:#12", "delete patient :#12"},
                new String[]{"deleot tasksa :task description", "delete task :task description"},
                new String[]{"delette   tasks:#1", "delete task :#1"},
                new String[]{"addd patents:name :nric :room :remark", "add patient :name :nric :room :remark"},
                new String[]{"dad tsak:a very long task name", "add task :a very long task name"},
                new String[]{"addd task :abc", "add task :abc"},
                new String[]{"asgn deadli task: #1 :12 :12/12/2019 1645", "assign deadline task : #1 :12 :12/12/2019 1645"},
                new String[]{"asSGn even tk :#1 :12 :12/12/2019 1645 :12/12/2020 1645",
                    "assign event task :#1 :12 :12/12/2019 1645 :12/12/2020 1645"},
                new String[]{"fi n d p atin t: #1", "find patient : #1"},
                new String[]{"u p da te pa i t en s :#12:Room:2A", "update patient :#12:Room:2A"}
            ));
        for (String[] testPair : testCases) {
            String correctedOutput = TypoCorrector.commandCorrection(testPair[0]);
            System.out.println("Input Command:     " + testPair[0]);
            System.out.println("Expected Command:  " + testPair[1]);
            System.out.println("Corrected Command: " + correctedOutput);
            System.out.println();
            assertEquals(testPair[1], correctedOutput);
        }

    }
}
