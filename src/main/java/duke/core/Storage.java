package duke.core;

import duke.task.Task;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a Storage class that deals with reading tasks from
 * a file and saving tasks in the file.
 */
public class Storage {
    /**
     * A string that represents a relative file path from the project folder.
     */
    private String filePath;

    /**
     * Constructs a Storage object with a specific file path.
     *
     * @param path A string that represents the path of the file to read or
     *             write.
     */
    public Storage(String path) {
        this.filePath = path;
    }

    /**
     * Read tasks from the file and store into a ArrayList of task.
     *
     * @return A ArrayList of tasks from the file.
     * @throws DukeException If file is not found.
     */
    public ArrayList<Task> load() throws DukeException {
        File newDuke = new File(filePath);
        System.out.println("Hi.");
        return new ArrayList<Task>();
    }

    /**
     * Saves tasks to the local file.
     *
     * @param task The TaskList storing tasks.
     * @throws DukeException If writing to the local file failed.
     */
    public void save(ArrayList<Task> task) throws DukeException {
        try {
            FileWriter fileWriter = new FileWriter("./data/duke.txt");
            for (Task t : task) {
                fileWriter.write(t.writeTxt() + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new DukeException("File writing process encounters an error " + e.getMessage());
        }
    }



}
