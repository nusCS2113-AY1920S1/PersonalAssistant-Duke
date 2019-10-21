package oof;

import oof.model.module.Semester;
import oof.model.task.TaskList;
import oof.exception.OofException;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.Todo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a Storage class to store and read Task objects to/from hard disk.
 */
public class Storage {

    private ArrayList<Task> arr = new ArrayList<>();
    private static final String PATH_MANUAL = "src/main/manual.txt";
    private static final String PATH_THRESHOLD = "oof.config";
    private static final int INDEX_LABEL_END = 7;
    private static final int INDEX_DESCRIPTION = 0;
    private static final int INDEX_DATE = 1;
    private static final int INDEX_START = 0;
    private static final int INDEX_END = 1;
    private static final int DEFAULT_THRESHOLD = 24;

    /**
     * Reads and prints all commands available to user.
     *
     * @return commandList  ArrayList of available commands.
     * @throws OofException if manual cannot be retrieved from file path.
     */
    public ArrayList<String> readManual() throws OofException {
        try {
            File file = new File(PATH_MANUAL);
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            ArrayList<String> commandList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                commandList.add(line);
            }
            return commandList;
        } catch (IOException e) {
            throw new OofException("Manual Unavailable!");
        }
    }

    /**
     * Reads the threshold previously saved to hard disk.
     *
     * @return threshold    Integer of threshold.
     */
    public int readThreshold() {
        try {
            File file = new File(PATH_THRESHOLD);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            line = reader.readLine();
            int threshold;
            threshold = Integer.parseInt(line);
            return threshold;
        } catch (IOException e) {
            System.out.println(e + ", thus please try inputting other things.");
            return DEFAULT_THRESHOLD;
        }
    }

    /**
     * Writes updated threshold to hard disk.
     *
     * @param updateThreshold The new threshold to be saved.
     */
    public void writeThreshold(String updateThreshold) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(PATH_THRESHOLD));
            out.write(updateThreshold);
            out.close();
        } catch (IOException e) {
            System.out.println(e + ", thus please try inputting other things.");
        }
    }

    /**
     * Writes Task objects to hard disk.
     *
     * @param taskList TaskList that contains Task objects.
     */
    public void writeTaskList(TaskList taskList) {
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

    /**
     * Reads Task objects that were previously saved to hard disk.
     *
     * @return TaskList containing Task objects.
     * @throws IOException if file does not exist.
     */
    public ArrayList<Task> readTaskList() throws IOException {
        String filename = "output.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int counter = 0;
        while ((line = reader.readLine()) != null) {
            counter += 1;
            if (isTodo(line)) {
                String details = line.substring(INDEX_LABEL_END).trim();
                String[] lineSplit = details.split("\\(on: ");
                String description = lineSplit[INDEX_DESCRIPTION].trim();
                String on = lineSplit[INDEX_DATE].trim().substring(0, lineSplit[INDEX_DATE].length() - 1);
                Todo todo = new Todo(description, on);
                arr.add(todo);
                if (checkDone(line)) {
                    arr.get(counter - 1).setStatus();
                }
            } else if (isDeadline(line)) {
                String details = line.substring(INDEX_LABEL_END).trim();
                String[] lineSplit = details.split("\\(by: ");
                String description = lineSplit[INDEX_DESCRIPTION].trim();
                String date = lineSplit[INDEX_DATE].trim().substring(0, lineSplit[INDEX_DATE].length() - 1);
                Deadline deadline = new Deadline(description, date);
                arr.add(deadline);
                if (checkDone(line)) {
                    arr.get(counter - 1).setStatus();
                }
            } else if (isEvent(line)) {
                String details = line.substring(INDEX_LABEL_END).trim();
                String[] lineSplit = details.split(" \\(from: ");
                String description = lineSplit[INDEX_DESCRIPTION].trim();
                String[] dateSplit = lineSplit[INDEX_DATE].split("to: ");
                String startDate = dateSplit[INDEX_START].trim().substring(0, dateSplit[INDEX_START].length() - 1);
                String endDate = dateSplit[INDEX_END].trim().substring(0, dateSplit[INDEX_END].length() - 1);
                Event event = new Event(description, startDate, endDate);
                arr.add(event);
                if (checkDone(line)) {
                    arr.get(counter - 1).setStatus();
                }
            }
        }
        reader.close();
        return arr;
    }

    public ArrayList<Semester> readSemesterList() throws IOException {
        throw new IOException();
    }

    /**
     * Checks if entry in save file is an Event.
     *
     * @param line Entry in save file.
     * @return true if entry starts with [E].
     */
    private boolean isEvent(String line) {
        return line.startsWith("[E]");
    }

    /**
     * Checks if entry in save file is a Deadline.
     *
     * @param line Entry in save file.
     * @return true if entry starts with [D].
     */
    private boolean isDeadline(String line) {
        return line.startsWith("[D]");
    }

    /**
     * Checks if entry in save file is a Todo.
     *
     * @param line Entry in save file.
     * @return true if entry starts with [T].
     */
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
        return line.charAt(4) == 'Y';
    }

}
