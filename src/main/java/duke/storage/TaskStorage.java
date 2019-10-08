package duke.storage;

import duke.core.DukeException;
import duke.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TaskStorage extends Storage<Task>{

    private String filePath;

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
        File newDuke = new File(filePath);
        System.out.println("Hi.");
        return new ArrayList<Task>();
    }


    /**
     * Saves tasks to the local file.
     *
     * @param tasks The TaskList storing tasks.
     * @throws DukeException If writing to the local file failed.
     */
    @Override
    public void save(ArrayList<Task> tasks) throws DukeException {
//        try {
//            FileWriter fileWriter = new FileWriter("./data/duke.txt");
//            for (Task t : task) {
//                fileWriter.write(t.writeTxt() + System.lineSeparator());
//            }
//            fileWriter.close();
//        } catch (IOException e) {
//            throw new DukeException("File writing process encounters an error " + e.getMessage());
//        }
    }
}
