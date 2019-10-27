package optix.commands.shows;

import optix.commands.shows.AddCommand;
import optix.commands.shows.DeleteCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteCommandTest {
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
    @DisplayName("Valid Deletion Test")
    void validDeleteTest() { // adding a show and deleting the same show
        new AddCommand("Test Show 1|20|5/5/2020|6/5/2020").execute(model, ui, storage);
        DeleteCommand testCommand1 = new DeleteCommand("Test Show 1|5/5/2020|6/5/2020");
        testCommand1.execute(model, ui, storage);
        String expected1 = "__________________________________________________________________________________\n"
                + "Noted. The following shows has been deleted:\n"
                + "1. Test Show 1 (on: 5/5/2020)\n"
                + "2. Test Show 1 (on: 6/5/2020)\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());
    }

    @Test
    @DisplayName("Invalid Deletion Test")
    void invalidDeleteTest() { // deleting a show that does not exist
        DeleteCommand testCommand2 = new DeleteCommand("Non-existent show|4/5/2020");
        testCommand2.execute(model, ui, storage);
        String expected2 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! Unable to find the following shows:\n"
                + "1. Non-existent show (on: 4/5/2020)\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected2, ui.showCommandLine());
    }

    @Test
    @DisplayName("No Details Test")
    void testNoDetails() {
        new DeleteCommand("").execute(model, ui, storage); // No details
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @AfterEach
    void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}