package booking;

import exception.DukeException;
import storage.BookingConstants;

import java.util.ArrayList;

public class ApprovedList extends BookingList {

    /**
     * list for approved requests.
     * @param loader load the line from file
     * @throws DukeException entry error
     */
    public ApprovedList(ArrayList<String> loader) throws DukeException {
        for (String line : loader) {
            String[] splitStr = line.split(" \\| ", 7);
            this.add(new Booking(splitStr[BookingConstants.USERNAME],
                    splitStr[BookingConstants.VENUE], splitStr[BookingConstants.DESCRIPTION],
                    splitStr[BookingConstants.TIMESTART], splitStr[BookingConstants.TIMEEND],
                    splitStr[BookingConstants.STATUS], splitStr[6]));
        }
    }


    public ApprovedList() {
        super();
    }


}
