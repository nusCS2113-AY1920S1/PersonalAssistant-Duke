import duke.logic.command.bookingcommands.AllBookingsCommand;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static duke.common.BookingMessages.MESSAGE_ALL_CURRENT_BOOKINGS;
import static duke.common.Messages.filePathBookingTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllBookingsCommandTest {

    private Ui ui;
    private BookingStorage bookingStorage;
    private BookingList bookingList;
    private MainWindow mainWindow;

    @Test
    public void testAllBookingsCommand() {
        ui = new Ui(mainWindow);
        bookingStorage = new BookingStorage(filePathBookingTest);
        bookingList = new BookingList(bookingStorage.load());

        ArrayList<String> arrExpectedOutput = new ArrayList<>();
        arrExpectedOutput.add(MESSAGE_ALL_CURRENT_BOOKINGS);
        arrExpectedOutput.add("     1. [Customer name: Zihan] [Contact No.: 91520567] [No. of pax: 4] [Booking on: 12 December 2019] [Orders: prawn pasta, chicken pie]");
        arrExpectedOutput.add("     2. [Customer name: John_Lim] [Contact No.: 82738364] [No. of pax: 6] [Booking on: 30 June 2019] [Orders: chicken rice, fish and chips]");
        arrExpectedOutput.add("     3. [Customer name: Jack] [Contact No.: 94847291] [No. of pax: 2] [Booking on: 13 May 2019] [Orders: beef pasta]");

        AllBookingsCommand allBookingsCommand = new AllBookingsCommand();
        ArrayList<String> arrActualOutput = new ArrayList<>(allBookingsCommand.execute(bookingList, ui, bookingStorage));

        assertEquals(arrExpectedOutput, arrActualOutput);
        System.out.println("Test passed.");
    }
}
