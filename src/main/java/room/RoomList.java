package room;

import storage.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RoomList extends ArrayList<AddRoom> {

    /**
     * Creates room list from text file
     * @param loader Strings from text file containing room list info
     */
    public RoomList(ArrayList<String> loader) {
        for (String line : loader) {
            String[] splitStr = line.split("\\|", 4);
            this.add(new AddRoom(splitStr[Constants.ROOMCODE],
                    splitStr[Constants.ROOMDATE], splitStr[Constants.ROOMTIMESLOT]));
        }
    }

    /**
     * Public constructor in Duke.java
     */
    public RoomList() {
        super();
    }

    /**
     * Checks if a room to be added is already present in the room list file
     * @param roomcode Room code of the target room
     * @param filePath Directory of the text file
     * @return if the room already exists in the file
     * @throws IOException if the input is in the wrong format
     */
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
