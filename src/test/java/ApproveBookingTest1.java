import booking.Booking;
import control.Duke;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.Storage;
import ui.Ui;
import user.UserList;

import java.io.FileNotFoundException;
//@@author Alex-Teo
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApproveBookingTest1 {
    private Storage userStorage;
    private static UserList adminList;
    private Ui ui;

    @Test
    void testApproveBooking() throws DukeException {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        assertEquals("Bob room4 22/12/2019 1100 to 1200 P", newBooking.toString());
        newBooking.approveStatus("amir");
        assertEquals("Bob room4 22/12/2019 1100 to 1200 A", newBooking.toString());
    }
}
