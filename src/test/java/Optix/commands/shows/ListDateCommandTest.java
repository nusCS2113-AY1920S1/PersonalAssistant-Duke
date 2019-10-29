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

class ListDateCommandTest {
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
    @DisplayName("Test for no results")
    void testNoResults() {
        // try looking for a show that does not exist
        ListDateCommand testCommand1 = new ListDateCommand("december 2020");
        testCommand1.execute(model, ui, storage);
        String expected1 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! There are no shows on December 2020.\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());
    }

    @Test
    @DisplayName("Positive Test")
    void testValidListDate() {
        // insert dummy show
        AddCommand insertDummyShow1 = new AddCommand("Dummy Show|20|5/5/2020");
        insertDummyShow1.execute(model, ui, storage);
        AddCommand insertDummyShow2 = new AddCommand("Dummy Show|20|6/5/2020");
        insertDummyShow2.execute(model, ui, storage);
        // attempt to view dummy show.
        ListDateCommand testCommand2 = new ListDateCommand("May 2020");
        testCommand2.execute(model, ui, storage);
        String expected2 = "__________________________________________________________________________________\n"
                + "These shows are showing on May 2020: \n"
                + "1. Dummy Show (on: 05/05/2020)\n"
                + "2. Dummy Show (on: 06/05/2020)\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected2, ui.showCommandLine());
    }

    @Test
    @DisplayName("Invalid Date")
    void testInvalidDate() {
        new AddCommand("Test Show|5|5/5/2030").execute(model, ui, storage);
        new ListDateCommand("ocotber 2020").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid date.\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Incomplete Details")
    void testIncompleteDetails() {
        new AddCommand("Test Show|5|5/5/2030").execute(model, ui, storage);
        new ListDateCommand("May").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}