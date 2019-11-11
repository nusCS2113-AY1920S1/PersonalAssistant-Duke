package optix.commands.seats;

import optix.commands.shows.AddCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.commons.model.Theatre;
import optix.ui.Ui;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewSeatsCommandTest {
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
        new AddCommand("Test Show|20|5/5/2020").execute(model, ui, storage);
        new ViewSeatsCommand("").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Invalid Date")
    void testInvalidDate() {
        new AddCommand("Test Show|5|5/5/2030").execute(model, ui, storage);
        new ViewSeatsCommand("Test Show|2020").execute(model, ui, storage);
        String expected = "☹ OOPS!!! That is an invalid date.\n"
                + "Please try again. \n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Show Name Do Not Match")
    void testInvalidShowName() {
        new AddCommand("Test Show|5|5/5/2030").execute(model, ui, storage);
        new ViewSeatsCommand("Test|5/5/2030").execute(model, ui, storage);
        String expected = "☹ OOPS!!! Sorry the show Test cannot be found.\n";
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Valid Execute")
    void testValidViewSeat() {
        new AddCommand("Test Show|5|5/5/2030").execute(model, ui, storage);
        new ViewSeatsCommand("Test Show|5/5/2030").execute(model, ui, storage);
        Theatre show = model.getShows().get(LocalDate.of(2030, 5, 5));
        String expected = "Here is the layout of the theatre for Test Show on 5/5/2030:\n"
                        + show.getSeatingArrangement();
        assertEquals(expected, ui.getMessage());
    }

    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}
