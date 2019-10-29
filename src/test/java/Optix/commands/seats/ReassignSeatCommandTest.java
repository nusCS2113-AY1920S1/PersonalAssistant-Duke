package optix.commands.seats;

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

class ReassignSeatCommandTest {
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
    @DisplayName("No Details Test")
    void testNoDetails() {
        new ReassignSeatCommand("").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Invalid Date")
    void testInvalidDate() {
        new AddCommand("Test Show|20|5/5/2030").execute(model, ui, storage);
        new ReassignSeatCommand("Test Show|2020|a1|a2").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid date.\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Show Name Do Not Match")
    void testInvalidShowName() {
        new AddCommand("Test Show|5|5/5/2030").execute(model, ui, storage);
        new ReassignSeatCommand("Test|5/5/2030|a1|a2").execute(model, ui, storage);
        String expected = "☹ OOPS!!! The show cannot be found.\n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Valid Execute")
    void testReassignSeat() {
        new AddCommand("Test Show|20|10/10/2030").execute(model, ui, storage);
        new SellSeatCommand("Test Show|10/10/2030|C1 C2 C3").execute(model, ui, storage);
        new ReassignSeatCommand("Test Show|10/10/2030|A1 | A5").execute(model, ui, storage);
        String expected = "The seat A1 is still available for booking.\n";
        assertEquals(expected, ui.getMessage());
        new ReassignSeatCommand("Test Show|10/10/2030|C1 | A5").execute(model, ui, storage);
        expected = "Your seat has been successfully changed from C1 to A5.\n"
                    + "An extra cost of $6.00 is required.\n";
        assertEquals(expected, ui.getMessage());
        new ReassignSeatCommand("Test Show|10/10/2030|C2 | E5").execute(model, ui, storage);
        expected = "Your seat has been successfully changed from C2 to E5.\n"
                    + "$4.00 will be returned.\n";
        assertEquals(expected, ui.getMessage());
    }


    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}