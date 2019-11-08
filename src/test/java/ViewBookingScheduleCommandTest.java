import duke.logic.command.bookingcommands.ViewBookingScheduleCommand;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.filePathBookingTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewBookingScheduleCommandTest {
    private Ui ui;
    private BookingStorage bookingStorage;
    private BookingList bookingList;
    private MainWindow mainWindow;

    @Test
    public void testViewBookingSchedule() throws ParseException {

        ui = new Ui(mainWindow);
        bookingStorage = new BookingStorage(filePathBookingTest);
        bookingList = new BookingList(bookingStorage.load());

        ArrayList<String> arrExpectedOutput = new ArrayList<>();
        String expectedOutput = "      No booking on 01 January 2000. \n" +
                "      You may proceed with command: addbooking";
        arrExpectedOutput.add(expectedOutput);

        ViewBookingScheduleCommand viewBookingScheduleCommand = new ViewBookingScheduleCommand("viewbookingschedule 1/1/2000");
        ArrayList<String> arrActualOutput = new ArrayList<>(viewBookingScheduleCommand.execute(bookingList, ui, bookingStorage));

        assertEquals(arrExpectedOutput, arrActualOutput);
        System.out.println("Test passed.");
    }
}
