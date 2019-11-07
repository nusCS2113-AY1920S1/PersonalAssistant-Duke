import booking.Booking;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddBookingTest1 {
    @Test
    void testAddBooking() {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        assertEquals("Bob", newBooking.getName());
        assertEquals("room4", newBooking.getVenue());
        assertEquals("study", newBooking.getDescription());
        assertEquals("2019-12-22", newBooking.getDateStart().toString());
        assertEquals("11:00", newBooking.getTimeStart().toString());
        assertEquals("12:00", newBooking.getTimeEnd().toString());
    }
}
