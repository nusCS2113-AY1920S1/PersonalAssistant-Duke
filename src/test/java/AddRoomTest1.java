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
        String dateStartTime = "19/02/2019 0800";
        String endTime = "2000";
        Room newRoom = new Room(roomcode, dateStartTime, endTime);
        assertEquals("SR4 19/02/2019 0800 to 2000", newRoom.toString());
        assertEquals("SR4", newRoom.getRoomcode());
        assertEquals("2019-02-19", newRoom.getDateStart().toString());
        assertEquals("08:00", newRoom.getTimeStart().toString());
        assertEquals("20:00", newRoom.getTimeEnd().toString());
    }

}
