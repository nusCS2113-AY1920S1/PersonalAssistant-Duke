package optix.commands;

import optix.commands.shows.AddCommand;
import optix.commands.shows.PostponeCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostponeCommandTest {

    private Ui ui = new Ui();
    private File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix.txt");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @Test
    void execute() {
        // add test shows
        AddCommand addTestShow1 = new AddCommand("Test Show 1", "5/5/2020", 20);
        addTestShow1.execute(model, ui, storage);
        AddCommand addTestShow2 = new AddCommand("Test Show 2", "7/5/2020", 20);
        addTestShow2.execute(model, ui, storage);
        // postpone show 1 to a valid date (there is no show on desired date.)
        PostponeCommand testCommand = new PostponeCommand("Test Show 1", "5/5/2020", "6/5/2020");
        testCommand.execute(model, ui, storage);
        String expected1 = "__________________________________________________________________________________\n"
                + "Test Show 1 has been postponed from 5/5/2020 to 6/5/2020.\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());
        // postpone show 1 to an invalid date (there is a show on the desired date.)
        PostponeCommand testCommand2 = new PostponeCommand("Test Show 1", "6/5/2020", "7/5/2020");
        testCommand2.execute(model, ui, storage);
        String expected2 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! There already exists a show for 7/5/2020.\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected2, ui.showCommandLine());
        filePath.deleteOnExit();
    }
}