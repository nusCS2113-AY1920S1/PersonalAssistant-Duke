package duke;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;

/**
 * Represents a <code>Storage</code> class to store and read
 * <code>Task</code> objects to/from harddisk.
 */
public class Storage {
    /**
     * Writes <code>Task</code> objects to harddisk.
     * @param tasklist <code>TaskList</code> that contains <code>Task</code> objects.
     * @throws DukeException Exception if the file cannot be written to.
     */
    public void writeToFile(TaskList tasklist) throws DukeException {
        try {
            ArrayList<Task> arr = tasklist.getTasks();
            String filename = "output.txt";
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            for (Task task : arr) {
                out.write(task + "\n");
            }
            out.close();
        } catch (IOException e) {
            System.out.println(e + ", thus please try inputting other things.");
        }
    }

    protected ArrayList<Task> arr = new ArrayList<Task>();

    /**
     * Reads <code>Task</code> objects that were previously saved to harddisk.
     * @return <code>TaskList</code> containing <code>Task</code> objects.
     */
    public ArrayList<Task> readFromFile() throws DukeException {
        try {
            String filename = "output.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                counter += 1;
                boolean checker = false;
                if (line.startsWith("[T]")) {
                    checker = checkDone(line);
                    line = line.substring(7).trim();
                    Task task = new Todo(line);
                    arr.add(task);
                    if (checker) {
                        arr.get(counter - 1).setStatus();
                    }
                } else if (line.startsWith("[D]")) {
                    checker = checkDone(line);
                    line = line.substring(7).trim();
                    String[] linesplit = line.split("\\(by:");
                    String start = linesplit[0].trim();
                    String end = linesplit[1].trim();
                    end = end.substring(0, end.length() - 1);
                    Task task = new Deadline(start, end);
                    arr.add(task);
                    if (checker) {
                        arr.get(counter - 1).setStatus();
                    }
                } else if (line.startsWith("[E]")) {
                    checker = checkDone(line);
                    line = line.substring(7).trim();
                    String[] lineSplit = line.split(" \\(from: ");
                    String description = lineSplit[0].trim();
                    String[] dateSplit = lineSplit[1].split(" to: ");
                    dateSplit[1] = dateSplit[1].substring(0, dateSplit[1].length() - 1);
                    Task task = new Event(description, dateSplit[0], dateSplit[1]);
                    arr.add(task);
                    if (checker) {
                        arr.get(counter - 1).setStatus();
                    }
                }
            }
            reader.close();
            return arr;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace(System.out);
            System.out.println("Look at the output.txt for any irregularities!");
        }
        return arr;
    }

    /**
     * Checks if the <code>Task</code> has already been marked as done.
     * @param line <code>Task</code> object in string format.
     * @return true if the <code>Task</code> object has already been marked as done, false otherwise.
     */
    public boolean checkDone(String line) {
        return line.charAt(4) == '\u2713'; //u2713 is a tick emoticon
    }

    /**
     * Checks if a <code>Task</code> has a <code>Timestamp</code>.
     * @param index Index of the <code>Task</code> in the <code>TaskList</code>.
     * @return True if <code>Task</code> has a <code>Timestamp</code>, false otherwise.
     */
    public String taskHasTimestamp(int index) {
        try {
            String filename = "output.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                boolean checker = false;
                if ((counter == index) && (line.startsWith("[D]"))) {
                    reader.close();
                    return "deadline";
                } else if ((counter == index) && (line.startsWith("[E]"))) {
                    return "event";
                }
                counter += 1;
            }
            reader.close();
            return "none";
        } catch (Exception e) {
            System.out.println("Something went wrong while checking if the task has a timestamp!");
            return "none";
        }
    }
}
