package Optix.commands;

import optix.Ui;
import optix.commands.shows.AddCommand;
import optix.commands.shows.EditCommand;
import optix.core.Storage;
import optix.util.ShowMap;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditCommandTest {
    private ShowMap shows = new ShowMap();
    private Ui ui = new Ui();
    private File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\main\\data\\testOptix.txt");
    private Storage storage = new Storage(filePath);

    @Test
    void execute() {
        // add test shows
        AddCommand addTestShow1 = new AddCommand("Test Show 1","5/5/2020", 2000, 20);
        addTestShow1.execute(shows, ui, storage);
        AddCommand addTestShow2 = new AddCommand("Test Show 2","7/5/2020", 2000, 20);
        addTestShow2.execute(shows, ui, storage);
        // postpone show 1 to a valid date (there is no show on desired date.)
        EditCommand testCommand = new EditCommand("Test Show 1","5/5/2020","Test Show 3");
        testCommand.execute(shows,ui,storage);
        String expected1 =  "__________________________________________________________________________________\n"
                + "Show has been successfully updated to Test Show 3.\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1,ui.showLine());
        // postpone show 1 to an invalid date (there is a show on the desired date.)
        EditCommand testCommand2 = new EditCommand("Test Show 1","5/5/2020","Test Show 3");
        testCommand2.execute(shows,ui,storage);
        String expected2 =  "__________________________________________________________________________________\n"
                + "☹ OOPS!!! The show you are finding does not exist!\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected2,ui.showLine());
        filePath.deleteOnExit();
    }
}