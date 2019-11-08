import booking.Booking;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.BookingConstants;
import storage.Constants;

import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
//@@Alex-Teo
public class AddBookingTest3 {
    @Test
    void testAddBooking() throws DukeException {

            String user = "Null";
            String room = "room#";
            String description = " eat chicken";
            String dateTimeStart = "21-12-2021 1100";
            String timeEnd = "1500";
            Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);

        assertThrows(DateTimeException.class, () -> {
            new Booking(user, room, description, dateTimeStart, timeEnd);
        });
        assertEquals(BookingConstants.DATETIMEERROR, "Not able to parse the date for all patterns given, "
                + "please use this format: add NAME DESCRIPTION /at ROOM_CODE /from DATE TIMESTART /to TIMEEND"
                + ", DATE TIMESTART format is dd/mm/yyyy HHMM, TIMEEND is HHMM");
    }
}