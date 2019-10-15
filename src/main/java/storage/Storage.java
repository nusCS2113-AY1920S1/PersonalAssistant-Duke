package storage;

import room.RoomList;
import room.Room;
import task.Task;
import task.TaskList;
import booking.Booking;
import booking.BookingList;

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
     * Save task list to text file.
     * @param tasks task list
     * @throws IOException for IO exception
     */
    public void saveToFile(TaskList tasks) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileToRead);
        String toWrite = "";
        for (Task i : tasks) {
            toWrite += i.toWriteFile();
        }
        fileOutputStream.write(toWrite.getBytes());
        fileOutputStream.close();
    }

    public void saveToFile(BookingList bookingList) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileToRead);
        String toWrite = "";
        for (Booking i : bookingList) {
            toWrite += i.toWriteFile();
        }
        fileOutputStream.write(toWrite.getBytes());
        fileOutputStream.close();
    }

    public void saveToFile(RoomList roomList) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileToRead);
        String toWrite = "";
        for (Room i : roomList) {
            toWrite += i.toWriteFile();
        }
        fileOutputStream.write(toWrite.getBytes());
        fileOutputStream.close();
    }
}
