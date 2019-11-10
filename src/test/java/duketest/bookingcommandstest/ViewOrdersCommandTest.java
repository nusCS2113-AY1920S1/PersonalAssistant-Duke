package duketest.bookingcommandstest;

import duke.logic.command.bookingcommands.ViewOrdersCommand;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static duke.common.Messages.filePathBookingTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewOrdersCommandTest {
    private Ui ui;
    private BookingStorage bookingStorage;
    private BookingList bookingList;
    private MainWindow mainWindow;

    @Test
    public void testViewBookingSchedule() {

        ui = new Ui(mainWindow);
        bookingStorage = new BookingStorage(filePathBookingTest);
        bookingList = new BookingList(bookingStorage.load());

        ArrayList<String> arrExpectedOutput = new ArrayList<>();
        arrExpectedOutput.add("     Here are your orders for: john_lim");
        arrExpectedOutput.add("     [chicken rice,  fish and chips] on 30 June 2019");

        ViewOrdersCommand viewOrdersCommand = new ViewOrdersCommand("vieworders john_lim");
        ArrayList<String> arrActualOutput = new ArrayList<>(viewOrdersCommand.execute(bookingList, ui, bookingStorage));

        assertEquals(arrExpectedOutput, arrActualOutput);
        System.out.println("Test passed.");
    }
}
