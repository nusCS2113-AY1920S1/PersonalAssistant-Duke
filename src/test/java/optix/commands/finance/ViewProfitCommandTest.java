package optix.commands.finance;

import optix.commands.seats.SellSeatCommand;
import optix.commands.shows.AddCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author NicholasLiu97
class ViewProfitCommandTest {
    private Ui ui = new Ui();
    private static File currentDir = new File(System.getProperty("user.dir"));
    private static File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix");
    private Storage storage = new Storage(filePath);
    private Model model = new Model(storage);

    @BeforeEach
    void init() {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.model = new Model(storage);
    }

    @Test
    @DisplayName("Incorrect number of parameters")
    void testParseDetails() {
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        new ViewProfitCommand("").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
        new ViewProfitCommand("Lion King").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
        new ViewProfitCommand("Lion King|5/5/2020|20202").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Invalid Date")
    void testInvalidDate() {
        new ViewProfitCommand("Test Show|2020").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid date.\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Invalid Show Name")
    void testInvalidShow() {
        new ViewProfitCommand("Test Show|5/5/2020").execute(model, ui, storage); //show don't exist
        String expected = "☹ OOPS!!! The show cannot be found.\n";
        assertEquals(expected, ui.getMessage());
        new ViewProfitCommand("Test Show|14/10/2015").execute(model, ui, storage); //archive show don't exist
        assertEquals(expected, ui.getMessage());
        new AddCommand("Test Show|20|5/5/2020").execute(model, ui, storage);
        new ViewProfitCommand("Test |5/5/2020").execute(model, ui, storage); //show name does not match
        assertEquals(expected, ui.getMessage());
        new ViewProfitCommand("Harry Potte|13/10/2015").execute(model, ui, storage); //show name does not match
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Archive Show exist")
    void testArchiveShow() {
        String expected = "The profit for Harry Potter on 13/10/2015 is $2000.00\n";
        new ViewProfitCommand("Harry Potter|13/10/2015").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Scheduled Show exist")
    void testViewProfit() {
        new AddCommand("Test Show|20|5/5/2020").execute(model, ui, storage);
        new SellSeatCommand("Test Show|5/5/2020|A1 A2").execute(model, ui, storage);
        new ViewProfitCommand("Test Show|5/5/2020").execute(model, ui, storage);
        String expected = "The profit for Test Show on 5/5/2020 is $60.00\n";
        assertEquals(expected, ui.getMessage());
    }

    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}
