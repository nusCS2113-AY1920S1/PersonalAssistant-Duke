package optix.commands;

import optix.commands.shows.AddCommand;
import optix.commands.shows.DeleteOneCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteOneCommandTest {

    private Ui ui = new Ui();
    private File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix.txt");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @Test
    void execute() {
        AddCommand addTestShow1 = new AddCommand("Test Show 1", "5/5/2020", 20);
        addTestShow1.execute(model, ui, storage);
        DeleteOneCommand testCommand1 = new DeleteOneCommand("Test Show 1", "5/5/2020");
        testCommand1.execute(model, ui, storage);
        String expected1 = "__________________________________________________________________________________\n"
                + "Noted. The show <Test Show 1> scheduled on <5/5/2020> has been removed.\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());

        DeleteOneCommand testCommand2 = new DeleteOneCommand("Non-existent show", "4/5/2020");
        testCommand2.execute(model, ui, storage);
        String expected2 = "__________________________________________________________________________________\n"
                + "Unable to find show called <Non-existent show> scheduled on <4/5/2020>.\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected2, ui.showCommandLine());
        filePath.deleteOnExit();
    }
}