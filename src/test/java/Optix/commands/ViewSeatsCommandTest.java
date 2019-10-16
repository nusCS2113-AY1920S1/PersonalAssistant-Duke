package optix.commands;

import optix.commands.seats.ViewSeatsCommand;
import optix.commands.shows.AddCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewSeatsCommandTest {

    private Ui ui = new Ui();
    private File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix.txt");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @Test
    void execute() {
        // add a dummy show
        AddCommand addDummyShow = new AddCommand("Dummy Show", "5/5/2020", 20);
        addDummyShow.execute(model, ui, storage);
        ViewSeatsCommand testCommand = new ViewSeatsCommand("Dummy Show", "5/5/2020");
        testCommand.execute(model, ui, storage);
        String expected1 =
                "__________________________________________________________________________________\n"
                        + "Here is the layout of the theatre for Dummy Show on 5/5/2020:\n"
                        + "                |STAGE|           \n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"

                        + "\nTier 1 Seats: 20\n"
                        + "Tier 2 Seats: 20\n"
                        + "Tier 3 Seats: 20\n"
                        + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());

        // view a show that does not exist
        ViewSeatsCommand viewNonExistentShow = new ViewSeatsCommand("non existent show", "5/5/2020");
        viewNonExistentShow.execute(model, ui, storage);
        String expected2 =
                "__________________________________________________________________________________\n"
                        + "☹ OOPS!!! Sorry the show non existent show cannot be found.\n"
                        + "__________________________________________________________________________________\n";
        assertEquals(expected2, ui.showCommandLine());
    }
}
