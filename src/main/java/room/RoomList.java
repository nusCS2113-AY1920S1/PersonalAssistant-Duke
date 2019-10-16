package room;

import exception.DukeException;
import storage.Constants;
import java.util.ArrayList;

public class RoomList extends ArrayList<Room> {

    public RoomList(ArrayList<String> loader) {
        for (String line : loader) {
            String[] splitStr = line.split("\\|", 4);
            this.add(new AddRoom(splitStr[Constants.ISBOOKED], splitStr[Constants.ROOMCODE],
                    splitStr[Constants.ROOMDATE], splitStr[Constants.ROOMTIMESLOT]));
            }
        }
}
