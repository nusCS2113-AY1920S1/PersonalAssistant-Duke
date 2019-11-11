import booking.Booking;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.BookingConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author zkchang97
public class AddBookingTest4 {

    // Test for correct message thrown when incorrect date format is input
    @Test
    void addBookingTest5() {
        String name = "John";
        String description = "eat";
        String roomcode = "SR4";
        String startDateTime = "19-08-2019 0900";
        String endTime = "2100";
        assertThrows(DukeException.class, () -> {
            new Booking(name, roomcode, description, startDateTime, endTime);
        });
        assertEquals(":-( Not able to parse the date for all patterns given, "
                + "please use this format: add NAME DESCRIPTION /at ROOM_CODE /from DATE TIMESTART /to TIMEEND"
                + ", DATE TIMESTART format is dd/mm/yyyy HHMM, TIMEEND is HHMM", BookingConstants.DATETIMEERROR );
    }
}
