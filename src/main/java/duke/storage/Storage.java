package duke.storage;

import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.logic.parsers.ParserStorageUtil;
import duke.data.tasks.Task;
import duke.data.UniqueTaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages duke.storage of duke.Duke data in local duke.storage.
 */
public class Storage {
    private String filePath;
    private UniqueTaskList tasks;

    /**
     * Constructs a Storage object that contains duke.data.tasks and duke.storage related operations.
     *
     * @param filePath The filepath to the txt file.
     */
    public Storage(String filePath) throws DukeException {
        this.filePath = filePath;
        tasks = new UniqueTaskList();
        read();
    }

    /**
     * Reads tasks from filepath. Creates empty tasks if file cannot be read.
     */
    protected void read() throws DukeException {
        List<Task> newTasks = new ArrayList<>();
        try {
            File f = new File(filePath);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                newTasks.add(ParserStorageUtil.createTaskFromStorage(s.nextLine()));
            }
            s.close();
        } catch (FileNotFoundException e) {
            throw new DukeException(Messages.FILE_NOT_FOUND);
        }
        tasks.setTasks(newTasks);
    }

    /**
     * Writes the tasks into a file of the given filepath.
     */
    public void write() throws DukeException {
        try {
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                writer.write(ParserStorageUtil.toStorageString(task) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new DukeException(Messages.FILE_NOT_SAVE);
        }
    }

    public UniqueTaskList getTasks() {
        return tasks;
    }
}
