package oof;

import oof.task.Deadline;
import oof.task.Event;
import oof.task.Task;
import oof.task.Todo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a Storage class to store and read
 * Task objects to/from hard disk.
 */
public class Storage {

    /**
     * Writes Task objects to hard disk.
     *
     * @param taskList TaskList that contains Task objects.
     */
    public void writeToFile(TaskList taskList) {
        try {
            ArrayList<Task> arr = taskList.getTasks();
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
     * Reads Task objects that were previously saved to hard disk.
     *
     * @return TaskList containing Task objects.
     * @throws IOException if file does not exist.
     */
    public ArrayList<Task> readFromFile() throws IOException {
        String filename = "output.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int counter = 0;
        while ((line = reader.readLine()) != null) {
            counter += 1;
            if (isTodo(line)) {
                line = line.substring(7).trim();
                Task task = new Todo(line);
                arr.add(task);
                if (checkDone(line)) {
                    arr.get(counter - 1).setStatus();
                }
            } else if (isDeadline(line)) {
                line = line.substring(7).trim();
                String[] lineSplit = line.split("\\(by:");
                String start = lineSplit[0].trim();
                String end = lineSplit[1].trim();
                end = end.substring(0, end.length() - 1);
                Task task = new Deadline(start, end);
                arr.add(task);
                if (checkDone(line)) {
                    arr.get(counter - 1).setStatus();
                }
            } else if (isEvent(line)) {
                line = line.substring(7).trim();
                String[] lineSplit = line.split(" \\(from: ");
                String description = lineSplit[0].trim();
                String[] dateSplit = lineSplit[1].split(" to: ");
                dateSplit[1] = dateSplit[1].substring(0, dateSplit[1].length() - 1);
                Task task = new Event(description, dateSplit[0], dateSplit[1]);
                arr.add(task);
                if (checkDone(line)) {
                    arr.get(counter - 1).setStatus();
                }
            }
        }
        reader.close();
        return arr;
    }

    private boolean isEvent(String line) {
        return line.startsWith("[E]");
    }

    private boolean isDeadline(String line) {
        return line.startsWith("[D]");
    }

    private boolean isTodo(String line) {
        return line.startsWith("[T]");
    }

    /**
     * Checks if the Task has already been marked as done.
     *
     * @param line Task object in string format.
     * @return true if the Task object has already been marked as done, false otherwise.
     */
    public boolean checkDone(String line) {
        return line.charAt(4) == '\u2713'; //u2713 is a tick emoticon
    }
}
