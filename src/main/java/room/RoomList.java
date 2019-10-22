package room;

import exception.DukeException;
import storage.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RoomList extends ArrayList<AddRoom> {

    public RoomList(ArrayList<String> loader) {
        for (String line : loader) {
            String[] splitStr = line.split("\\|", 4);
            this.add(new AddRoom(splitStr[Constants.ROOMCODE],
                    splitStr[Constants.ROOMDATE], splitStr[Constants.ROOMTIMESLOT]));
        }
    }

    public RoomList() {
        super();
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
