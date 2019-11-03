package oof;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;

import oof.exception.OofException;
import oof.model.module.Lesson;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.Assessment;
import oof.model.task.Assignment;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.task.Todo;
import oof.model.tracker.Tracker;

/**
 * Represents a Storage class to store and read Task objects to/from hard disk.
 */
public class Storage {
    private ArrayList<Task> tasks;
    private static final String DELIMITER_ESCAPED = "\\|\\|";
    private static final String DELIMITER_TRACKER = ",";
    private static final String DONE = "Y";
    private static final String SPACE = " ";
    private static final String PATH_OUTPUT = "./output.txt";
    private static final String PATH_MANUAL = "./manual.txt";
    private static final String PATH_THRESHOLD = "./oof.config";
    private static final String PATH_TRACKER = "./tracker.csv";
    private static final String PATH_SEMESTER = "./semester.txt";
    private static final String TODO = "TODO";
    private static final String DEADLINE = "DEADLINE";
    private static final String EVENT = "EVENT";
    private static final String ASSIGNMENT = "ASSIGNMENT";
    private static final String SEMESTER = "SEMESTER";
    private static final String MODULE = "MODULE";
    private static final String LESSON = "LESSON";
    private static final String ASSESSMENT = "ASSESSMENT";
    private static final int INDEX_STORAGE_TYPE = 0;
    private static final int INDEX_SEMESTER_YEAR = 1;
    private static final int INDEX_SEMESTER_NAME = 2;
    private static final int INDEX_SEMESTER_DATE_START = 3;
    private static final int INDEX_SEMESTER_DATE_END = 4;
    private static final int INDEX_MODULE_CODE = 1;
    private static final int INDEX_MODULE_NAME = 2;
    private static final int INDEX_LESSON_MODULE_CODE = 1;
    private static final int INDEX_LESSON_NAME = 2;
    private static final int INDEX_LESSON_DAY = 3;
    private static final int INDEX_LESSON_TIME_START = 4;
    private static final int INDEX_LESSON_TIME_END = 5;
    private static final int INDEX_ASSESSMENT_MODULE_CODE = 2;
    private static final int INDEX_ASSESSMENT_DESCRIPTION = 3;
    private static final int INDEX_ASSESSMENT_START_DATE = 4;
    private static final int INDEX_ASSESSMENT_START_TIME = 5;
    private static final int INDEX_ASSESSMENT_END_DATE = 6;
    private static final int INDEX_ASSESSMENT_END_TIME = 7;
    private static final int INDEX_ASSIGNMENT_MODULE_CODE = 2;
    private static final int INDEX_ASSIGNMENT_DESCRIPTION = 3;
    private static final int INDEX_ASSIGNMENT_DEADLINE_DATE = 4;
    private static final int INDEX_ASSIGNMENT_DEADLINE_TIME = 5;
    private static final int INDEX_TASK_NAME = 2;
    private static final int INDEX_TASK_DATE_START = 3;
    private static final int INDEX_TASK_TIME_START = 4;
    private static final int INDEX_TASK_DATE_END = 5;
    private static final int INDEX_TASK_TIME_END = 6;
    private static final int DEFAULT_THRESHOLD = 24;
    private static final int INDEX_TASK_STATUS = 1;
    private static final int INDEX_TRACKER_MODULECODE = 0;
    private static final int INDEX_TRACKER_TASKINDEX = 1;
    private static final int INDEX_TRACKER_DESCRIPTION = 2;
    private static final int INDEX_TRACKER_STARTDATE = 3;
    private static final int INDEX_TRACKER_LASTUPDATED = 4;
    private static final int INDEX_TRACKER_TIMETAKEN = 5;

    /**
     * Constructor for Storage class.
     */
    public Storage() {
        this.tasks = new ArrayList<>();
    }

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
            BufferedWriter out = new BufferedWriter(new FileWriter(PATH_OUTPUT));
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
     * @param semesterList Instance of SemesterList containing Semester objects.
     * @return TaskList containing Task objects.
     * @throws IOException  if file does not exist.
     * @throws OofException if file is corrupted.
     */
    public ArrayList<Task> readTaskList(SemesterList semesterList) throws IOException, OofException {
        BufferedReader reader = new BufferedReader(new FileReader(PATH_OUTPUT));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineSplit = line.split(DELIMITER_ESCAPED);
            switch (lineSplit[INDEX_STORAGE_TYPE]) {
            case TODO:
                addToDo(lineSplit);
                break;
            case DEADLINE:
                addDeadline(lineSplit);
                break;
            case EVENT:
                addEvent(lineSplit);
                break;
            case ASSIGNMENT:
                addAssignment(lineSplit, semesterList);
                break;
            case ASSESSMENT:
                addAssessment(lineSplit, semesterList);
                break;
            default:
                throw new OofException("Output.txt is corrupted!");
            }
        }
        reader.close();
        return tasks;
    }

    /**
     * Writes Tracker objects to hard disk.
     *
     * @param trackerList   TrackerList of Tracker objects.
     * @throws OofException if unable to write TrackerList.
     */
    public void writeTrackerList(ArrayList<Tracker> trackerList) throws OofException {
        try {
            String filename = PATH_TRACKER;
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < trackerList.size(); i++) {
                Tracker tracker = trackerList.get(i);
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
     * @return ArrayList containing Tracker objects.
     * @throws OofException if unable to read tracker.txt.
     */
    public ArrayList<Tracker> readTrackerList() throws OofException {
        try {
            File file = new File(PATH_TRACKER);
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            ArrayList<Tracker> trackerList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                Tracker tracker = processLine(line);
                trackerList.add(tracker);
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
        String moduleCode = processed[INDEX_TRACKER_MODULECODE];
        int taskIndex = Integer.parseInt(processed[INDEX_TRACKER_TASKINDEX]);
        String description = processed[INDEX_TRACKER_DESCRIPTION];
        String startDate = processed[INDEX_TRACKER_STARTDATE];
        String lastUpdated = processed[INDEX_TRACKER_LASTUPDATED];
        long timeTaken = Long.parseLong(processed[INDEX_TRACKER_TIMETAKEN]);

        if (startDate.equals("null")) {
            start = null;
        } else {
            start = readFormat.parse(startDate);
        }
        Date updated = readFormat.parse(lastUpdated);

        return new Tracker(moduleCode, taskIndex, description, start, updated, timeTaken);
    }

    /**
     * Writes Semester objects to hard disk.
     *
     * @param semesterList SemesterList that contains Semester objects.
     */
    public void writeSemesters(SemesterList semesterList) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH_SEMESTER));
            for (Semester semester : semesterList.getSemesterList()) {
                bufferedWriter.write(semester.toStorageString() + "\n");
                writeModules(semester, bufferedWriter);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e + ", thus please try inputting other things.");
        }
    }

    private void writeModules(Semester semester, BufferedWriter bufferedWriter) throws IOException {
        for (Module module : semester.getModules()) {
            bufferedWriter.write(module.toStorageString() + "\n");
            writeLessons(module, bufferedWriter);
        }

    }

    private void writeLessons(Module module, BufferedWriter bufferedWriter) throws IOException {
        for (Lesson lesson : module.getLessons()) {
            bufferedWriter.write(lesson.toStorageString() + "\n");
        }
    }

    /**
     * Reads Semester objects that were previously saved to hard disk.
     *
     * @return SemesterList containing Semester objects.
     * @throws IOException  if file does not exist.
     * @throws OofException if file is corrupted.
     */
    public ArrayList<Semester> readSemesterList() throws IOException, OofException {
        ArrayList<Semester> semesters = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_SEMESTER));
        String line;
        Semester semester = null;
        Module module = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] lineSplit = line.split(DELIMITER_ESCAPED);
            switch (lineSplit[INDEX_STORAGE_TYPE]) {
            case SEMESTER:
                semester = addSemester(lineSplit);
                semesters.add(semester);
                break;
            case MODULE:
                if (semester != null) {
                    module = addModule(lineSplit);
                    semester.addModule(module);
                }
                break;
            case LESSON:
                if (module != null) {
                    Lesson lesson = addLesson(lineSplit);
                    module.addLesson(lesson);
                }
                break;
            default:
                throw new OofException("OOPS!! semester.txt is corrupted.");
            }
        }
        bufferedReader.close();
        return semesters;
    }

    /**
     * Adds Semester from persistent storage to semesterList.
     *
     * @param lineSplit Semester object split in string array format.
     * @return Semester added to semesterList.
     */
    private Semester addSemester(String[] lineSplit) {
        String year = lineSplit[INDEX_SEMESTER_YEAR];
        String name = lineSplit[INDEX_SEMESTER_NAME];
        String startDate = lineSplit[INDEX_SEMESTER_DATE_START];
        String endDate = lineSplit[INDEX_SEMESTER_DATE_END];
        return new Semester(year, name, startDate, endDate);
    }

    /**
     * Adds Module from persistent storage to Semester.
     *
     * @param lineSplit Module object split in string array format.
     * @return Module added to Semester.
     */
    private Module addModule(String[] lineSplit) {
        String moduleCode = lineSplit[INDEX_MODULE_CODE];
        String moduleName = lineSplit[INDEX_MODULE_NAME];
        return new Module(moduleCode, moduleName);
    }

    /**
     * Adds Lesson from persistent storage to Module.
     *
     * @param lineSplit Lesson object split in string array format.
     * @return Lesson added to Module.
     */
    private Lesson addLesson(String[] lineSplit) {
        String moduleCode = lineSplit[INDEX_LESSON_MODULE_CODE];
        String lessonName = lineSplit[INDEX_LESSON_NAME];
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(lineSplit[INDEX_LESSON_DAY]);
        String startTime = lineSplit[INDEX_LESSON_TIME_START];
        String endTime = lineSplit[INDEX_LESSON_TIME_END];
        return new Lesson(moduleCode, lessonName, dayOfWeek, startTime, endTime);
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
     *
     * @param lineSplit Task object split in string array format.
     */
    private void addToDo(String[] lineSplit) {
        String description = lineSplit[INDEX_TASK_NAME].trim();
        String date = lineSplit[INDEX_TASK_DATE_START].trim();
        Todo todo = new Todo(description, date);
        if (checkDone(lineSplit[INDEX_TASK_STATUS])) {
            todo.setStatus();
        }
        tasks.add(todo);
    }

    /**
     * Adds deadline task from persistent storage to taskList.
     *
     * @param lineSplit Task object split in string array format.
     */
    private void addDeadline(String[] lineSplit) {
        String description = lineSplit[INDEX_TASK_NAME].trim();
        String date = lineSplit[INDEX_TASK_DATE_START].trim() + SPACE + lineSplit[INDEX_TASK_TIME_START].trim();
        Deadline deadline = new Deadline(description, date);
        if (checkDone(lineSplit[INDEX_TASK_STATUS])) {
            deadline.setStatus();
        }
        tasks.add(deadline);
    }

    /**
     * Adds event task from persistent storage to taskList.
     *
     * @param lineSplit Task object split in string array format.
     */
    private void addEvent(String[] lineSplit) {
        String description = lineSplit[INDEX_TASK_NAME].trim();
        String startDate = lineSplit[INDEX_TASK_DATE_START].trim() + SPACE + lineSplit[INDEX_TASK_TIME_START];
        String endDate = lineSplit[INDEX_TASK_DATE_END].trim() + SPACE + lineSplit[INDEX_TASK_TIME_END];
        Event event = new Event(description, startDate, endDate);
        if (checkDone(lineSplit[INDEX_TASK_STATUS])) {
            event.setStatus();
        }
        tasks.add(event);
    }

    /**
     * Adds Assignment task from persistent storage to taskList.
     *
     * @param lineSplit    Task object split in string array format.
     * @param semesterList Instance of SemesterList containing list of semesters.
     */
    private void addAssignment(String[] lineSplit, SemesterList semesterList) {
        String moduleCode = lineSplit[INDEX_ASSIGNMENT_MODULE_CODE];
        String description = lineSplit[INDEX_ASSIGNMENT_DESCRIPTION];
        String deadlineDate = lineSplit[INDEX_ASSIGNMENT_DEADLINE_DATE];
        String deadlineTime = lineSplit[INDEX_ASSIGNMENT_DEADLINE_TIME];
        String deadlineDateTime = deadlineDate + " " + deadlineTime;
        Assignment assignment = new Assignment(moduleCode, description, deadlineDateTime);
        if (checkDone(lineSplit[INDEX_TASK_STATUS])) {
            assignment.setStatus();
        }
        tasks.add(assignment);
        addToModule(semesterList, moduleCode, assignment);
    }

    /**
     * Iterates through each module and adds assignment or assessment to correct module.
     *
     * @param semesterList Instance of SemesterList containing list of semesters.
     * @param moduleCode   String containing module code of assignment.
     * @param task         Assignment or Assessment Object to be added.
     */
    private void addToModule(SemesterList semesterList, String moduleCode, Task task) {
        for (Semester semester : semesterList.getSemesterList()) {
            for (Module module : semester.getModules()) {
                if (task instanceof Assignment && module.getModuleCode().equals(moduleCode)) {
                    module.addAssignment((Assignment) task);
                } else if (task instanceof Assessment && module.getModuleCode().equals(moduleCode)) {
                    module.addAssessment((Assessment) task);
                }
            }
        }
    }

    /**
     * Adds Assessment from persistent storage to Module.
     *
     * @param lineSplit    Assessment object split in string array format.
     * @param semesterList Instance of SemesterList containing list of semesters.
     */
    private void addAssessment(String[] lineSplit, SemesterList semesterList) {
        String moduleCode = lineSplit[INDEX_ASSESSMENT_MODULE_CODE];
        String description = lineSplit[INDEX_ASSESSMENT_DESCRIPTION];
        String startDate = lineSplit[INDEX_ASSESSMENT_START_DATE];
        String startTime = lineSplit[INDEX_ASSESSMENT_START_TIME];
        String startDateTime = startDate + " " + startTime;
        String endDate = lineSplit[INDEX_ASSESSMENT_END_DATE];
        String endTime = lineSplit[INDEX_ASSESSMENT_END_TIME];
        String endDateTime = endDate + " " + endTime;
        Assessment assessment = new Assessment(moduleCode, description, startDateTime, endDateTime);
        if (checkDone(lineSplit[INDEX_TASK_STATUS])) {
            assessment.setStatus();
        }
        tasks.add(assessment);
        addToModule(semesterList, moduleCode, assessment);
    }
}
