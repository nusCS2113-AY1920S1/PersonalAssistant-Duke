package helpertests;

import org.junit.jupiter.api.Test;
import util.uiformatter.ViewHelper;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.constant.ConstantHelper.DEFAULT_HORI_BORDER_LENGTH_70_FOR_TEST;

class ViewHelperTest {
    private ViewHelper viewHelper;

    //@@author seanlimhx
    ViewHelperTest() {
        this.viewHelper = new ViewHelper();
    }

    /**
     * Always true test just to test if jUnit is working.
     */
    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void consolePrintTableTest() {
        ArrayList<String> sampleTableContent = new ArrayList<>();
        sampleTableContent.add("Project Header");
        sampleTableContent.add("line 1");
        sampleTableContent.add("line 2");
        sampleTableContent.add("");
        sampleTableContent.add("line 3");
        sampleTableContent.add("line 4");
        sampleTableContent.add("");
        String[] actualOutput = viewHelper.consolePrintTable(sampleTableContent,
                DEFAULT_HORI_BORDER_LENGTH_70_FOR_TEST);
        String[] expectedOutput = new String[] {
            "+----------------------------------------------------------------------+",
            "|Project Header                                                        |",
            "+----------------------------------------------------------------------+",
            "|line 1                                                                |",
            "|line 2                                                                |",
            "|                                                                      |",
            "|line 3                                                                |",
            "|line 4                                                                |",
            "|                                                                      |",
            "+----------------------------------------------------------------------+"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    void consolePrintTableHoriBorderTest() {
        String expectedOutput = "+----------------------------------------------------------------------+";
        String actualOutput = viewHelper.consolePrintTableHoriBorder(DEFAULT_HORI_BORDER_LENGTH_70_FOR_TEST);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getRemainingSpacesTest() {
        String expectedOutput = "                    ";
        String actualOutput = viewHelper.getRemainingSpaces(20);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getArrayOfSplitStringsTest() {
        String input = " 1. A very very very long name (Phone: 12345678901234567890 | Email:"
                + " alonglonglongemailaddress@gmail.com | Role: member)";
        String[] expectedOutput = new String[] {" 1. A very very very long name (Phone: 12345678901234567890 | Email: ",
            "alonglonglongemailaddress@gmail.com | Role: member)"};
        String[] actualOutput = viewHelper.getArrayOfSplitStrings(input, DEFAULT_HORI_BORDER_LENGTH_70_FOR_TEST);
        assertArrayEquals(expectedOutput, actualOutput);

        String input2 = " 3. aVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryLongNameWi"
                + "thoutSpace (Phone: -- | Email: -- | Role: member)";
        String[] expectedOutput2 = new String[] {
            " 3. aVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryLongNameWithoutS-",
            "pace (Phone: -- | Email: -- | Role: member)"};
        String[] actualOutput2 = viewHelper.getArrayOfSplitStrings(input2, DEFAULT_HORI_BORDER_LENGTH_70_FOR_TEST);
        assertArrayEquals(expectedOutput2, actualOutput2);

        String input3 = " 4. A very very very very very very very very very very     "
                + "very very very very very very very very very very very very "
                + "very long text (Phone: -- | Email: -- | Role: member)";
        String[] expectedOutput3 = new String[] {
            " 4. A very very very very very very very very very very     very very ",
            "very very very very very very very very very very very long text ",
            "(Phone: -- | Email: -- | Role: member)"};
        String[] actualOutput3 = viewHelper.getArrayOfSplitStrings(input3, DEFAULT_HORI_BORDER_LENGTH_70_FOR_TEST);
        assertArrayEquals(expectedOutput3, actualOutput3);
    }

    @Test
    void consolePrintMultipleTablesTest_oneColumn() {
        ArrayList<String> sampleTableContent1 = new ArrayList<>();
        sampleTableContent1.add("Project 1: Test");
        sampleTableContent1.add("Members:");
        sampleTableContent1.add(" --");
        sampleTableContent1.add("Next Deadline:");
        sampleTableContent1.add(" - No deadlines left -");
        sampleTableContent1.add("");
        sampleTableContent1.add("Overall Progress:");
        sampleTableContent1.add(" - Completed: 0%");
        sampleTableContent1.add(" - In Progress: 0%");
        sampleTableContent1.add(" - Not Done: 100%");
        ArrayList<String> sampleTableContent2 = new ArrayList<>();
        sampleTableContent2.add("Project 2: Another Test");
        sampleTableContent2.add("Members:");
        sampleTableContent2.add(" --");
        sampleTableContent2.add("Next Deadline:");
        sampleTableContent2.add(" --");
        sampleTableContent2.add("Overall Progress:");
        sampleTableContent2.add(" --");
        ArrayList<ArrayList<String>> input = new ArrayList<>();
        input.add(sampleTableContent1);
        input.add(sampleTableContent2);
        String[] actualOutput = viewHelper.consolePrintMultipleTables(input, DEFAULT_HORI_BORDER_LENGTH_70_FOR_TEST,
                1, "Here are all the Projects you are managing:");
        String[] expectedOutput = new String[] {
            "+----------------------------------------------------------------------+",
            "|Here are all the Projects you are managing:                           |",
            "+----------------------------------------------------------------------+",
            "| +------------------------------------------------------------------+ |",
            "| |Project 1: Test                                                   | |",
            "| +------------------------------------------------------------------+ |",
            "| |Members:                                                          | |",
            "| | --                                                               | |",
            "| |Next Deadline:                                                    | |",
            "| | - No deadlines left -                                            | |",
            "| |                                                                  | |",
            "| |Overall Progress:                                                 | |",
            "| | - Completed: 0%                                                  | |",
            "| | - In Progress: 0%                                                | |",
            "| | - Not Done: 100%                                                 | |",
            "| +------------------------------------------------------------------+ |",
            "| +------------------------------------------------------------------+ |",
            "| |Project 2: Another Test                                           | |",
            "| +------------------------------------------------------------------+ |",
            "| |Members:                                                          | |",
            "| | --                                                               | |",
            "| |Next Deadline:                                                    | |",
            "| | --                                                               | |",
            "| |Overall Progress:                                                 | |",
            "| | --                                                               | |",
            "| +------------------------------------------------------------------+ |",
            "+----------------------------------------------------------------------+"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    void consolePrintMultipleTablesTest_twoColumns() {
        ArrayList<String> sampleTableContent1 = new ArrayList<>();
        sampleTableContent1.add("test task (P: 1, D: --, C: 2, S: OPEN)");
        sampleTableContent1.add("1. Cynthia");
        sampleTableContent1.add("2. Dillen");
        sampleTableContent1.add("3. Jerry");
        ArrayList<String> sampleTableContent2 = new ArrayList<>();
        sampleTableContent2.add("Watch show (P: 5, D: 01 Nov 2019, C: 3, S: DOING)");
        sampleTableContent2.add("1. Dillen");
        sampleTableContent2.add("2. Jerry");
        ArrayList<String> sampleTableContent3 = new ArrayList<>();
        sampleTableContent3.add("Eat food (P: 1, D: 03 Nov 2019, C: 3, S: OPEN)");
        sampleTableContent3.add("1. Cynthia");
        sampleTableContent3.add("2. Jerry");
        ArrayList<ArrayList<String>> input = new ArrayList<>();
        input.add(sampleTableContent1);
        input.add(sampleTableContent2);
        input.add(sampleTableContent3);
        String[] actualOutput = viewHelper.consolePrintMultipleTables(input, DEFAULT_HORI_BORDER_LENGTH_70_FOR_TEST,
                2, "Here are the members assigned to each task:");
        String[] expectedOutput = new String[] {
            "+----------------------------------------------------------------------+",
            "|Here are the members assigned to each task:                           |",
            "+----------------------------------------------------------------------+",
            "| +-------------------------------+ +-------------------------------+  |",
            "| |test task (P: 1, D: --, C: 2,  | |Watch show (P: 5, D: 01 Nov    |  |",
            "| |S: OPEN)                       | |2019, C: 3, S: DOING)          |  |",
            "| +-------------------------------+ +-------------------------------+  |",
            "| |1. Cynthia                     | |1. Dillen                      |  |",
            "| |2. Dillen                      | |2. Jerry                       |  |",
            "| |3. Jerry                       | +-------------------------------+  |",
            "| +-------------------------------+ +-------------------------------+  |",
            "|                                   |Eat food (P: 1, D: 03 Nov      |  |",
            "|                                   |2019, C: 3, S: OPEN)           |  |",
            "|                                   +-------------------------------+  |",
            "|                                   |1. Cynthia                     |  |",
            "|                                   |2. Jerry                       |  |",
            "|                                   +-------------------------------+  |",
            "+----------------------------------------------------------------------+"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    //@@author Lucria
    @Test
    void consolePrintCalender_successfulExecution() {
        HashMap<Integer, Integer> simulatedInput = new HashMap<>();
        String[] simulatedOutput = viewHelper.consolePrintCalender(simulatedInput);
        String[] expectedOutput = new String[]{
            "+----------------------------------------------------------------------+",
            "|    Today's date is 8 11 2019                                         |",
            "+----------------------------------------------------------------------+",
            "|        U        M        T        W        R        F        S       |",
            "|                                                     1        2       |",
            "|                                                                      |",
            "|        3        4        5        6        7        8        9       |",
            "|                                                                      |",
            "|       10       11       12       13       14       15       16       |",
            "|                                                                      |",
            "|       17       18       19       20       21       22       23       |",
            "|                                                                      |",
            "|       24       25       26       27       28       29       30       |",
            "+----------------------------------------------------------------------+"
        };
        assertArrayEquals(expectedOutput, simulatedOutput);
    }
}
