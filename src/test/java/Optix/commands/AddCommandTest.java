package optix.commands;

import optix.commands.shows.AddCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddCommandTest {

    private Ui ui = new Ui();
    private File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix.txt");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @Test
    void execute() {
        AddCommand testCommand = new AddCommand("dummy show name", "5/5/2020 | 6/10/2020", 20);

        testCommand.execute(model, ui, storage);
        String expected = "__________________________________________________________________________________\n"
                + "Noted. The following shows has been added:\n"
                + "1. dummy show name (on: 5/5/2020)\n"
                + "2. dummy show name (on: 6/10/2020)\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected, ui.showCommandLine());

        AddCommand testCommand2 = new AddCommand("dummy show name", "7/10/2020|6/10/2020", 20);

        testCommand2.execute(model, ui, storage);
        String expected2 = "__________________________________________________________________________________\n"
                + "Noted. The following shows has been added:\n"
                + "1. dummy show name (on: 7/10/2020)\n"
                + "\n"
                + "☹ OOPS!!! Unable to add the following shows:\n"
                + "1. dummy show name (on: 6/10/2020)\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected2, ui.showCommandLine());

        AddCommand testCommand3 = new AddCommand("dummy show name", "5/5/2020|6/10/2020", 20);

        testCommand3.execute(model, ui, storage);
        String expected3 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! Unable to add the following shows:\n"
                + "1. dummy show name (on: 5/5/2020)\n"
                + "2. dummy show name (on: 6/10/2020)\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected3, ui.showCommandLine());

        filePath.deleteOnExit();
    }
}