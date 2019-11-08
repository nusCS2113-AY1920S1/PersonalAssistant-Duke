import booking.Booking;
import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
//@@Alex-Teo
public class AddBookingTest2 {
    @Test
    void testAddBooking() throws DukeException {
        String user = "Barry";
        String room = "room4";
        String description = " eat chicken";
        String dateTimeStart = "21/12/2021 1100";
        String timeEnd = "1500";
        String status = "P";
        //String currentUser = "Bob";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        assertEquals(newBooking.toString(), "Barry room4 21/12/2021 1100 to 1500 P");
    }
}
