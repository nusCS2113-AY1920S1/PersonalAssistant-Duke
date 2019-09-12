package duke;

import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.ToDo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * A class to implement persistent storage of the task list using a .txt file.
 */
public class Storage {

    private Path file;

    /**
     * Constructor for the duke.Storage class.
     *
     * @param file the Path object representing the path to the file being used to store the task list.
     */
    Storage(Path file) {
        this.file = file;
    }

    /**
     * Returns an ArrayList of the String representations of all the duke.tasks.Task objects in the task list.
     *
     * @param list the task list containing all the duke.tasks.Task objects
     * @return an ArrayList of the String representations of the tasks in the task list
     */
    private ArrayList<String> formatFile(ArrayList<Task> list) {
        ArrayList<String> result = new ArrayList<>();
        for (Task task : list) {
            result.add(task.toString());
        }
        return result;
    }

    /**
     * Writes the task list to the .txt file.
     *
     * @param tasks an ArrayList of the String representations of the tasks in the task list
     * @throws DukeException in the case of input or output exceptions
     */
    private void writeFile(ArrayList<String> tasks) throws DukeException {
        try {
            Files.write(file, tasks, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DukeException("","io");
        }
    }

    /**
     * Reads the .txt fil and returns an ArrayList of Strings that represent the tasks in the task
     * list
     *
     * @return an ArrayList of Strings that represent the tasks in the task list
     * @throws DukeException in the case of input or output exceptions
     */
    private ArrayList<String> readFile() throws DukeException {
        // reads file and returns an ArrayList of lines
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception e) {
            throw new DukeException("", "io");
        }
        return result;
    }

    /**
     * After reading the file, converts each String representation back into its corresponding
     * duke.tasks.Task object and pushes it into the duke.TaskList.
     *
     * @param taskList the duke.TaskList object used to store the task list
     * @throws DukeException in the case of input or output exceptions
     */
    void loadList(TaskList taskList) throws DukeException {
        // loads data into list
        ArrayList<String> data = readFile();
        for (String line: data) {
            convertString(taskList, line);
        }
    }

    /**
     * Interprets the String, translates it to the appropriate duke.tasks.Task object, and adds it
     * to the duke.TaskList.
     *
     * @param taskList the duke.TaskList object used to store the task list
     * @param s the String representation to be converted
     * @throws DukeException in the case of input or output exceptions
     */
    private void convertString(TaskList taskList, String s) throws DukeException {
        try {
            String type = s.substring(1,2); // T, D or E
            boolean isDone = s.substring(4,5).equals("v");
            String description;
            String addendum;
            switch (type) {
            case "T":
                description = s.substring(7);
                ToDo todo = new ToDo(description);
                if (isDone) {
                    todo.setDone();
                }
                taskList.add(todo);
                break;
            case "E": {
                String[] sections = s.substring(7).split("at:");
                description = sections[0].substring(0, sections[0].length() - 2);
                addendum = sections[1].substring(1, sections[1].length() - 1);
                Event event = new Event(description, addendum);
                if (isDone) {
                    event.setDone();
                }
                taskList.add(event);
                break;
            }
            case "D": {
                String[] sections = s.substring(7).split("by:");
                description = sections[0].substring(0, sections[0].length() - 2);
                addendum = sections[1].substring(1, sections[1].length() - 1);
                Deadline deadline = new Deadline(description, addendum);
                if (isDone) {
                    deadline.setDone();
                }
                taskList.add(deadline);
                break;
            }
            default:
                throw new DukeException("","io");
            }

        } catch (Exception e) {
            throw new DukeException("","io");
        }
    }

    /**
     * Updates the .txt file with the latest task list found within the duke.Duke program.
     *
     * @param taskList the duke.TaskList object used to store the task list
     * @throws DukeException in the case of input or output exceptions
     */
    public void updateFile(TaskList taskList) throws DukeException {
        writeFile(formatFile(taskList.getTaskList()));
    }

}
