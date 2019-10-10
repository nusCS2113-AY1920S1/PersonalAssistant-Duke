package moomoo.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Handles storage and retrieval of the tasks.
 */
public class Storage {
    private String filePath;

    /**
     * Takes in the filePath for future I/O.
     * @param filePath String representing the path of the file to be written and read from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads in categories from an existing file into a created ArrayList object.
     * @return ArrayList object consisting of the categories read from the file.
     * @throws MooMooException Thrown when the file does not exist
     */
    public ArrayList<Category> loadCategories() throws MooMooException {
        ArrayList<Category> categoryArrayList = new ArrayList<Category>();

        return categoryArrayList;
    }

    /**
     * Loads in transactions from an existing file into a created ArrayList object.
     * @return ArrayList object consisting of the transactions read from the file.
     * @throws MooMooException Thrown when the file does not exist
     */
    public ArrayList<Transaction> loadTransactions() throws MooMooException {
        ArrayList<Transaction> transactionArrayList = new ArrayList<Transaction>();

        return transactionArrayList;
    }


    /**
     * Creates the directory and file as given by the file path initialized in the constructor.
     */
    private void createFileAndDirectory() {
        try {
            File myNewFile = new File(this.filePath);
            Files.createDirectory(Paths.get(myNewFile.getParent()));
            Files.createFile(Paths.get(this.filePath));
        } catch (FileAlreadyExistsException e) {
            return;
        } catch (IOException e) {
            createFileAndDirectory();
        }
    }

    private LocalDateTime parseDate(String dateToParse) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateToParse, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Converts the LocalDateTime object into printable string for writing to file.
     * @param dateTime LocalDateTime object to be converted
     * @return String format of the LocalDateTime object
     */
    private String unparseDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return dateTime.format(formatter);
    }

    /**
     * Creates the file as necessary, reads the TaskList and converts each value into a string and writes it to file.
     */
    public void saveToFile() {
    }
}