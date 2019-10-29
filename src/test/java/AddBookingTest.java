import booking.Booking;
import org.junit.jupiter.api.Test;
import user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddBookingTest {
    @Test
    void testAddBooking() {
        String user = "Bob";
        String room = "room4";
        String description = " study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "22/12/2019 1200";
        User temp = new User("dummy");
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        assertEquals(newBooking.toString(), "Bob room4 22/12/2019 1100 to 22/12/2019 1200 P");
    }
}
