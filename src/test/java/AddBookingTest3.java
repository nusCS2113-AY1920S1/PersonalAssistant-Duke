import booking.Booking;
import command.AddBookingCommand;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.BookingConstants;
import storage.Constants;

import java.io.IOException;
import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author Alex-Teo
public class AddBookingTest3 {
    @Test //Test Start time error
    void testAddBookingError1() throws DukeException, IOException {
        String user = "Null";
        String room = "room#";
        String description = " eat chicken";
        String dateTimeStart = "21/12/2021 11:00";
        String timeEnd = "1500";
        assertThrows(DukeException.class, () -> {
            new Booking(user, room, description, dateTimeStart, timeEnd);
        });
        assertEquals(":-( Not able to parse the date for all patterns given, "
                + "please use this format: add NAME DESCRIPTION "
                + "/at ROOM_CODE /from DATE TIMESTART /to TIMEEND"
                + ", DATE TIMESTART format is dd/mm/yyyy HHMM, TIMEEND is HHMM"
                , BookingConstants.DATETIMEERROR);
    }

    @Test //Test end time error
    void testAddBookingError2() throws DukeException, IOException {
        String user = "Null";
        String room = "room#";
        String description = " eat chicken";
        String dateTimeStart = "21/12/2021 1100";
        String timeEnd = "15:00";
        assertThrows(DukeException.class, () -> {
            new Booking(user, room, description, dateTimeStart, timeEnd);
        });
        assertEquals(":-( Not able to parse the date for all patterns given, "
                        + "please use this format: add NAME DESCRIPTION "
                        + "/at ROOM_CODE /from DATE TIMESTART /to TIMEEND"
                        + ", DATE TIMESTART format is dd/mm/yyyy HHMM, TIMEEND is HHMM"
                , BookingConstants.DATETIMEERROR);
    }

    @Test //no /at
    void testAddBookingError3() throws DukeException, IOException {
        String input = "add Null eat chicken room# /from 21/12/2021 1100 /to 1500";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new AddBookingCommand(input, splitStr);
        });
        assertEquals(":-( Not able to parse the date for all patterns given, "
                        + "please use this format: add NAME DESCRIPTION "
                        + "/at ROOM_CODE /from DATE TIMESTART /to TIMEEND"
                        + ", DATE TIMESTART format is dd/mm/yyyy HHMM, TIMEEND is HHMM"
                , BookingConstants.DATETIMEERROR);
    }

    @Test //no /from
    void testAddBookingError4() throws DukeException, IOException {
        String input = "add Null eat chicken /at room# 21/12/2021 1100 /to 1500";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new AddBookingCommand(input, splitStr);
        });
        assertEquals(":-( Not able to parse the date for all patterns given, "
                        + "please use this format: add NAME DESCRIPTION "
                        + "/at ROOM_CODE /from DATE TIMESTART /to TIMEEND"
                        + ", DATE TIMESTART format is dd/mm/yyyy HHMM, TIMEEND is HHMM"
                , BookingConstants.DATETIMEERROR);
    }

    @Test //no /to
    void testAddBookingError5() throws DukeException, IOException {
        String input = "add Null eat chicken /at room# /from 21/12/2021 1100 1500";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new AddBookingCommand(input, splitStr);
        });
        assertEquals(":-( Not able to parse the date for all patterns given, "
                        + "please use this format: add NAME DESCRIPTION "
                        + "/at ROOM_CODE /from DATE TIMESTART /to TIMEEND"
                        + ", DATE TIMESTART format is dd/mm/yyyy HHMM, TIMEEND is HHMM"
                , BookingConstants.DATETIMEERROR);
    }

    @Test //no date and time at all
    void testAddBookingError6() throws DukeException, IOException {
        String input = "add Null eat chicken /at";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new AddBookingCommand(input, splitStr);
        });
        assertEquals(":-( Not able to parse the date for all patterns given, "
                        + "please use this format: add NAME DESCRIPTION "
                        + "/at ROOM_CODE /from DATE TIMESTART /to TIMEEND"
                        + ", DATE TIMESTART format is dd/mm/yyyy HHMM, TIMEEND is HHMM"
                , BookingConstants.DATETIMEERROR);
    }
}