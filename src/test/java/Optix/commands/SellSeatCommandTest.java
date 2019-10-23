package optix.commands;

import optix.commands.seats.SellSeatCommand;
import optix.commands.shows.AddCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidCommandException;
import optix.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SellSeatCommandTest {

    private Ui ui = new Ui();
    private File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix.txt");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @Test
    void execute() throws OptixInvalidCommandException {
        filePath.deleteOnExit();
        AddCommand addTestShow1 = new AddCommand("Test Show 1|20|5/5/2020");
        addTestShow1.execute(model, ui, storage);
        // sell an available seat
        SellSeatCommand testCommand1 = new SellSeatCommand("Test Show 1|5/5/2020|A1");
        testCommand1.execute(model, ui, storage);
        String expected1 = "__________________________________________________________________________________\n"
                + "You have successfully purchased the following seats: \n"
                + "[A1]\n"
                + "The total cost of the ticket is $30.00\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected1, ui.showCommandLine());

        // sell a seat that is taken
        // SellSeatCommand testCommand2 = new SellSeatCommand("Test Show 1", "5/5/2020","A1");
        testCommand1.execute(model, ui, storage);
        String expected2 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! All of the seats [A1] are unavailable\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected2, ui.showCommandLine());
        //sell a seat that does not exist
        SellSeatCommand testCommand2 = new SellSeatCommand("Test Show 1|5/5/2020|%1");
        testCommand2.execute(model, ui, storage);
        String expected3 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! All of the seats [%1] are unavailable\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected3, ui.showCommandLine());
    }
}