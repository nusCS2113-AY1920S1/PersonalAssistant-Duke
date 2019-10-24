package optix.commands;

import optix.commands.seats.SellSeatCommand;
import optix.commands.shows.AddCommand;
import optix.commands.shows.ViewMonthlyCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewMonthlyCommandTest {

    private Ui ui = new Ui();
    private File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix.txt");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @Test
    void execute() {
        AddCommand addDummyShow = new AddCommand("dummy show name|20|5/5/2020");
        addDummyShow.execute(model, ui, storage);

        SellSeatCommand sellDummySeat = new SellSeatCommand("dummy show name|5/5/2020|A1 A2 A3");
        sellDummySeat.execute(model, ui, storage);

        AddCommand addDummyShow2 = new AddCommand("dummy show name2|20|20/5/2020");
        addDummyShow2.execute(model, ui, storage);

        SellSeatCommand sellDummySeat2 = new SellSeatCommand("dummy show name2|20/5/2020|A1 A2 A3 A4 A5");
        sellDummySeat2.execute(model, ui, storage);

        AddCommand addDummyShow3 = new AddCommand("dummy show name3|20|1/1/2030");
        addDummyShow3.execute(model, ui, storage);

        SellSeatCommand sellDummySeat3 = new SellSeatCommand("dummy show name3|1/1/2030|A1 A2 A3 B2");
        sellDummySeat3.execute(model, ui, storage);

        ViewMonthlyCommand testCommand = new ViewMonthlyCommand("May 2020");
        testCommand.execute(model, ui, storage);

        String expected = "__________________________________________________________________________________\n"
                + "The projected earnings for May 2020 is 240.00.\n"
                + "__________________________________________________________________________________\n";

        assertEquals(expected, ui.showCommandLine());

        ViewMonthlyCommand testCommand2 = new ViewMonthlyCommand("January 2023");
        testCommand2.execute(model, ui, storage);

        String expected2 = "__________________________________________________________________________________\n"
                + "☹ OOPS!!! There are no shows in January 2023.\n"
                + "__________________________________________________________________________________\n";

        assertEquals(expected2, ui.showCommandLine());

        filePath.deleteOnExit();
    }
}
