package oof.storage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import oof.commons.exceptions.StorageFileCorruptedException;
import oof.model.semester.Semester;
import oof.model.semester.SemesterList;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.tracker.Tracker;

/**
 * Represents a Storage Manager to invoke StorageParser and Storage classes.
 */
public class StorageManager {

    private Storage storage;
    private static final String PATH_OUTPUT = "output.txt";
    private static final String PATH_MANUAL = "manual.txt";
    private static final String PATH_THRESHOLD = "oof.config";
    private static final String PATH_TRACKER = "tracker.csv";
    private static final String PATH_SEMESTER = "semester.txt";
    private static final int INDEX_THRESHOLD = 0;
    private static final int DEFAULT_THRESHOLD = 24;

    public StorageManager() {
        this.storage = new Storage();
    }

    /**
     * Reads list of Semesters from persistent storage.
     *
     * @return ArrayList containing list of Semester objects.
     * @throws NullPointerException if file does not exist.
     */
    public ArrayList<Semester> readSemesterList() throws NullPointerException, StorageFileCorruptedException {
        ArrayList<String> data = storage.loadFile(PATH_SEMESTER);
        return StorageParser.dataToSemester(data);
    }

    /**
     * Writes list of Semesters to persistent storage.
     *
     * @param semesterList Instance of SemesterList containing Semester data
     */
    public void writeSemesterList(SemesterList semesterList) {
        ArrayList<Semester> semesters = semesterList.getSemesterList();
        ArrayList<String> data = StorageParser.semestersToData(semesters);
        storage.writeFile(PATH_SEMESTER, data);
    }

    /**
     * Reads list of Task from persistent storage.
     *
     * @param semesterList Instance of SemesterList containing Semester data
     * @return ArrayList containing list of Task objects.
     * @throws NullPointerException if file does not exist.
     */
    public ArrayList<Task> readTaskList(SemesterList semesterList) throws NullPointerException,
            StorageFileCorruptedException {
        ArrayList<String> data = storage.loadFile(PATH_OUTPUT);
        return StorageParser.dataToTask(data, semesterList);
    }

    /**
     * Writes list of Task objects to persistent storage.
     *
     * @param taskList Instance of TaskList containing Task data
     */
    public void writeTaskList(TaskList taskList) {
        ArrayList<Task> tasks = taskList.getTaskList();
        ArrayList<String> data = StorageParser.tasksToData(tasks);
        storage.writeFile(PATH_OUTPUT, data);
    }

    /**
     * Reads threshold value from storage.
     *
     * @return value of threshold if file exists, else returns default threshold
     */
    public int readThreshold() {
        try {
            ArrayList<String> data = storage.loadFile(PATH_THRESHOLD);
            return Integer.parseInt(data.get(INDEX_THRESHOLD));
        } catch (NullPointerException | NumberFormatException e) {
            return DEFAULT_THRESHOLD;
        }
    }

    /**
     * Writes updated threshold to hard disk.
     *
     * @param updateThreshold The new threshold to be saved.
     */
    public void writeThreshold(int updateThreshold) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(PATH_THRESHOLD));
            out.write(String.valueOf(updateThreshold));
            out.close();
        } catch (IOException e) {
            System.out.println(e + ", thus please try inputting other things.");
        }
    }

    /**
     * Reads Tracker objects that were previously saved to hard disk.
     *
     * @return TrackerList that contains Tracker objects.
     */
    public ArrayList<Tracker> readTrackerList() throws NullPointerException, StorageFileCorruptedException {
        ArrayList<String> data = storage.loadFile(PATH_TRACKER);
        return StorageParser.dataToTrackerList(data);
    }

    /**
     * Writes Tracker objects to hard disk.
     *
     * @param trackerList TrackerList of Tracker objects.
     */
    public void writeTrackerList(ArrayList<Tracker> trackerList) {
        ArrayList<String> data = StorageParser.trackerListToData(trackerList);
        storage.writeFile(PATH_TRACKER, data);
    }

    public ArrayList<String> readManual() throws NullPointerException {
        return storage.loadFile(PATH_MANUAL);
    }
}
