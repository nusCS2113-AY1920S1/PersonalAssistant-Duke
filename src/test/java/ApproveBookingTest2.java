import booking.Booking;
import command.AddBookingCommand;
import command.ApproveCommand;
import control.Duke;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.BookingConstants;
import storage.Storage;
import ui.Ui;
import user.UserList;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@Alex-Teo
public class ApproveBookingTest2 {

    @Test
    void TestApproveBooking1() throws DukeException {
        String user = "Bob";
        String room = "ST4";
        String description = "study";
        String dateTimeStart = "30/12/9999 1400";
        String timeEnd = "1900";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        assertEquals("Bob ST4 30/12/9999 1400 to 1900 P", newBooking.toString());
        newBooking.approveStatus("amir");
        assertEquals( "Bob ST4 30/12/9999 1400 to 1900 A", newBooking.toString());
    }

    @Test
    void testApproveBookingError1() throws DukeException, IOException {
        String input = "approve";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new ApproveCommand(input, splitStr);
        });
        assertEquals(":-( OOPS!!! "
                + "Please enter the index of the item you want to edit as well as the "
                + "updated description of your booking!", BookingConstants.INDEXERROR1);
    }

    @Test
    void testApproveBookingError2() throws DukeException, IOException {
        String input = "approve #";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new ApproveCommand(input, splitStr);
        });
        assertEquals(":-( OOPS!!! Please enter a index in integer form!", BookingConstants.INDEXERROR2);
    }
}
