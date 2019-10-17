package duke.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Junit test for TypoCorrector class
 * @author  HUANG XUAN KUN
 * @version 1.2
 */
public class TypoCorrectorTest {

    @Test
    void StringMatchTest(){
        ArrayList<String[]> testCases = new ArrayList<String[]>(
                Arrays.asList(new String[]{"beyee", "bye"},
                        new String[]{"bqe", "bye"},
                        new String[]{"lsit patant", "list patients"},
                        new String[]{"lsfvs takss", "list tasks"},
                        new String[]{"deelte Ptients #12", "delete patient #12"},
                        new String[]{"deleot tasksa task description", "delete task task description"},
                        new String[]{"delette tasks #1", "delete task #1"},
                        new String[]{"addd patents name nric room remark", "add patient name nric room remark"},
                        new String[]{"dad tsak a very long task name", "add task a very long task name"},
                        new String[]{"addd task abc", "add task abc"}
                        ));
        for (String[] testPair : testCases){
            String correctedOutput = TypoCorrector.CommandCorrection(testPair[0]);
            System.out.println("Input Command: "+ testPair[0]);
            System.out.println("Expected Command: " + testPair[1]);
            System.out.println("Corrected Command: "+ correctedOutput);
            System.out.println();
            assertEquals(testPair[1], correctedOutput);
        }

    }
}
