package booking;

import exception.DukeException;
import storage.BookingConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BookingList extends ArrayList<Booking> {

    /**
     * Create tasklist from text file.
     * @param loader strings from text file containing task info
     * @throws DukeException if file format incorrect
     */
    public BookingList(ArrayList<String> loader) throws DukeException {
        for (String line : loader) {
            String[] splitStr = line.split(" \\| ", 5);
            this.add(new Booking(splitStr[BookingConstants.USERNAME],
                    splitStr[BookingConstants.VENUE], splitStr[BookingConstants.DESCRIPTION],
                    splitStr[BookingConstants.TIMESTART], splitStr[BookingConstants.TIMEEND]));
        }
    }


    public BookingList(){
        super();
    }

    /**
     * To check if a request with the same place and time slot has already been made
     * @param bookinglist the list of requests
     * @param roomcode the room in question
     * @param timeStart the starting time
     * @param timeEnd the ending time
     * @return if there is already a request with that venue and time slot
     */
    public static boolean checkBooking(BookingList bookinglist, String roomcode, String timeStart, String timeEnd) {
        boolean found = false;
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        LocalDateTime startTime = LocalDateTime.parse(timeStart, formatterStart);
        LocalDateTime endTime = LocalDateTime.parse(timeEnd, formatterEnd);
        for (int i = 0; i < bookinglist.size(); i++) {
            if (bookinglist.get(i).venue == roomcode) {
                if ((bookinglist.get(i).dateTimeStart.isBefore(startTime) ||
                        bookinglist.get(i).dateTimeStart.isEqual(startTime)) &&
                        (bookinglist.get(i).dateTimeEnd.isAfter(endTime) ||
                                bookinglist.get(i).dateTimeEnd.isEqual(endTime))) {
                    found = true;
                }
            }
        }
        return found;
    }
}