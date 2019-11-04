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

public class DeleteBookingCommandTest {

    private Ui ui;
    private BookingStorage bookingStorage;
    private BookingList bookingList;
    private MainWindow mainWindow;

    @Test
    public void testDeleteBookingCommand() throws ParseException {
        ui = new Ui(mainWindow);
        bookingStorage = new BookingStorage(filePathBookingTest);
        bookingList = new BookingList(bookingStorage.load());

        ArrayList<String> arrExpectedOutput = new ArrayList<>();
        String testCase = "deletebooking 4";
        String expectedOutput = "     Noted. I've removed this booking:\n" +
                "       [Customer name: Kelvin] [Contact No.: 81234567] [No. of pax: 8] [Booking on: 1 January 2000] [Orders: beef burger]\n" +
                "Now you have 3 bookings in the list.";
        arrExpectedOutput.add(expectedOutput);

        AddBookingCommand addBookingCommand = new AddBookingCommand("addbooking Kelvin 81234567 8 1/1/2000 orders/ beef burger");
        addBookingCommand.execute(bookingList, ui, bookingStorage);

        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(testCase);
        ArrayList<String> arrActualOutput = new ArrayList<>(deleteBookingCommand.execute(bookingList, ui, bookingStorage));

        assertEquals(arrExpectedOutput, arrActualOutput);
        System.out.println("Test passed.");

    }
}
