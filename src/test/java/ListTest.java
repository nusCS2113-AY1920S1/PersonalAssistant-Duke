import booking.Booking;
import command.ListBookingDailyCommand;
import command.ListBookingMonthCommand;
import control.Duke;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import user.UserList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Alex-Teo
// for day

public class ListTest {
    @Test
    public void TestListDay () throws DukeException {
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
        assertEquals( "1. Bob room4 22/12/2019 1100 to 1200 P", "1. " + newBooking.toString());
    }

    @Test
    public void TestListMonth () throws DukeException {
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
        assertEquals( "2. brian room4 23/11/2019 1100 to 1200 P", "2. " + newBooking1.toString());
    }

    @Test
    public void TestListYear () throws DukeException {
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
        assertEquals( "1. Bob room4 22/12/2019 1100 to 1200 P" + "\n"
                + "2. brian room4 23/11/2019 1100 to 1200 P",
                "1. " + newBooking.toString() + "\n" + "2. " + newBooking1.toString());
    }
}
