package storage;

import room.Room;
import room.RoomList;
import booking.Booking;
import booking.BookingList;
import inventory.Inventory;
import inventory.Item;
import user.User;
import user.UserList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles read and write to text file to contain task list.
 */
public class Storage {

    /**
     * Text file to read or write.
     */
    private File fileToRead;

    public Storage(String filePath) {
        fileToRead = new File(filePath);
    }

    /**
     * Convert contents of text file to task list.
     * @return Tasklist
     * @throws FileNotFoundException if file not found
     */
    public ArrayList<String> load() throws FileNotFoundException {
        Scanner scanFile = new Scanner(fileToRead);
        ArrayList<String> textLoaded = new ArrayList<>();
        while (scanFile.hasNextLine()) {
            String line = scanFile.nextLine();
            textLoaded.add(line);
        }
        return textLoaded;
    }

    /**
     * To write new entries or changes from the list of bookings to file.
     * @param bookingList the list used
     * @throws IOException entry error
     */
    public void saveToFile(BookingList bookingList) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileToRead);
        String toWrite = "";
        for (Booking i : bookingList) {
            toWrite += i.toWriteFile();
        }
        fileOutputStream.write(toWrite.getBytes());
        fileOutputStream.close();
    }

    /**
     * To write new entries or changes from the list of rooms to file.
     * @param roomList the list of rooms
     * @throws IOException invalid entry
     */
    public void saveToFile(RoomList roomList) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileToRead);
        String toWrite = "";
        for (Room i : roomList) {
            toWrite += i.toWriteFile();
        }
        fileOutputStream.write(toWrite.getBytes());
        fileOutputStream.close();
    }

    /**
     * To write to file for inventory.
     * @param inventory inventory input
     * @throws IOException parse error
     */
    public void saveToFile(Inventory inventory) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileToRead);
        String toWrite = "";
        for (Item i : inventory) {
            toWrite += i.toWriteFile();
        }
        fileOutputStream.write(toWrite.getBytes());
        fileOutputStream.close();
    }

    /**
     * To write to file for users.
     * @param userList list of users
     * @throws IOException entry error
     */
    public void saveToFile(UserList userList) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileToRead);
        String toWrite = "";
        for (User i : userList) {
            toWrite += i.toWriteFile();
        }
        fileOutputStream.write(toWrite.getBytes());
        fileOutputStream.close();
    }
}
