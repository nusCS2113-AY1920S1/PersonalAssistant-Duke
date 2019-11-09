package duketest.bookingcommandstest;

import duke.logic.command.bookingcommands.AddBookingCommand;
import duke.logic.command.bookingcommands.DeleteBookingCommand;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.filePathBookingTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddBookingCommandTest {

    private Ui ui;
    private BookingStorage bookingStorage;
    private BookingList bookingList;
    private MainWindow mainWindow;

    @Test
    public void testAddBookingCommand() throws ParseException {

        ui = new Ui(mainWindow);
        bookingStorage = new BookingStorage(filePathBookingTest);
        bookingList = new BookingList(bookingStorage.load());

        ArrayList<String> arrExpectedOutput = new ArrayList<>();
        String testCase = "addbooking Kelvin 81234567 8 1/1/2000 orders/ beef burger";
        String expectedOutput = "New booking added:\n" +
                "       [Customer name: Kelvin] [Contact No.: 81234567] [No. of pax: 8] [Booking on: 1 January 2000] [Orders: beef burger]\n" +
                "Now you have 4 bookings in the list.";
        arrExpectedOutput.add(expectedOutput);

        AddBookingCommand addBookingCommand = new AddBookingCommand(testCase);
        ArrayList<String> arrActualOutput = new ArrayList<>(addBookingCommand.execute(bookingList, ui, bookingStorage));


        assertEquals(arrExpectedOutput, arrActualOutput);
        System.out.println("Test passed.");

        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand("deletebooking 4");
        deleteBookingCommand.execute(bookingList, ui, bookingStorage);

    }
}
