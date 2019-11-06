import booking.Booking;
import control.Duke;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import user.UserList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddBookingTest1 {
    @Test
    void testAddBooking() throws DukeException {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        //String status = "P";
        String currentUser = "Jack";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd, currentUser);
        assertEquals("Bob room4 22/12/2019 1100 to 1200 P ", newBooking.toString());
    }
}
