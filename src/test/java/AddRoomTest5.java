import org.junit.jupiter.api.Test;
import room.Room;
import room.RoomList;
import storage.StorageManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author zkchang97
public class AddRoomTest5 {

    @Test
    void addRoomTest() {
        String roomcode = "SR4";
        String dateStartTime = "19/08/2019 0800";
        String endTime = "2000";
        assertThrows(AssertionError.class, () -> {
            Room addroom = new Room(roomcode, dateStartTime, endTime);
            RoomList roomList = null;
            StorageManager allStorage = null;
            assert false;
            roomList.add(addroom);
            allStorage.getRoomStorage().saveToFile(roomList);
        });
        assertEquals("Got it, I've added this room.\n"
                + "SR4" + "\n" + "Now you have " + "1" + " room(s) in the list.", "Got it, I've added this room.\n"
                + "SR4" + "\n" + "Now you have " + "1" + " room(s) in the list.");
    }
}
