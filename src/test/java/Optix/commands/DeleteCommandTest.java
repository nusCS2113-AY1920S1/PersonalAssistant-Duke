package optix.commands;

import optix.commands.shows.AddCommand;
import optix.commands.shows.DeleteCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidCommandException;
import optix.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteCommandTest {

    private Ui ui = new Ui();
    private File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix.txt");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @Test
    void execute() throws OptixInvalidCommandException {
        AddCommand addTestShow1 = new AddCommand("Test Show 1|20|5/5/2020|6/5/2020");
        addTestShow1.execute(model, ui, storage);
        DeleteCommand testCommand1 = new DeleteCommand("Test Show 1|5/5/2020|6/5/2020");
        testCommand1.execute(model, ui, storage);
        String expected1 = "__________________________________________________________________________________\n"
                + "Noted. The following shows has been deleted:\n"
                + "1. Test Show 1 (on: 5/5/2020)\n"
                + "2. Test Show 1 (on: 6/5/2020)\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());

        DeleteCommand testCommand2 = new DeleteCommand("Non-existent show|4/5/2020");
        testCommand2.execute(model, ui, storage);
        String expected2 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! Unable to find the following shows:\n"
                + "1. Non-existent show (on: 4/5/2020)\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected2, ui.showCommandLine());

        addTestShow1.execute(model, ui, storage);
        DeleteCommand testCommand3 = new DeleteCommand("Test Show 1|6/5/2020|5/5/2020|2/5/2020");
        testCommand3.execute(model, ui, storage);
        String expected3 = "__________________________________________________________________________________\n"
                + "Noted. The following shows has been deleted:\n"
                + "1. Test Show 1 (on: 6/5/2020)\n"
                + "2. Test Show 1 (on: 5/5/2020)\n"
                + "\n"
                + "☹ OOPS!!! Unable to find the following shows:\n"
                + "1. Test Show 1 (on: 2/5/2020)\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected3, ui.showCommandLine());


        filePath.deleteOnExit();
    }
}