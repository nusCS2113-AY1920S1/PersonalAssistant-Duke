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

class ListCommandTest {
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
    @DisplayName("Empty list")
    void testEmptyList() {
        // testing for an empty show list
        ListCommand testCommand1 = new ListCommand();
        testCommand1.execute(model, ui, storage);
        String expected1 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! There are no shows in the near future.\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());
    }

    @Test
    @DisplayName("Positive test")
    void testValidList() {
        // testing for a filled show list
        AddCommand addShow1 = new AddCommand("dummy test 1|20|5/5/2020");
        addShow1.execute(model, ui, storage);
        AddCommand addShow2 = new AddCommand("dummy test 2|20|6/5/2020");
        addShow2.execute(model, ui, storage);
        ListCommand testCommand2 = new ListCommand();
        testCommand2.execute(model, ui, storage);
        String expected2 = "__________________________________________________________________________________\n"
                + "Here are the list of shows:\n"
                + "1. dummy test 1 (on: 05/05/2020)\n"
                + "2. dummy test 2 (on: 06/05/2020)\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected2, ui.showCommandLine());
    }

    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}