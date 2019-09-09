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
            for (int i = 0; i < arr.size(); i++) {
                out.write(arr.get(i) + "\n");
            }
            out.close();
        }
        catch (IOException e) {
            System.out.println(e + ", thus please try inputting other things.");
        }
    }
    protected ArrayList<Task> arr = new ArrayList<Task>();

    /**
     * Reads <code>Task</code> objects that were previously saved to harddisk.
     * @return <code>TaskList</code> containing <code>Task</code> objects.
     * @throws DukeException Exception if the file does not exist or cannot be read.
     */
    public ArrayList<Task> readFromFile() throws DukeException {
        try {
            String filename = "output.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                counter += 1;
                if (line.startsWith("[T]")) {
                    boolean checker = checkDone(line);
                    line = line.substring(7).trim();
                    Task task = new Todo(line);
                    arr.add(task);
                    if (checker) {
                        arr.get(counter - 1).setStatus();
                    }
                }
                else if (line.startsWith("[D]")) {
                    boolean checker = checkDone(line);
                    line = line.substring(7).trim();
                    String linesplit[] = line.split("\\(by:");
                    String start = linesplit[0].trim();
                    String end = linesplit[1].trim();
                    end = end.substring(0, end.length() - 1);
                    Task task = new Deadline(start, end);
                    arr.add(task);
                    if (checker) {
                        arr.get(counter - 1).setStatus();
                    }
                }
                else if (line.startsWith("[E]")) {
                    boolean checker = checkDone(line);
                    line = line.substring(7).trim();
                    String linesplit[] = line.split("\\(at:");
                    String start = linesplit[0].trim();
                    String end = linesplit[1].trim();
                    end = end.substring(0, end.length() - 1);
                    Task task = new Event(start, end);
                    arr.add(task);
                    if (checker) {
                        arr.get(counter - 1).setStatus();
                    }
                }
            }
            reader.close();
            return arr;
        }
        catch (Exception e) {
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
        if (line.charAt(4) == '\u2713') {
            return true;
        }
        return false;
    }
}
