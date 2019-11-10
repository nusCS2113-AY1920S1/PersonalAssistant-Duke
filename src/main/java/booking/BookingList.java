package booking;

import exception.DukeException;
import storage.BookingConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BookingList extends ArrayList<Booking> {

    /**
     * Create booking list from text file.
     * @param loader strings from text file containing booking info
     * @throws DukeException if file format incorrect
     */
    public BookingList(ArrayList<String> loader) throws DukeException {
        for (String line : loader) {
            String[] splitStr = line.split(" \\| ");
            this.add(new Booking(splitStr[BookingConstants.USERNAME],
                    splitStr[BookingConstants.VENUE], splitStr[BookingConstants.DESCRIPTION],
                    splitStr[BookingConstants.TIMESTART], splitStr[BookingConstants.TIMEEND],
                    splitStr[BookingConstants.STATUS], splitStr[BookingConstants.APPROVEDBY]));
        }
    }


    public BookingList() {
        super();
    }

    /**
     * To check if a request with the same place and time slot has already been made.
     * @param bookinglist the list of requests
     * @param roomcode the room in question
     * @param timeStart the starting time
     * @param timeEnd the ending time
     * @return if there is already a request with that venue and time slot
     */
    public static boolean checkBooking(BookingList bookinglist, String roomcode, String timeStart, String timeEnd) {
        boolean found = false;
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HHmm");
        LocalDateTime startDateTime = LocalDateTime.parse(timeStart, formatterStart);
        LocalDate dateStart = startDateTime.toLocalDate();
        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = LocalTime.parse(timeEnd, formatterEnd);
        boolean startAfter;
        boolean startBefore;
        boolean endAfter;
        boolean endBefore;
        boolean startBetween;
        boolean endBetween;
        for (Booking booking : bookinglist) {
            startAfter = booking.getTimeStart().isAfter(startTime);
            startBefore = booking.getTimeStart().isBefore(startTime);
            endAfter = booking.getTimeEnd().isAfter(endTime);
            endBefore = booking.getTimeEnd().isBefore(endTime);
            startBetween = (booking.getTimeStart().isAfter(startTime)
                    && booking.getTimeStart().isBefore(endTime))
                    || (startTime.isAfter(booking.getTimeStart())
                    && startTime.isBefore(booking.getTimeEnd()));
            endBetween = (booking.getTimeEnd().isAfter(startTime)
                    && booking.getTimeEnd().isBefore(endTime))
                    || (endTime.isAfter(booking.getTimeStart())
                    && endTime.isBefore(booking.getTimeEnd()));
            if ((booking.venue.equals(roomcode)) && (booking.getDateStart().equals(dateStart))) {
                if ((!startAfter && !startBefore) && (!endAfter && !endBefore)) {
                    found = true;
                } else if (startAfter && endBefore) {
                    found = true;
                } else if (startBefore && endAfter) {
                    found = true;
                } else if (startBetween || endBetween) {
                    found = true;
                }
            }
        }
        return found;
    }
}