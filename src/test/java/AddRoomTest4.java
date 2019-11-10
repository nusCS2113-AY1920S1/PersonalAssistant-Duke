import command.AddRoomCommand;
import control.Duke;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import room.Room;
import storage.Constants;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author zkchang97
public class AddRoomTest4 {

    @Test
    void addRoomTest() throws DukeException {
        String input = "addroom";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new AddRoomCommand(input, splitStr);
        });
        assertEquals(Constants.ADDROOMFORMAT, "Please enter the following to add a room:\n"
                + "addroom ROOMCODE /date DD/MM/YYYY HHMM /to HHMM.\n");
    }

    @Test
    void addRoomTest1() {
        String input = "addroom SR4";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new AddRoomCommand(input, splitStr);
        });
        assertEquals(Constants.WRONGDATETIMEFORMAT, "Please enter correct date and start-time for the room.");
    }

    @Test
    void addRoomTest2() {
        String input = "addroom SR4 /date 19/08/2019";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
           new AddRoomCommand(input,splitStr);
        });
        assertEquals(Constants.NOSTARTENDTIME, "Please enter a start/end time for the room.");
    }

    @Test
    void addRoomTest3() {
        String input = "addroom SR4 /date 19/08/2019 0900";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new AddRoomCommand(input, splitStr);
        });
        assertEquals(Constants.NOSTARTENDTIME, "Please enter a start/end time for the room.");
    }

    @Test
    void addRoomTest4() {
        String roomcode = "SR4";
        String startDateTime = "19/08/2019 0900";
        String endTime = " ";
        assertThrows(DukeException.class, () -> {
            new Room(roomcode, startDateTime, endTime);
        });
        assertEquals(Constants.INVALIDDATETIME, "Please enter a valid date or time.");
    }
}
