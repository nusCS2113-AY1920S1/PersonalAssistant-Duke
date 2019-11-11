import booking.Booking;
import command.ListBookingDailyCommand;
import command.ListBookingMonthCommand;
import control.Duke;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.BookingConstants;
import user.UserList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author Alex-Teo
// for day

public class ListTest {
    @Test
    public void testListDay() throws DukeException {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        String user1 = "brian";
        String room1 = "room4";
        String description1 = "study";
        String dateTimeStart1 = "23/12/2019 1100";
        String timeEnd1 = "1200";
        Booking newBooking1 = new Booking(user1, room1, description1, dateTimeStart1, timeEnd1);
        String input = "listday 22/12/2019";
        String[] splitStr = input.split(" ");
        new ListBookingDailyCommand(input, splitStr);
        assertEquals("1. Bob room4 22/12/2019 1100 to 1200 P", "1. " + newBooking.toString());
    }

    @Test
    public void testListDayError() throws DukeException {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        String user1 = "brian";
        String room1 = "room4";
        String description1 = "study";
        String dateTimeStart1 = "23/12/2019 1100";
        String timeEnd1 = "1200";
        Booking newBooking1 = new Booking(user1, room1, description1, dateTimeStart1, timeEnd1);
        String input = "listday 22-12-2019";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new ListBookingDailyCommand(input, splitStr);
        });
        assertEquals("Not able to parse the date for all patterns given, "
                + "please use this format: DATE" + ", DATE format is dd/mm/yyyy",
                BookingConstants.DATEERROR);
    }

    @Test
    public void testListMonth() throws DukeException {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        String user1 = "brian";
        String room1 = "room4";
        String description1 = "study";
        String dateTimeStart1 = "23/11/2019 1100";
        String timeEnd1 = "1200";
        Booking newBooking1 = new Booking(user1, room1, description1, dateTimeStart1, timeEnd1);
        String input = "listmonth 11";
        String[] splitStr = input.split(" ");
        new ListBookingMonthCommand(input, splitStr);
        assertEquals("2. brian room4 23/11/2019 1100 to 1200 P", "2. " + newBooking1.toString());
    }

    @Test
    public void testListMonthError() throws DukeException {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        String user1 = "brian";
        String room1 = "room4";
        String description1 = "study";
        String dateTimeStart1 = "23/12/2019 1100";
        String timeEnd1 = "1200";
        Booking newBooking1 = new Booking(user1, room1, description1, dateTimeStart1, timeEnd1);
        String input = "listmonth #";
        String[] splitStr = input.split(" ");
        assertThrows(NumberFormatException.class, () -> {
            new ListBookingMonthCommand(input, splitStr);
        });
        assertEquals(":-( OOPS!!! Please create your booking with the "
                        + "following format: month value",
                BookingConstants.MONTHERROR);
    }

    @Test
    public void testListYear() throws DukeException {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        String user1 = "brian";
        String room1 = "room4";
        String description1 = "study";
        String dateTimeStart1 = "23/11/2019 1100";
        String timeEnd1 = "1200";
        Booking newBooking1 = new Booking(user1, room1, description1, dateTimeStart1, timeEnd1);
        String input = "listmonth 11";
        String[] splitStr = input.split(" ");
        new ListBookingMonthCommand(input, splitStr);
        assertEquals("1. Bob room4 22/12/2019 1100 to 1200 P" + "\n"
                + "2. brian room4 23/11/2019 1100 to 1200 P",
                "1. " + newBooking.toString() + "\n" + "2. " + newBooking1.toString());
    }

    @Test
    public void testListYearError() throws DukeException {
        String user = "Bob";
        String room = "room4";
        String description = "study";
        String dateTimeStart = "22/12/2019 1100";
        String timeEnd = "1200";
        Booking newBooking = new Booking(user, room, description, dateTimeStart, timeEnd);
        String user1 = "brian";
        String room1 = "room4";
        String description1 = "study";
        String dateTimeStart1 = "23/12/2019 1100";
        String timeEnd1 = "1200";
        Booking newBooking1 = new Booking(user1, room1, description1, dateTimeStart1, timeEnd1);
        String input = "listyear 22-12-2019";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new ListBookingDailyCommand(input, splitStr);
        });
        assertEquals("Not able to parse the date for all patterns given, "
                        + "please use this format: DATE" + ", DATE format is dd/mm/yyyy",
                BookingConstants.DATEERROR);
    }
}
