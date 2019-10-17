package moomoo.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles storage and retrieval of the tasks.
 */
public class Storage {
    private String filePath;
    private DecimalFormat df;

    /**
     * Takes in the filePath for future I/O.
     * @param filePath String representing the path of the file to be written and read from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        df = new DecimalFormat("#.00");
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
     * Loads in budget from an existing file into a created ArrayList object.
     * @return ArrayList object consisting of the budget read from the file.
     * @throws MooMooException Thrown when the file does not exist
     */
    public double loadBudget() throws MooMooException {
        try {
            if (Files.isRegularFile(Paths.get(this.filePath))) {
                List<String> input = Files.readAllLines(Paths.get(this.filePath));
                for (String value : input) {
                    if (value.charAt(0) == 'B') {
                        String[] splitInput = value.split(" \\| ");
                        return Double.parseDouble(splitInput[1]);
                    }
                }
                throw new MooMooException("Unable to load budget from file. Please reset your budget.");
            } else {
                throw new MooMooException("File not found. New file will be created");
            }
        } catch (IOException e) {
            throw new MooMooException("Unable to write to file. Please retry again.");
        }
    }

    /**
     * Loads scheduled payments from file into an ArrayList object.
     * @return ArrayList object consisting of the scheduled payments read from the file
     * @throws MooMooException Thrown when file does not exist
     */
    public ArrayList<SchedulePayment> loadCalendar() throws MooMooException {
        ArrayList<SchedulePayment> scheduleArray = new ArrayList<>();
        try {
            if (Files.isRegularFile(Paths.get(this.filePath))) {
                List<String> input = Files.readAllLines(Paths.get(this.filePath));
                for (String s : input) {
                    if (s.startsWith("d/")) {
                        String[] splitInput = s.split(" ", 2);
                        String date = splitInput[0].replace("d/","");
                        String task = splitInput[1].replace("t/", "");
                        SchedulePayment day = new SchedulePayment(date, task);
                        scheduleArray.add(day);
                    }
                }
                return scheduleArray;
            } else {
                throw new MooMooException("File not found. New file will be created");
            }
        } catch (IOException e) {
            throw new MooMooException("Unable to read file. Please retry again.");
        }
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
     * Creates the file as necessary, reads Budget and converts each value into a string and writes it to file.
     */
    public void saveBudgetToFile(Budget budget) throws MooMooException {
        createFileAndDirectory();

        String toSave = "B" + " | " + df.format(budget.getBudget()) + "\n";
        try {
            Files.writeString(Paths.get(this.filePath), toSave);
        } catch (Exception e) {
            throw new MooMooException("Unable to write to file. Please retry again.");
        }
    }

    /**
     * Writes scheduled payments to file.
     */
    public void saveScheduleToFile(ScheduleList calendar) throws MooMooException {
        createFileAndDirectory();

        String list = "Schedule: \n";
        System.out.println(calendar.fullSchedule.size());
        for (SchedulePayment c : calendar.fullSchedule) {
            list += "d/" + c.date + " t/" + c.tasks + "\n";
        }
        try {
            Files.writeString(Paths.get(this.filePath), list);
        } catch (Exception e) {
            throw new MooMooException("Unable to write to file. Please try again.");
        }
    }
}