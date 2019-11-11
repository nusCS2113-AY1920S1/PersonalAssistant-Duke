package duke.storage;

import duke.model.task.bookingtasks.Booking;
import duke.model.list.bookinglist.BookingList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static duke.common.Messages.filePathBookingTest;

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
                File file = new File(filePath);
                file.getParentFile().mkdir();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Unknown IO error when creating 'data/' folder.");
            }
        }
        try {
            InputStream inputStream;
            if (filePath.equals(filePathBookingTest)) {
                inputStream = getClass().getResourceAsStream("/datatest/bookingsTest.txt");
            } else {
                inputStream = getClass().getResourceAsStream("/data/bookings.txt");
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader1 = new BufferedReader(fileReader);
            String content = "";
            ArrayList<String> arrayList1 = new ArrayList<>();
            ArrayList<String> arrayList2 = new ArrayList<>();

            while ((content = bufferedReader.readLine()) != null){
                arrayList1.add(content);
            }

            while ((content = bufferedReader1.readLine()) != null){
                arrayList2.add(content);
            }
            List<String> listTwoCopy = new ArrayList<>(arrayList2);
            listTwoCopy.removeAll(arrayList1);
            arrayList1.addAll(listTwoCopy);

            for (String item : arrayList1){

                if(item.split("\\|",6)[0].trim().equals("booking")) {

                    String customerName = item.split("\\|",6)[1].trim();
                    String customerContact = item.split("\\|",6)[2].trim();
                    String numberOfPax = item.split("\\|",6)[3].trim();
                    String bookingDate = item.split("\\|",6)[4].trim();
                    String orderName = item.split("\\|",6)[5].trim();
                    Booking booking = new Booking(customerName, customerContact, numberOfPax, bookingDate, orderName);
                    arrBookingList.add(booking);
                }
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
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
