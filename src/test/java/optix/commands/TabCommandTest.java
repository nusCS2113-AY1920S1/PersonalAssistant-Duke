package optix.commands;

import optix.commands.shows.AddCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TabCommandTest {
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
        new AddCommand("Test Show|20|5/5/2020|6/5/2020|7/5/2020").execute(model, ui, storage);
    }

    @Test
    @DisplayName("No details parsed into command")
    void testEmptyParameter() {
        new TabCommand("").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Valid Command parameter")
    void testExecute() {
        new TabCommand("archive").execute(model, ui, storage);
        assertEquals("Here is your list of archived shows.\n"
                + model.listShowHistory(), ui.getMessage());
        assertEquals(model.getShowsHistory(), model.getShowsGui());
        new TabCommand("finance").execute(model, ui, storage);
        assertEquals("Here is your list of projected earnings.\n"
                + "1. Test Show (on: 05/05/2020): $0.00\n"
                + "2. Test Show (on: 06/05/2020): $0.00\n"
                + "3. Test Show (on: 07/05/2020): $0.00\n", ui.getMessage());
        new TabCommand("help").execute(model, ui, storage);
        assertEquals("Here are the list of commands you can use.\n", ui.getMessage());
        assertEquals(model.getShows(), model.getShowsGui());
        new TabCommand("random command").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
        assertEquals(model.getShows(), model.getShowsGui());
    }

    @AfterEach
    void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}