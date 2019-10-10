package moomoo.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
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

    /**
     * Takes in the filePath for future I/O.
     * @param filePath String representing the path of the file to be written and read from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads in data from an existing file into a created TaskList object.
     * @return TaskList object consisting of the data read from the file.
     * @throws MooMooException Thrown when the file does not exist
     */
    public ArrayList<Task> load() throws MooMooException {
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            if (Files.isRegularFile(Paths.get(this.filePath))) {
                List<String> input = Files.readAllLines(Paths.get(this.filePath));

                for (String value : input) {
                    String[] splitInput = value.split(" \\| ");


                }
            } else {
                throw new MooMooException("");
            }
        } catch (IOException e) {
            createFileAndDirectory();
        }
        return taskList;
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
        String toSave = "";
        createFileAndDirectory();

        for (int i = 0; i < TaskList.getSize(); ++i) {
            Task value = TaskList.getTask(i);
            String taskType = "";
            int isDone = 0;
            String description = value.description;
            String newDate = "";
            String endDate = "";
            String newTask = "";

            if (value.isDone) {
                isDone = 1;
            } else {
                isDone = 0;
            }
            if (newDate != "") {
                if (endDate != "") {
                    toSave += taskType + " | " + Integer.toString(isDone) + " | " + description
                            + " | " + newDate + " | " + endDate + "\n";
                } else {
                    toSave += taskType + " | " + Integer.toString(isDone) + " | " + description
                            + " | " + newDate + "\n";
                }
            } else {
                if (taskType.equals("A")) {
                    toSave += taskType + " | " + Integer.toString(isDone) + " | " + description
                            + " | " + newTask + "|n";
                } else {
                    toSave += taskType + " | " + Integer.toString(isDone) + " | " + description + "\n";
                }
            }
        }
        try {
            Files.writeString(Paths.get(this.filePath), toSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}