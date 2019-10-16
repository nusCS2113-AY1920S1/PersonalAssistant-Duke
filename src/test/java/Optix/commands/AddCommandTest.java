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
        AddCommand testCommand = new AddCommand("dummy show name", "5/5/2020", 20);

        testCommand.execute(model, ui, storage);
        String expected = "__________________________________________________________________________________\n"
                + "Got it. I've added this show:\n"
                + "dummy show name on 5/5/2020\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected, ui.showCommandLine());
        filePath.deleteOnExit();
    }
}