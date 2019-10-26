package optix.commands.shows;

import optix.commands.shows.AddCommand;
import optix.commands.shows.EditCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditCommandTest {
    private Ui ui = new Ui();
    private static File currentDir = new File(System.getProperty("user.dir"));
    private static File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @Test
    void execute() {
        // add test shows
        AddCommand addTestShow1 = new AddCommand("Test Show 1|20|5/5/2020");
        addTestShow1.execute(model, ui, storage);
        AddCommand addTestShow2 = new AddCommand("Test Show 2|20|7/5/2020");
        addTestShow2.execute(model, ui, storage);
        EditCommand testCommand = new EditCommand("Test Show 1|5/5/2020|Test Show 3");
        testCommand.execute(model, ui, storage);
        String expected1 = "__________________________________________________________________________________\n"
                + "Show has been successfully updated to Test Show 3.\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());
        // postpone show 1 to an invalid date (there is a show on the desired date.)
        EditCommand testCommand2 = new EditCommand("Test Show 1|5/5/2020|Test Show 3");
        testCommand2.execute(model, ui, storage);
        String expected2 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! The show you are finding does not exist!\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected2, ui.showCommandLine());
        filePath.deleteOnExit();
    }

    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}