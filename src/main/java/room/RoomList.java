package room;

import storage.Constants;

import java.io.IOException;
import java.time.LocalDate;
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

    public static boolean checkRoom(RoomList roomlist, String roomcode, String timeStart, String timeEnd) {
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
        for (int i = 0; i < roomlist.size(); i++) {
            startAfter = roomlist.get(i).getTimeStart().isAfter(startTime);
            startBefore = roomlist.get(i).getTimeStart().isBefore(startTime);
            endAfter = roomlist.get(i).getTimeEnd().isAfter(endTime);
            endBefore = roomlist.get(i).getTimeEnd().isBefore(endTime);
            startBetween = (roomlist.get(i).getTimeStart().isAfter(startTime)
                    && roomlist.get(i).getTimeStart().isBefore(endTime))
                    || (startTime.isAfter(roomlist.get(i).getTimeStart())
                    && startTime.isBefore(roomlist.get(i).getTimeEnd()));
            endBetween = (roomlist.get(i).getTimeEnd().isAfter(startTime)
                    && roomlist.get(i).getTimeEnd().isBefore(endTime))
                    || (endTime.isAfter(roomlist.get(i).getTimeStart())
                    && endTime.isBefore(roomlist.get(i).getTimeEnd()));
            if ((roomlist.get(i).roomcode.equals(roomcode)) && (roomlist.get(i).getDateStart().equals(dateStart))) {
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
