package optix.commands;

import optix.commands.shows.AddCommand;
import optix.commands.shows.DeleteAllCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteAllCommandTest {

    private Ui ui = new Ui();
    private File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix.txt");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @Test
    void execute() {
        AddCommand addTestShow1 = new AddCommand("Test Show 1", "5/5/2020", 20);
        AddCommand addTestShow2 = new AddCommand("Test Show 2", "6/5/2020", 50);
        addTestShow1.execute(model, ui, storage);
        addTestShow2.execute(model, ui, storage);
        DeleteAllCommand testCommand = new DeleteAllCommand(new String[]{"Test Show 1", "Test Show 2", "Intentionally missing show"});
        testCommand.execute(model, ui, storage);
        String expected = "__________________________________________________________________________________\n"
                + "Noted. These are the deleted entries:\n"
                + "2020-05-05 Test Show 1\n"
                + "2020-05-06 Test Show 2\n"
                + "Sorry, these shows were not found:\n"
                + "Intentionally missing show\n"
                + "__________________________________________________________________________________\n";

        assertEquals(expected, ui.showCommandLine());
        filePath.deleteOnExit();
    }
}