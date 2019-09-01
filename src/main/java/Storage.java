import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private String fileName;

    public Storage(String fileName) {
        this.fileName = fileName;
    }

    // Unchecked type coercion is necessary here.
    // And possible cast exceptions are handled
    @SuppressWarnings("unchecked")
    public TaskList load() throws DukeException {
        try {
            FileInputStream fileStream = new FileInputStream(this.fileName);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            TaskList taskList = (TaskList) objectStream.readObject();
            objectStream.close();
            return taskList;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new DukeException("Unable to read file, continuing with empty list.");
        }
    }

    public void save(TaskList taskList) throws DukeException {
        try {
            FileOutputStream fileStream = new FileOutputStream(this.fileName);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(taskList);
            objectStream.close();
        } catch (IOException e) {
            throw new DukeException("An unexpected error occurred when writing to the file. " + e);
        }
    }
}
