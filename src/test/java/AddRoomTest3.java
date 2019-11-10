import room.Room;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author zkchang97
public class AddRoomTest3 {

    @Test
    void addRoomTest() {
        String roomcode = "SR4";
        String dateStartTime = "19/13/2019 0900";
        String endTime = "2100";
        assertThrows(DukeException.class, () -> {
            new Room(roomcode, dateStartTime, endTime);
        });
        assertEquals(Constants.INVALIDDATETIME, "Please enter a valid date/time.");
    }
}
