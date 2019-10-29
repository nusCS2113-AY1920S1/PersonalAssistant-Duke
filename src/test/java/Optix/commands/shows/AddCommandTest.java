package optix.commands.shows;

import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddCommandTest {
    private Ui ui;
    private static File currentDir = new File(System.getProperty("user.dir"));
    private static File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix");
    private Storage storage;
    private Model model;

    @BeforeEach
    void init() {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.model = new Model(storage);
    }

    @Test
    @DisplayName("Invalid Command test")
    void testParseDetails() {
        AddCommand c = new AddCommand("Test Show|20"); //test has less than 3 parameter
        c.execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("No Details Test")
    void testNoDetails() {
        new AddCommand("").execute(model, ui, storage); // No details
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Negative Base Price Test")
    void testNegativePrice() {
        AddCommand c = new AddCommand("Test Show|-5|2/12/2030"); // test has negative seat base price
        c.execute(model, ui, storage);
        String expected = "Seat base price cannot be negative.\n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Empty Seat Base Price Test")
    void testEmptyPrice() {
        AddCommand c = new AddCommand("Test Show||2/12/2030"); // no seat base price
        c.execute(model, ui, storage);
        String expected = "Please set a number for the seat base price.\n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Invalid Show Date Test")
    void testInvalidShowDate() {
        AddCommand c = new AddCommand("Test Show|20|29/2/2021|hello"); // test invalid date format
        c.execute(model, ui, storage);
        String expected = "☹ OOPS!!! Unable to add the following shows:\n"
                + "1. Test Show (on: 29/2/2021)\n"
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
        AddCommand c = new AddCommand("TestShow|20|6/5/2030|7/6/2030"); //test successful execution.
        c.execute(model, ui, storage);
        String expected = "Noted. The following shows has been added:\n"
                + "1. TestShow (on: 6/5/2030)\n"
                + "2. TestShow (on: 7/6/2030)\n";
        assertEquals(expected, ui.getMessage());

        c = new AddCommand("Test Show|20|6/5/2030|20/5/2030");
        c.execute(model, ui, storage);
        expected = "Noted. The following shows has been added:\n"
                + "1. Test Show (on: 20/5/2030)\n"
                + "\n"
                + "☹ OOPS!!! Unable to add the following shows:\n"
                + "1. Test Show (on: 6/5/2030)\n";
        assertEquals(expected, ui.getMessage());
    }

    @AfterEach
    void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}