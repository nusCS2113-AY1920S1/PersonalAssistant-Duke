package room;

import storage.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RoomList extends ArrayList<Room> {

    //@@author zkchang97
    /**
     * Creates room list from text file.
     * @param loader Strings from text file containing room list info
     */
    public RoomList(ArrayList<String> loader) {
        for (String line : loader) {
            String[] splitStr = line.split(" \\| ", 4);
            Long longDateTimeStart = Long.parseLong(splitStr[Constants.ROOMDATETIMESTART]);
            Long longDateTimeEnd = Long.parseLong(splitStr[Constants.ROOMDATETIMEEND]);
            this.add(new Room(splitStr[Constants.ROOMCODE], longDateTimeStart, longDateTimeEnd));
        }
    }

    /**
     * Public constructor in Duke.java
     */
    public RoomList() {
        super();
    }

    /**
     * Checks if a room to be added is already present in the room list file.
     * @param roomcode Room code of the target room
     * @return if the room already exists in the file
     * @throws IOException if the input is in the wrong format
     */

    public boolean checkRoom(String roomcode) {
        for (Room i : this) {
            if (i.roomcode.equals(roomcode)) {
                return true;
            }
        }
        return false;
    }

    /*
    public static boolean checkRoom(RoomList roomlist, String roomcode, String timeStart, String timeEnd) {
        boolean found = false;
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HHmm");
        LocalDateTime startTime = LocalDateTime.parse(timeStart, formatterStart);
        LocalTime endTime = LocalTime.parse(timeEnd, formatterEnd);
        for (int i = 0; i < roomlist.size(); i++) {
            if (roomlist.get(i).roomcode == roomcode) {
                if ((roomlist.get(i).dateTimeStart.isBefore(startTime)
                        || roomlist.get(i).dateTimeStart.isEqual(startTime))
                        && ((roomlist.get(i).timeEnd.isAfter(endTime))
                        && (roomlist.get(i).timeEnd.isBefore(endTime)))) {
                    found = true;
                }
            }
        }
        return found;
    }
     */
}
