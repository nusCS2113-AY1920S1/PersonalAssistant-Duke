package duke.storage;

import duke.commons.DukeException;
import duke.commons.MessageUtil;
import duke.parsers.ParserStorageUtil;
import duke.tasks.Deadline;
import duke.tasks.DoWithin;
import duke.tasks.Event;
import duke.tasks.RecurringTask;
import duke.tasks.Task;
import duke.tasks.TaskWithDates;
import duke.tasks.UniqueTaskList;
import duke.ui.Ui;

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
    private UniqueTaskList tasksWithDate;
    private Ui ui;

    /**
     * Constructs a Storage object that contains duke.tasks and duke.storage related operations.
     *
     * @param filePath The filepath to the txt file.
     * @param ui The user interface displaying events on the task list.
     */
    public Storage(String filePath, Ui ui) throws DukeException {
        this.filePath = filePath;
        this.ui = ui;
        tasks = new UniqueTaskList();
        tasksWithDate = new UniqueTaskList();
        read();
    }

    /**
     * Reads duke.tasks from filepath. Creates empty duke.tasks if file cannot be read.
     */
    public void read() throws DukeException {
        List<Task> newTasks = new ArrayList<>();
        try {
            File f = new File(filePath);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                Task newTask = ParserStorageUtil.createTaskFromStorage(s.nextLine());
                if (newTask instanceof RecurringTask) {
                    ((RecurringTask) newTask).updateRecurringTask();
                }
                if (newTask instanceof Deadline || newTask instanceof DoWithin || newTask instanceof Event
                        || newTask instanceof RecurringTask) {
                    tasksWithDate.add(newTask);
                }
                newTasks.add(newTask);
            }
            s.close();
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        } catch (FileNotFoundException e) {
            ui.showError(MessageUtil.FILE_NOT_FOUND);
        }
        try {
            tasks.setTasks(newTasks);
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }

        try {
            for (Task task : newTasks) {
                if (task instanceof TaskWithDates) {
                    tasksWithDate.add(task);
                }
            }
        } catch (DukeException e) {
            ui.showError(e.getMessage());

        }
    }

    /**
     * Writes the duke.tasks into a file of the given filepath.
     */
    public void write() {
        try {
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                writer.write(ParserStorageUtil.toStorageString(task) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            ui.showError(MessageUtil.FILE_NOT_SAVE);
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }
    }

    public UniqueTaskList getTasks() {
        return tasks;
    }

    public UniqueTaskList getTasksWithDate() {
        return tasksWithDate;
    }
}
