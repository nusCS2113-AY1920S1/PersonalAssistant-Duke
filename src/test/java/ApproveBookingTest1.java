import booking.Booking;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApproveBookingTest1 {

    @Test
    void TestApproveBooking() {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        String status = "P";
        String currentUser = "Bob";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd, status, currentUser);
        //assertEquals("Bob room4 22/12/2019 1100 to 1200 P", newBooking.toString());
        newBooking.approveStatus("John");
        assertEquals("This request has been approved" + "\n" + "Bob room4 22/12/2019 1100 to 1200 A", newBooking.toString());


    }
}
