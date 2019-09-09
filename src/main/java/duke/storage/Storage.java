package duke.storage;

import duke.commons.DukeException;
import duke.task.TaskList;

import java.io.*;

/**
 * Stores tasks in file and reads tasks from file.
 */
public class Storage {

    private String path;

    /**
     * Constructor for Storage.
     *
     * @param path the path to store the file, including the file name.
     */
    public Storage(String path) {
        this.path = path;
    }

    /**
     * Serialize TaskList to file.
     *
     * @param tasks the TaskList to serialize.
     * @throws DukeException if fails to serialize due to IO exception.
     */
    public void serialize(TaskList tasks) throws DukeException {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tasks);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            throw new DukeException("IO Exception");
        }
    }

    /**
     * Deserialize TaskList from file. If the file is not found, returns an empty TaskList.
     *
     * @return a TaskList object.
     * @throws DukeException if file is damaged.
     */
    public TaskList deserialize() throws DukeException {
        TaskList tasks = null;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tasks = (TaskList) in.readObject();
            in.close();
            fileIn.close();
            return tasks;
        } catch (ClassNotFoundException c) {
            throw new DukeException("File is damaged");
        } catch (IOException i) {
            if (i instanceof FileNotFoundException) {
                return new TaskList();
            } else {
                throw new DukeException("IO Exception");
            }
        }
    }
}
