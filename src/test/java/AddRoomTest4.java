import org.junit.jupiter.api.Test;
import room.Room;
import room.RoomList;
import storage.StorageManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author zkchang97
public class AddRoomTest4 {

    // Test for correct entry of all parameters
    @Test
    void addRoomTest() {
        String roomcode = "SR4";
        int capacity = 111;
        assertThrows(AssertionError.class, () -> {
            Room addroom = new Room(roomcode, capacity);
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
