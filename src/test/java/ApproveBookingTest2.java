import booking.Booking;
import control.Duke;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.Storage;
import ui.Ui;
import user.UserList;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
//@@Alex-Teo
public class ApproveBookingTest2 {
    private Storage userStorage;
    private static UserList adminList;
    private Ui ui;

    @Test
    void TestApproveBooking(/*String userFile*/) throws DukeException {
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
}
