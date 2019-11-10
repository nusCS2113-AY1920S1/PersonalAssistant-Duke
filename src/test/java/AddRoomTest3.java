import command.AddRoomCommand;
import room.Room;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author zkchang97
public class AddRoomTest3 {

    // Test for correct message thrown when invalid date is input
    @Test
    void addRoomTest1() {
        String roomcode = "SR4";
        String dateStartTime = "19/13/2019 0900";
        String endTime = "2100";
        assertThrows(DukeException.class, () -> {
            new Room(roomcode, dateStartTime, endTime);
        });
        assertEquals(Constants.INVALIDDATETIME, "Please enter a valid date or time.");
    }

    // Test for correct message thrown when only "addroom" is input
    @Test
    void addRoomTest2() {
        String input = "addroom";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new AddRoomCommand(input, splitStr);
        });
        assertEquals(Constants.ADDROOMFORMAT, "Please enter the following to add a room:\n"
                + "addroom ROOMCODE /date DD/MM/YYYY HHMM /to HHMM.\n");
    }

    // Test for correct error thrown when "addroom <room code>" is input
    @Test
    void addRoomTest3() {
        String input = "addroom SR4";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new AddRoomCommand(input, splitStr);
        });
        assertEquals(Constants.WRONGDATETIMEFORMAT, "Please enter valid date and start-time for the room.");
    }

    //Test for correct message thrown when no timings are input
    @Test
    void addRoomTest4() {
        String input = "addroom SR4 /date 19/08/2019";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new AddRoomCommand(input,splitStr);
        });
        assertEquals(Constants.NOSTARTENDTIME, "Please enter a start/end time for the room.");
    }

    // Test for correct error thrown when ending time is not input
    @Test
    void addRoomTest5() {
        String input = "addroom SR4 /date 19/08/2019 0900";
        String[] splitStr = input.split(" ");
        assertThrows(DukeException.class, () -> {
            new AddRoomCommand(input, splitStr);
        });
        assertEquals(Constants.NOSTARTENDTIME, "Please enter a start/end time for the room.");
    }

    // Test for correct message thrown when incorrect date format is input
    @Test
    void addRoomTest6() {
        String roomcode = "SR4";
        String startDateTime = "19-08-2019 0900";
        String endTime = "2100";
        assertThrows(DukeException.class, () -> {
            new Room(roomcode, startDateTime, endTime);
        });
        assertEquals(Constants.INVALIDDATETIME, "Please enter a valid date or time.");
    }

    // Test for correct message thrown when invalid time is input
    @Test
    void addRoomTest7() {
        String roomcode = "SR4";
        String startDateTime = "19/08/2019 0900";
        String endTime = "2500";
        assertThrows(DukeException.class, () -> {
            new Room(roomcode, startDateTime, endTime);
        });
        assertEquals(Constants.INVALIDDATETIME, "Please enter a valid date or time.");
    }
}
