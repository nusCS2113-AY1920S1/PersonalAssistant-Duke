package helpertests;

import org.junit.jupiter.api.Test;
import util.ViewHelper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.constant.ConstantHelper.DEFAULT_HORI_BORDER_LENGTH_70_FOR_TEST;

public class ViewHelperTest {
    private ViewHelper viewHelper;

    //@@author seanlimhx
    public ViewHelperTest() {
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
        ArrayList<ArrayList<String>> input = new ArrayList<>();
        input.add(sampleTableContent);
        String[] actualOutput = viewHelper.consolePrintTable(input, DEFAULT_HORI_BORDER_LENGTH_70_FOR_TEST);
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
        String[] expectedOutput2 = new String[] {" 3. ",
            "aVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryLongNameWithoutSpace ",
            "(Phone: -- | Email: -- | Role: member)"};
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

}
