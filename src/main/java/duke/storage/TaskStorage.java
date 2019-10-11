package duke.storage;

import duke.core.DukeException;
import duke.task.Task;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TaskStorage {

    /**
     * A string that represents a relative file path from the project folder.
     */
    private String filePath;

    /**
     * Constructs a Storage object with a specific file path.
     *
     * @param filePath A string that represents the path of the file to read or
     *             write.
     */
    public TaskStorage(String filePath) {
        this.filePath = filePath;
    }


    /**
     * Read tasks from the file and store into a ArrayList of task.
     *
     * @return A ArrayList of tasks from the file.
     * @throws DukeException If file is not found.
     */
    public ArrayList<Task> load() throws DukeException {
        ArrayList<Task> taskList = new ArrayList<Task>();
        try {
            Reader in = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                String description = record.get("Description");
                taskList.add(new Task(description));
            }
            return taskList;
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }


    /**
     * Saves tasks to the local file.
     *
     * @param tasks The TaskList storing tasks.
     * @throws DukeException If writing to the local file failed.
     */
    public void save(ArrayList<Task> tasks) throws DukeException {
        try{
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Description"));
            for (Task task : tasks){
                String description = task.getDescription();
                csvPrinter.printRecord(description);
            }
            csvPrinter.flush();
        }
        catch(IOException e){
            throw new DukeException(e.getMessage());
        }
    }
}
