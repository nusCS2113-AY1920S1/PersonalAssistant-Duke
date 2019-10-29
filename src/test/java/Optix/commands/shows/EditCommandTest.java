package optix.commands.shows;

import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditCommandTest {
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
    @DisplayName("Editing non existent show")
    void testNoShow() {
        new EditCommand("Non existent show|5/5/2020|Test Show").execute(model, ui, storage);
        String expected = "☹ OOPS!!! The show you are finding does not exist!\n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Incomplete Details")
    void testIncompleteDetails() {
        new AddCommand("Test Show|5|5/5/2030").execute(model, ui, storage);
        new EditCommand("Test Show").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("No Details Test")
    void testNoDetails() {
        new EditCommand("").execute(model, ui, storage); // No details
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Invalid Date Details")
    void testInvalidDate() {
        new AddCommand("Test Show|5|5/5/2030").execute(model, ui, storage);
        new EditCommand("Test Show|5/13/2020|Test Show 1").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid date.\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Valid edit")
    void testValidEdit() {
        // add test shows
        AddCommand addTestShow1 = new AddCommand("Test Show 1|20|5/5/2020");
        addTestShow1.execute(model, ui, storage);
        EditCommand testCommand = new EditCommand("Test Show 1|5/5/2020|Test Show 3");
        testCommand.execute(model, ui, storage);
        String expected1 = "__________________________________________________________________________________\n"
                + "Show has been successfully updated to Test Show 3.\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());
    }

    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}