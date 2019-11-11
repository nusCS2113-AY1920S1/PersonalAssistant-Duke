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

class SellSeatCommandTest {
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
        new SellSeatCommand("").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Invalid Date")
    void testInvalidDate() {
        new AddCommand("Test Show|5|5/5/2030").execute(model, ui, storage);
        new SellSeatCommand("Test Show|2020|a1 a2").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid date.\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Show Name Do Not Match")
    void testInvalidShowName() {
        new AddCommand("Test Show|5|5/5/2030").execute(model, ui, storage);
        new SellSeatCommand("Test|5/5/2030|a1 a2").execute(model, ui, storage);
        String expected = "☹ OOPS!!! The show cannot be found.\n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Sell available seats")
    void testSellAvailableSeat() {
        new AddCommand("Test Show|20|10/10/2030").execute(model, ui, storage);
        new SellSeatCommand("Test Show|10/10/2030|A1 B3").execute(model, ui, storage);
        String expected = "You have successfully purchased the following seats: \n"
                + "[A1, B3]\n"
                + "The total cost of the tickets are $60.00\n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Valid Execute")
    void testSellSeat() {
        new AddCommand("Test Show|20|10/10/2030").execute(model, ui, storage);
        new SellSeatCommand("Test Show|10/10/2030|A1").execute(model, ui, storage);
        new SellSeatCommand("Test Show|10/10/2030|A1 A2 A3 A11").execute(model, ui, storage);
        String expected = "You have successfully purchased the following seats: \n"
                + "[A2, A3]\n"
                + "The total cost of the tickets are $60.00\n"
                + "The following seats are unavailable: \n"
                + "[A1]\n"
                + "The following seats do not exist: \n"
                + "[A11]\n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Unavailable Seats")
    void testSellUnavailableSeats() {
        new AddCommand("Test Show|20|10/10/2030").execute(model, ui, storage);
        new SellSeatCommand("Test Show|10/10/2030|A1").execute(model, ui, storage);
        new SellSeatCommand("Test Show|10/10/2030|A1").execute(model, ui, storage);
        String expected = "☹ OOPS!!! All of the seats [A1] are unavailable.\n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Non-existent seats")
    void testSellSeatsNotExist() {
        new AddCommand("Test Show|20|10/10/2030").execute(model, ui, storage);
        new SellSeatCommand("Test Show|10/10/2030|A11").execute(model, ui, storage);
        String expected = "☹ OOPS!!! All of the seats [A11] do not exist.\n";
        assertEquals(expected, ui.getMessage());
    }


    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}