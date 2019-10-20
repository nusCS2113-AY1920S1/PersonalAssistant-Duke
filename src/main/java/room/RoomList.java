package room;

import exception.DukeException;
import storage.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public static boolean checkRoom(String roomcode,String filePath) throws IOException {
        BufferedReader reader;
        boolean found = false;
        reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();

        while (line != null){
            if(line.equals(roomcode))
                found = true;
                line = reader.readLine();
            break;
        }
        reader.close();
        return found;
    }
}
