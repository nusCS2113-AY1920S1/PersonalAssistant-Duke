package optix.commands;

import optix.commands.shows.AddCommand;
import optix.commands.shows.ListCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCommandTest {

    private Ui ui = new Ui();
    private File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix.txt");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @Test
    void execute() {
        // testing for an empty show list
        ListCommand testCommand1 = new ListCommand();
        testCommand1.execute(model, ui, storage);
        String expected1 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! There are no shows in the near future.\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());

        // testing for a filled show list
        AddCommand addShow1 = new AddCommand("dummy test 1", "5/5/2020", 20);
        addShow1.execute(model, ui, storage);
        AddCommand addShow2 = new AddCommand("dummy test 2", "6/5/2020", 20);
        addShow2.execute(model, ui, storage);
        ListCommand testCommand2 = new ListCommand();
        testCommand2.execute(model, ui, storage);
        String expected2 = "__________________________________________________________________________________\n"
                + "Here are the list of shows:\n"
                + "1. dummy test 1 (on: 05/05/2020)\n"
                + "2. dummy test 2 (on: 06/05/2020)\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected2, ui.showCommandLine());
        filePath.deleteOnExit();
    }
}