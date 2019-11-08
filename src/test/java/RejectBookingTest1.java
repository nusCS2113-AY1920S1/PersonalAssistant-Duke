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
public class RejectBookingTest1 {

    @Test
    void TestRejectBooking(/*String userFile*/) throws DukeException {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        assertEquals("Bob room4 22/12/2019 1100 to 1200 P", newBooking.toString());
        newBooking.rejectStatus("amir");
        assertEquals( "Bob room4 22/12/2019 1100 to 1200 R", newBooking.toString());


    }
}
