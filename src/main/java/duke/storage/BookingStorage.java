package duke.storage;

import duke.model.task.bookingtasks.Booking;
import duke.model.list.bookinglist.BookingList;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Handles the ability to read and write to the booking storage location.
 */
public class BookingStorage {
    private static final ArrayList<Booking> arrBookingList = new ArrayList<>();
    private final String filePath;

    /**
     * Constructor for the class BookingStorage.
     *
     * @param filePath the directory in which the recipes are to be stored
     */
    public BookingStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Writes to file to save the bookings to file.
     *
     * @param bookingList the list containing recipes
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
     * Loads all the save bookings in the file.
     *
     * @return the list of bookings in booking list
     */
    public ArrayList<Booking> load() {

        if (Files.notExists(Paths.get(filePath))) {
            try {
                Files.createDirectory(Paths.get("data/"));
            } catch (IOException e) {
                System.out.println("Unknown IO error when creating 'data/' folder.");
            }
        }
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
