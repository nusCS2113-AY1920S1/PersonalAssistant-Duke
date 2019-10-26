package oof;

import oof.model.module.Semester;
import oof.exception.OofException;
import oof.model.task.Assignment;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.TaskList;
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
    private static final String DELIMITER_ESCAPED = "\\|\\|";
    private static final String DONE = "Y";
    private static final String SPACE = " ";
    private static final String PATH_OUTPUT = "./output.txt";
    private static final String PATH_MANUAL = "./manual.txt";
    private static final String PATH_THRESHOLD = "./oof.config";
    private static final String TODO = "T";
    private static final String DEADLINE = "D";
    private static final String EVENT = "E";
    private static final String ASSIGNMENT = "A";
    private static final int INDEX_TYPE = 0;
    private static final int INDEX_MODULE_CODE = 2;
    private static final int INDEX_STATUS = 1;
    private static final int INDEX_DESCRIPTION = 2;
    private static final int INDEX_DESCRIPTION_ASSIGNMENT = 3;
    private static final int INDEX_DATE_START = 3;
    private static final int INDEX_DATE_START_ASSIGNMENT = 4;
    private static final int INDEX_DATE_END = 5;
    private static final int INDEX_TIME_START = 4;
    private static final int INDEX_TIME_START_ASSIGNMENT = 5;
    private static final int INDEX_TIME_END = 6;
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
            String filename = PATH_OUTPUT;
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            for (Task task : arr) {
                if (task instanceof Todo) {
                    out.write(task.toStorageString() + "\n");
                } else if (task instanceof Assignment) {
                    out.write(task.toStorageString() + "\n");
                } else if (task instanceof Deadline) {
                    out.write(task.toStorageString() + "\n");
                } else if (task instanceof Event) {
                    out.write(task.toStorageString() + "\n");
                }
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
     * @throws OofException if file is corrupted.
     */
    public ArrayList<Task> readTaskList() throws IOException, OofException {
        BufferedReader reader = new BufferedReader(new FileReader(PATH_OUTPUT));
        String line;
        int counter = 0;
        while ((line = reader.readLine()) != null) {
            counter += 1;
            String[] lineSplit = line.split(DELIMITER_ESCAPED);
            switch (lineSplit[INDEX_TYPE]) {
            case TODO:
                addToDo(lineSplit, counter);
                continue;
            case DEADLINE:
                addDeadline(lineSplit, counter);
                continue;
            case EVENT:
                addEvent(lineSplit, counter);
                continue;
            case ASSIGNMENT:
                addAssignment(lineSplit, counter);
                continue;
            default:
                throw new OofException("Output.txt is corrupted!");
            }
        }
        reader.close();
        return arr;
    }

    public ArrayList<Semester> readSemesterList() throws IOException {
        throw new IOException();
    }

    /**
     * Checks if the Task has already been marked as done.
     *
     * @param line Task object in string format.
     * @return true if the Task object has already been marked as done, false otherwise.
     */
    public boolean checkDone(String line) {
        return line.equals(DONE);
    }

    /**
     * Adds todo task from persistent storage to taskList.
     * @param lineSplit Task object split in string array format.
     * @param counter Current task number in 1 based indexing.
     */
    private void addToDo(String[] lineSplit, int counter) {
        String description = lineSplit[INDEX_DESCRIPTION].trim();
        String date = lineSplit[INDEX_DATE_START].trim();
        Todo todo = new Todo(description, date);
        arr.add(todo);
        if (checkDone(lineSplit[INDEX_STATUS])) {
            arr.get(counter - 1).setStatus();
        }
    }

    /**
     * Adds deadline task from persistent storage to taskList.
     * @param lineSplit Task object split in string array format.
     * @param counter Current task number in 1 based indexing.
     */
    private void addDeadline(String[] lineSplit, int counter) {
        String description = lineSplit[INDEX_DESCRIPTION].trim();
        String date = lineSplit[INDEX_DATE_START].trim() + SPACE + lineSplit[INDEX_TIME_START].trim();
        Deadline deadline = new Deadline(description, date);
        arr.add(deadline);
        if (checkDone(lineSplit[INDEX_STATUS])) {
            arr.get(counter - 1).setStatus();
        }
    }

    /**
     * Adds event task from persistent storage to taskList.
     * @param lineSplit Task object split in string array format.
     * @param counter Current task number in 1 based indexing.
     */
    private void addEvent(String[] lineSplit, int counter) {
        String description = lineSplit[INDEX_DESCRIPTION].trim();
        String startDate = lineSplit[INDEX_DATE_START].trim() + SPACE + lineSplit[INDEX_TIME_START];
        String endDate = lineSplit[INDEX_DATE_END].trim() + SPACE + lineSplit[INDEX_TIME_END];
        Event event = new Event(description, startDate, endDate);
        arr.add(event);
        if (checkDone(lineSplit[INDEX_STATUS])) {
            arr.get(counter - 1).setStatus();
        }
    }

    /**
     * Adds Assignment task from persistent storage to taskList.
     * @param lineSplit Task object split in string array format.
     * @param counter Current task number in 1 based indexing.
     */
    private void addAssignment(String[] lineSplit, int counter) {
        String moduleCode = lineSplit[INDEX_MODULE_CODE];
        String description = lineSplit[INDEX_DESCRIPTION_ASSIGNMENT];
        String startDate = lineSplit[INDEX_DATE_START_ASSIGNMENT];
        String startTime = lineSplit[INDEX_TIME_START_ASSIGNMENT];
        String startDateTime = startDate + " " + startTime;
        Assignment assignment = new Assignment(moduleCode, description, startDateTime);
        arr.add(assignment);
        if (checkDone(lineSplit[INDEX_STATUS])) {
            arr.get(counter - 1).setStatus();
        }
    }
}
