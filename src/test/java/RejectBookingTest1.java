import booking.Booking;
import command.RejectCommand;
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
public class RejectBookingTest1 {

    @Test
    void testRejectBooking() throws DukeException {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        assertEquals("Bob room4 22/12/2019 1100 to 1200 P", newBooking.toString());
        newBooking.rejectStatus("amir");
        assertEquals("Bob room4 22/12/2019 1100 to 1200 R", newBooking.toString());
    }

    @Test
    void testRejecteBookingError1() throws DukeException, IOException {
        String input = "reject";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new RejectCommand(input, splitStr);
        });
        assertEquals(":-( OOPS!!! "
                + "Please enter the index of the item you want to edit as well as the "
                + "updated description of your booking!", BookingConstants.INDEXERROR1);
    }

    @Test
    void testRejectBookingError2() throws DukeException, IOException {
        String input = "reject alpha";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new RejectCommand(input, splitStr);
        });
        assertEquals(":-( OOPS!!! Please enter a index in integer form!", BookingConstants.INDEXERROR2);
    }
}
