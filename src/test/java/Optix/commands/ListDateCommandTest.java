package optix.commands;

import optix.commands.shows.AddCommand;
import optix.commands.shows.ListDateCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidCommandException;
import optix.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListDateCommandTest {
    private Ui ui = new Ui();
    private File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix.txt");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);


    @Test
    void execute() throws OptixInvalidCommandException {
        // try looking for a show that does not exist
        ListDateCommand testCommand1 = new ListDateCommand("december 2020");
        testCommand1.execute(model, ui, storage);
        String expected1 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! There are no shows on december 2020.\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());

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
        filePath.deleteOnExit();
    }
}