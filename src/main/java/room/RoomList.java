package room;

import exception.DukeException;
import storage.Constants;
import java.util.ArrayList;

public class RoomList extends ArrayList<Room> {

    public RoomList(ArrayList<String> loader) throws DukeException {
        for (String line : loader) {
            String[] splitStr = line.split("\\|");
            switch(splitStr[Constants.TYPE]) {
                case "RM":
                    this.add(new AddRoom(splitStr[Constants.ISBOOKED], splitStr[Constants.ROOMCODE],
                            splitStr[Constants.ROOMDATE], splitStr[Constants.ROOMTIMESLOT]));
                    break;
                default:
                    throw new DukeException("File format incorrect.");
            }
        }
    }
}
