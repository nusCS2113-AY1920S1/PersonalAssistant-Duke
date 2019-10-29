package oof;

import oof.model.module.Semester;
import oof.exception.OofException;
import oof.model.module.Assessment;
import oof.model.module.Lesson;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.Assignment;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.task.Todo;
import oof.model.tracker.Tracker;
import oof.model.tracker.TrackerList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a Storage class to store and read Task objects to/from hard disk.
 */
public class Storage {

    private ArrayList<Task> arr = new ArrayList<>();
    private ArrayList<Semester> semesterList = new ArrayList<>();
    private ArrayList<Module> modules = new ArrayList<>();
    private ArrayList<Assessment> assessments = new ArrayList<>();
    private ArrayList<Lesson> lessons = new ArrayList<>();
    private static final String DELIMITER_ESCAPED = "\\|\\|";
    private static final String DELIMITER_TRACKER = ",";
    private static final String DONE = "Y";
    private static final String SPACE = " ";
    private static final String PATH_OUTPUT = "./output.txt";
    private static final String PATH_MANUAL = "./manual.txt";
    private static final String PATH_THRESHOLD = "./oof.config";
    private static final String PATH_TRACKER = "./tracker.csv";
    private static final String PATH_SEMESTER = "./semester.txt";
    private static final String TODO = "T";
    private static final String DEADLINE = "D";
    private static final String EVENT = "E";
    private static final String ASSIGNMENT = "A";
    private static final String SEMESTER = "S";
    private static final String MODULE = "M";
    private static final String LESSON = "L";
    private static final String ASSESSMENT = "A";
    private static final int INDEX_TYPE = 0;
    private static final int INDEX_YEAR = 1;
    private static final int INDEX_CODE = 1;
    private static final int INDEX_NAME = 1;
    private static final int INDEX_MODULE_CODE = 2;
    private static final int INDEX_STATUS = 1;
    private static final int INDEX_DESCRIPTION = 2;
    private static final int INDEX_DESCRIPTION_ASSIGNMENT = 3;
    private static final int INDEX_DATE_START = 3;
    private static final int INDEX_DATE_START_ASSIGNMENT = 4;
    private static final int INDEX_DATE_START_LESSON = 2;
    private static final int INDEX_DATE_END_LESSON = 3;
    private static final int INDEX_DATE_START_ASSESSMENT = 3;
    private static final int INDEX_DATE_END_ASSESSMENT = 4;
    private static final int INDEX_DATE_END = 5;
    private static final int INDEX_TIME_START = 4;
    private static final int INDEX_TIME_START_ASSIGNMENT = 5;
    private static final int INDEX_TIME_END = 6;
    private static final int DEFAULT_THRESHOLD = 24;
    private static final int ARGUMENT_FIRST = 0;
    private static final int ARGUMENT_SECOND = 1;
    private static final int ARGUMENT_THIRD = 2;
    private static final int ARGUMENT_FOURTH = 3;
    private static final int ARGUMENT_FIFTH = 4;

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
            File file = new File(PATH_THRESHOLD);
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
                out.write(task.toStorageString() + "\n");
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

    /**
     * Writes Tracker objects to hard disk.
     *
     * @param trackerList   TrackerList of Tracker objects.
     * @throws OofException if unable to write TrackerList.
     */
    public void writeTrackerList(TrackerList trackerList) throws OofException {
        try {
            List<Tracker> arr = trackerList.getTrackers();
            String filename = PATH_TRACKER;
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < trackerList.getSize(); i++) {
                Tracker tracker = trackerList.getTracker(i);
                out.write(tracker.toStorageString() + "\n");
            }
            out.close();
        } catch (IOException e) {
            throw new OofException("Unable to save Tracker data.");
        }
    }

    /**
     * Reads Tracker objects that were previously saved to hard disk.
     *
     * @return TrackerList that contains Tracker objects.
     * @throws OofException if unable to read tracker.txt.
     */
    public TrackerList readTrackerList() throws OofException {
        try {
            File file = new File(PATH_TRACKER);
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            TrackerList trackerList = new TrackerList();
            String line;
            while ((line = br.readLine()) != null) {
                Tracker tracker = processLine(line);
                trackerList.addTracker(tracker);
            }
            return trackerList;
        } catch (IOException | ParseException e) {
            throw new OofException("Unable to process stored Tracker data.");
        }
    }

    /**
     * Processes String of line obtained from tracker.txt.
     *
     * @param line  String from tracker.txt.
     * @return      Tracker object updated from data found in line.
     */
    private Tracker processLine(String line) throws ParseException {
        SimpleDateFormat readFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date start;

        String[] processed = line.split(DELIMITER_TRACKER);
        String moduleCode = processed[ARGUMENT_FIRST];
        String description = processed[ARGUMENT_SECOND];
        String startDate = processed[ARGUMENT_THIRD];
        String lastUpdated = processed[ARGUMENT_FOURTH];
        long timeTaken = Long.parseLong(processed[ARGUMENT_FIFTH]);

        if (startDate.equals("null")) {
            start = null;
        } else {
            start = readFormat.parse(startDate);
        }
        Date updated = readFormat.parse(lastUpdated);

        return new Tracker(moduleCode, description, start, updated, timeTaken);
    }

    /**
     * Writes Semester objects to hard disk.
     *
     * @param semesterList SemesterList that contains Semester objects.
     */
    public void writeSemesterList(SemesterList semesterList, Semester semester, Module module) {
        try {
            ArrayList<Semester> semesters = semesterList.getSemesterList();
            BufferedWriter out = new BufferedWriter(new FileWriter(PATH_SEMESTER));
            for (Semester sem : semesters) {
                out.write(sem.toStorageString() + "\n");
            }
            ArrayList<Module> modules = semester.getModules();
            for (Module mod : modules) {
                out.write(mod.toStorageString() + "\n");
            }
            ArrayList<Lesson> lessons = module.getLessons();
            for (Lesson lesson : lessons) {
                out.write(lesson.toStorageString() + "\n");
            }
            ArrayList<Assessment> assessments = module.getAssessments();
            for (Assessment assessment : assessments) {
                out.write(assessment.toStorageString() + "\n");
            }
            out.close();
        } catch (IOException e) {
            System.out.println(e + ", thus please try inputting other things.");
        }
    }

    /**
     * Reads Semester objects that were previously saved to hard disk.
     *
     * @return SemesterList containing Semester objects.
     * @throws IOException if file does not exist.
     * @throws OofException if file is corrupted.
     */
    public ArrayList<Semester> readSemesterList() throws IOException, OofException {
        BufferedReader reader = new BufferedReader(new FileReader(PATH_SEMESTER));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineSplit = line.split(DELIMITER_ESCAPED);
            if (lineSplit[INDEX_TYPE].equals(SEMESTER)) {
                addSemester(lineSplit);
            }
        }
        reader.close();
        return semesterList;
    }

    /**
     * Reads Module objects that were previously saved to hard disk.
     *
     * @return Modules containing module objects.
     * @throws IOException if file does not exist.
     * @throws OofException if file is corrupted.
     */
    public ArrayList<Module> readModuleList() throws IOException, OofException {
        BufferedReader reader = new BufferedReader(new FileReader(PATH_SEMESTER));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineSplit = line.split(DELIMITER_ESCAPED);
            if (lineSplit[INDEX_TYPE].equals(MODULE)) {
                addModule(lineSplit);
            }
        }
        reader.close();
        return modules;
    }

    /**
     * Reads Lesson objects that were previously saved to hard disk.
     *
     * @return Lessons containing lesson objects.
     * @throws IOException if file does not exist.
     * @throws OofException if file is corrupted.
     */
    public ArrayList<Lesson> readLessonList() throws IOException, OofException {
        BufferedReader reader = new BufferedReader(new FileReader(PATH_SEMESTER));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineSplit = line.split(DELIMITER_ESCAPED);
            if (lineSplit[INDEX_TYPE].equals(LESSON)) {
                addLesson(lineSplit);
            }
        }
        reader.close();
        return lessons;
    }

    /**
     * Reads Assessment objects that were previously saved to hard disk.
     *
     * @return Assessments containing assessment objects.
     * @throws IOException if file does not exist.
     * @throws OofException if file is corrupted.
     */
    public ArrayList<Assessment> readAssessmentList() throws IOException, OofException {
        BufferedReader reader = new BufferedReader(new FileReader(PATH_SEMESTER));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineSplit = line.split(DELIMITER_ESCAPED);
            if (lineSplit[INDEX_TYPE].equals(ASSESSMENT)) {
                addAssessment(lineSplit);
            }
        }
        reader.close();
        return assessments;
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

    /**
     * Adds Semester from persistent storage to semesterList.
     * @param lineSplit Semester object split in string array format.
     */
    private void addSemester(String[] lineSplit) {
        String academicYear = lineSplit[INDEX_YEAR];
        String semesterName = lineSplit[INDEX_DESCRIPTION];
        Semester semester = new Semester(academicYear, semesterName);
        semesterList.add(semester);
    }

    /**
     * Adds Module from persistent storage to Semester.
     * @param lineSplit Module object split in string array format.
     */
    private void addModule(String[] lineSplit) {
        String moduleCode = lineSplit[INDEX_CODE];
        String moduleName = lineSplit[INDEX_DESCRIPTION];
        Module module = new Module(moduleCode, moduleName);
        modules.add(module);
    }

    /**
     * Adds Lesson from persistent storage to Module.
     * @param lineSplit Lesson object split in string array format.
     */
    private void addLesson(String[] lineSplit) {
        String lessonName = lineSplit[INDEX_NAME];
        String startTime = lineSplit[INDEX_DATE_START_LESSON];
        String endTime = lineSplit[INDEX_DATE_END_LESSON];
        Lesson lesson = new Lesson(lessonName, startTime, endTime);
        lessons.add(lesson);
    }

    /**
     * Adds Assessment from persistent storage to Module.
     * @param lineSplit Assessment object split in string array format.
     */
    private void addAssessment(String[] lineSplit) {
        String moduleCode = lineSplit[INDEX_CODE];
        String name = lineSplit[INDEX_DESCRIPTION];
        String startTime = lineSplit[INDEX_DATE_START_ASSESSMENT];
        String endTime = lineSplit[INDEX_DATE_END_ASSESSMENT];
        Assessment assessment = new Assessment(moduleCode, name, startTime, endTime);
        assessments.add(assessment);
    }
}
