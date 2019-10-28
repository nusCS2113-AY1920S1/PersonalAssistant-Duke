package duke.storage;

import duke.exception.DukeException;
import duke.model.task.bookingtasks.Booking;
import duke.model.list.bookinglist.BookingList;


import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Handles the ability to read and write to the storage location.
 */
public class BookingStorage {
    private static final ArrayList<Booking> arrBookingList = new ArrayList<>();
    private final String filePath;

    /**
     * Constructor for the class Storage.
     *
     * @param filePath String containing the directory in which the tasks are to be stored
     */
    public BookingStorage(String filePath) {
        this.filePath = filePath;
    }

    /**

     * Writing to file to save the task to file.
     *
     * @param bookingList contains the task list
     */
    public void saveFile(BookingList bookingList) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Booking booking : bookingList.getBookingList()) {
                bufferedWriter.write(booking.toSaveString() + "\n");
            }
            bufferedWriter.close();
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    /**
     * Load all the save tasks in the file.
     *
     * @return the list of tasks in taskList
     * @throws DukeException if Duke is not able to load the tasks from the file or unable to open the file
     */
    public ArrayList<Booking> load() throws DukeException {
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {

                if(content.split("\\|",6)[0].trim().equals("booking")) {

                    String customerName = content.split("\\|",6)[1].trim();
                    String customerContact = content.split("\\|",6)[2].trim();
                    String numberOfPax = content.split("\\|",6)[3].trim();
                    String bookingDate = content.split("\\|",6)[4].trim();
                    String orderName = content.split("\\|",6)[5].trim();
                    Booking booking = new Booking(customerName, customerContact, numberOfPax, bookingDate, orderName);
                    arrBookingList.add(booking);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return arrBookingList;
    }

}
