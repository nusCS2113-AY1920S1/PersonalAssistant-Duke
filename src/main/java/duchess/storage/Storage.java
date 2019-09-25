package duchess.storage;

import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.TaskList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Storage {
    private String fileName;

    public Storage(String fileName) {
        this.fileName = fileName;
    }

    // Unchecked type coercion is necessary here.
    // And possible cast exceptions are handled

    /**
     * Returns the tasklist loaded from file.
     */
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

    /**
     * Saves the given tasklist to file.
     *
     * @param taskList the tasklist to be saved
     * @throws DukeException an error if unable to write to file
     */
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
