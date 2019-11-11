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

class ListShowCommandTest {
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
    @DisplayName("Show Not Found")
    void testShowNotFound() {
        new AddCommand("Dummy Show|20|5/5/2020|6/10/2020").execute(model, ui, storage);
        new ListShowCommand("").execute(model, ui, storage); //empty Show Name
        assertEquals("☹ OOPS!!! The show cannot be found.\n", ui.getMessage());
        new ListShowCommand("Dummy").execute(model, ui, storage); //show cannot be found.
        assertEquals("☹ OOPS!!! The show cannot be found.\n", ui.getMessage());
    }

    @Test
    @DisplayName("Positive Test")
    void testValidListShow() {
        new AddCommand("Dummy Show|20|5/5/2020|6/10/2020").execute(model, ui, storage);
        new ListShowCommand("Dummy Show").execute(model, ui, storage);
        String expected = "The show Dummy Show is showing on the following following dates: \n"
                          + "1. 05/05/2020\n"
                          + "2. 06/10/2020\n";
        assertEquals(expected, ui.getMessage());
    }

    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}