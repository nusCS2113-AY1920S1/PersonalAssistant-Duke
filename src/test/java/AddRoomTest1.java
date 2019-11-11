import exception.DukeException;
import room.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author zkchang97
public class AddRoomTest1 {

    // Test for correct parameter values after instantiating a new Room
    @Test
    void addRoomTest() throws DukeException {
        String roomcode = "SR4";
        int capacity = 111;
        Room newRoom = new Room(roomcode, capacity);
        assertEquals("SR4 capacity: 111", newRoom.toString());
        assertEquals("SR4", newRoom.getRoomcode());
        assertEquals(111, newRoom.getCapacity());
    }

}
