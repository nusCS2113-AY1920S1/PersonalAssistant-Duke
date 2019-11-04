import duke.logic.command.bookingcommands.FindBookingCommand;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static duke.common.BookingMessages.MESSAGE_MATCHING_BOOKINGS;
import static duke.common.Messages.filePathBookingTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindBookingCommandTest {

    private Ui ui;
    private BookingStorage bookingStorage;
    private BookingList bookingList;
    private MainWindow mainWindow;

    @Test
    public void testFindBookingCommand() {
        ui = new Ui(mainWindow);
        bookingStorage = new BookingStorage(filePathBookingTest);
        bookingList = new BookingList(bookingStorage.load());

        ArrayList<String> arrExpectedOutput = new ArrayList<>();
        arrExpectedOutput.add(MESSAGE_MATCHING_BOOKINGS);
        arrExpectedOutput.add("     3. [Customer name: Jack] [Contact No.: 94847291] [No. of pax: 2] [Booking on: 13 May 2019] [Orders: beef pasta]");

        FindBookingCommand findBookingCommand = new FindBookingCommand("findbooking jack");
        ArrayList<String> arrActualOutput = new ArrayList<>(findBookingCommand.execute(bookingList, ui, bookingStorage));

        assertEquals(arrExpectedOutput, arrActualOutput);
        System.out.println("Test passed.");

    }
}
