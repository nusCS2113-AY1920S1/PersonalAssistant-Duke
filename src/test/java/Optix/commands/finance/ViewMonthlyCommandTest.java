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
class ViewMonthlyCommandTest {
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
        new ViewMonthlyCommand("").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
        new ViewMonthlyCommand("October").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
        new ViewMonthlyCommand("October 2020 2020").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Incorrect Date Format")
    void testDateFormatter() {
        String expected = "☹ OOPS!!! That is an invalid date.\n"
                + "Please try again. \n";
        new ViewMonthlyCommand("Octoberr 2020").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
        new ViewMonthlyCommand("0 2020").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
        new ViewMonthlyCommand("13 Oct").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("No show found")
    void testMonth() {
        String expected = "☹ OOPS!!! There are no shows in February 2019.\n";
        new ViewMonthlyCommand("2 2019").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Valid Test for Current Month Profits")
    void testCurrentMonthProfit() {
        String expected = "The current earnings for October 2019 is $200.00.\n";
        new ViewMonthlyCommand("10 2019").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Valid Test for Archive Profits")
    void testArchiveProfit() {
        String expected = "The earnings for November 2018 is $6000.00.\n";
        new ViewMonthlyCommand("Nov 2018").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
        expected = "The earnings for September 2019 is $200.00.\n";
        new ViewMonthlyCommand("September 2019").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
    }

    @Test
    @DisplayName("Valid Test for Projected Profits")
    void testShowProfit() {
        new AddCommand("Lion King|20|9/12/2019|10/10/2020|11/10/2020").execute(model, ui, storage);
        new SellSeatCommand("Lion King|10/10/2020|A1 A2 A3 A4 B5 C6 D7 E8 F9 A10").execute(model, ui, storage);
        new SellSeatCommand("Lion King|9/12/2019|A1 A2 A3 A4 B5 C6 D7 E8 F9 A10").execute(model, ui, storage);
        String expected = "The projected earnings for October 2020 is $268.00.\n";
        new ViewMonthlyCommand("Oct 2020").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
        expected = "The projected earnings for December 2019 is $268.00.\n";
        new ViewMonthlyCommand("12 2019").execute(model, ui, storage);
        assertEquals(expected, ui.getMessage());
    }

    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}
