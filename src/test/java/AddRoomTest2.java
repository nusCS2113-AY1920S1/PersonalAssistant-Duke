import control.Duke;
import room.Room;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author zkchang97
public class AddRoomTest2 {

    @Test
    void addRoomTest() throws DukeException {
        String room = "SR4";
        String dateTimeStart = "12/12/2021 0700";
        String timeEnd = "1900";
        Room newRoom = new Room(room, dateTimeStart, timeEnd);
        assertEquals(newRoom.toString(), "SR4 12/12/2021 0700 to 1900");
    }
}
