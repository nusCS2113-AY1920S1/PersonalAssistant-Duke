package optix.commands.shows;

import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class AddCommandTest {
    private Ui ui = new Ui();
    private static File currentDir = new File(System.getProperty("user.dir"));
    private static File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @Test
    @DisplayName("Invalid Command test")
    void testParseDetails() {
        AddCommand c = new AddCommand("Test Show|20"); //test has less than 3 parameter
        c.execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    // need to figure how to do this test.
    @Test
    @Disabled
    void testCostParameter() {
        try {
            AddCommand c = new AddCommand("Test Show|-20|5/5/2030"); //test seatBastPrice < 0
            c.execute(model, ui, storage);
            fail("Following test should fail");
        } catch (final RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Invalid Show Date Test")
    void testInvalidShowDate() {
        AddCommand c = new AddCommand("Test Show|20|5/13/2030|hello"); // test invalid date format
        c.execute(model, ui, storage);
        String expected = "☹ OOPS!!! Unable to add the following shows:\n"
                          + "1. Test Show (on: 5/13/2030)\n"
                          + "2. Test Show (on: hello)\n";
        assertEquals(expected, ui.getMessage());

        c = new AddCommand("Test Show|20|5/5/2030"); //test date clash
        c.execute(model, ui, storage);
        c.execute(model, ui, storage);
        expected = "☹ OOPS!!! Unable to add the following shows:\n"
                + "1. Test Show (on: 5/5/2030)\n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("AddCommand without exception")
    void testAddShowExecute() {
        AddCommand c = new AddCommand("TestShow|20|6/5/2030|7/5/2030"); //test successful execution.
        c.execute(model, ui, storage);
        String expected = "Noted. The following shows has been added:\n"
                          + "1. TestShow (on: 6/5/2030)\n"
                          + "2. TestShow (on: 7/5/2030)\n";
        assertEquals(expected, ui.getMessage());
    }

    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}