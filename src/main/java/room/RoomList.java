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
            String[] splitStr = line.split(" \\| ", 2);
            this.add(new Room(splitStr[Constants.ROOMCODE], splitStr[Constants.CAPACITY]));
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

    /**
     * Check for room clashes.
     * @param roomList list of rooms
     * @param roomcode room code of room
     * @return
     */
    public static boolean checkRoom(RoomList roomList, String roomcode) {
        for (Room i : roomList) {
            if (i.roomcode.equals(roomcode)) {
                return true;
            }
        }
        return false;
    }
}
