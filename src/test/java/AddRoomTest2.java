import control.Duke;
import room.Room;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author zkchang97
public class AddRoomTest2 {

    // Test for correct conversion of new Room object to String
    @Test
    void addRoomTest() throws DukeException {
        String room = "SR4";
        int capacity = 123;
        Room newRoom = new Room(room, capacity);
        assertEquals("SR4 capacity: 123", newRoom.toString());
    }
}
