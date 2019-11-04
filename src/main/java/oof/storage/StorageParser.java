package oof.storage;

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
import oof.model.task.Todo;
import oof.model.tracker.Tracker;

/**
 * Represents a class to parse save file data and object data.
 */
public class StorageParser {

    private static final String DELIMITER = "\\|\\|";
    private static final String DELIMITER_TRACKER = ",";
    private static final String DONE = "Y";
    private static final String SPACE = " ";
    private static final String TODO = "TODO";
    private static final String DEADLINE = "DEADLINE";
    private static final String EVENT = "EVENT";
    private static final String ASSIGNMENT = "ASSIGNMENT";
    private static final String SEMESTER = "SEMESTER";
    private static final String MODULE = "MODULE";
    private static final String LESSON = "LESSON";
    private static final String ASSESSMENT = "ASSESSMENT";
    private static final int INDEX_CLASS_TYPE = 0;
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
    private static final int INDEX_TASK_STATUS = 1;
    private static final int INDEX_TRACKER_MODULECODE = 0;
    private static final int INDEX_TRACKER_TASKINDEX = 1;
    private static final int INDEX_TRACKER_DESCRIPTION = 2;
    private static final int INDEX_TRACKER_STARTDATE = 3;
    private static final int INDEX_TRACKER_LASTUPDATED = 4;
    private static final int INDEX_TRACKER_TIMETAKEN = 5;

    /**
     * Converts data to ArrayList of Semester objects.
     *
     * @param data input supplied by storage file.
     * @return ArrayList containing list of Semesters.
     * @throws OofException if file is corrupted.
     */
    public static ArrayList<Semester> dataToSemester(ArrayList<String> data) throws OofException {
        ArrayList<Semester> semesters = new ArrayList<>();
        Semester semester = null;
        Module module = null;
        for (String datum : data) {
            String[] dataSplit = datum.split(DELIMITER);
            switch (dataSplit[INDEX_CLASS_TYPE]) {
            case SEMESTER:
                semester = addSemester(dataSplit);
                semesters.add(semester);
                break;
            case MODULE:
                if (semester != null) {
                    module = addModule(dataSplit);
                    semester.addModule(module);
                }
                break;
            case LESSON:
                if (module != null) {
                    Lesson lesson = addLesson(dataSplit);
                    module.addLesson(lesson);
                }
                break;
            default:
                throw new OofException("OOPS!! semester.txt is corrupted.");
            }
        }
        return semesters;
    }

    /**
     * Adds Semester from persistent storage to semesterList.
     *
     * @param lineSplit Semester object split in string array format.
     * @return Semester added to semesterList.
     */
    private static Semester addSemester(String[] lineSplit) {
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
    private static Module addModule(String[] lineSplit) {
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
    private static Lesson addLesson(String[] lineSplit) {
        String moduleCode = lineSplit[INDEX_LESSON_MODULE_CODE];
        String lessonName = lineSplit[INDEX_LESSON_NAME];
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(lineSplit[INDEX_LESSON_DAY]);
        String startTime = lineSplit[INDEX_LESSON_TIME_START];
        String endTime = lineSplit[INDEX_LESSON_TIME_END];
        return new Lesson(moduleCode, lessonName, dayOfWeek, startTime, endTime);
    }

    /**
     * Converts ArrayList of Semester objects into data strings.
     *
     * @param semesters ArrayList containing semester objects
     * @return ArrayList containing data strings.
     */
    public static ArrayList<String> semestersToData(ArrayList<Semester> semesters) {
        ArrayList<String> data = new ArrayList<>();
        for (Semester semester : semesters) {
            data.add(semester.toStorageString());
            writeModules(data, semester);
        }
        return data;
    }

    /**
     * Writes module data.
     *
     * @param data     ArrayList of Strings containing object data
     * @param semester Semester object containing Module data.
     */
    public static void writeModules(ArrayList<String> data, Semester semester) {
        for (Module module : semester.getModules()) {
            data.add(module.toStorageString());
            writeLessons(data, module);
        }
    }

    /**
     * Writes lesson data.
     *
     * @param data   ArrayList of Strings containing object data
     * @param module Module object containing Lesson data.
     */
    public static void writeLessons(ArrayList<String> data, Module module) {
        for (Lesson lesson : module.getLessons()) {
            data.add(lesson.toStorageString());
        }
    }

    /**
     * Converts data to ArrayList of Task objects.
     *
     * @param data         input supplied by storage file.
     * @param semesterList instance of SemesterList containing Semester data.
     * @return ArrayList containing list of Tasks.
     * @throws OofException if file is corrupted.
     */
    public static ArrayList<Task> dataToTask(ArrayList<String> data, SemesterList semesterList) throws OofException {
        ArrayList<Task> tasks = new ArrayList<>();
        for (String datum : data) {
            String[] dataSplit = datum.split(DELIMITER);
            switch (dataSplit[INDEX_CLASS_TYPE]) {
            case TODO:
                tasks.add(addToDo(dataSplit));
                break;
            case DEADLINE:
                tasks.add(addDeadline(dataSplit));
                break;
            case EVENT:
                tasks.add(addEvent(dataSplit));
                break;
            case ASSIGNMENT:
                tasks.add(addAssignment(dataSplit, semesterList));
                break;
            case ASSESSMENT:
                tasks.add(addAssessment(dataSplit, semesterList));
                break;
            default:
                throw new OofException("Output.txt is corrupted!");
            }
        }
        return tasks;
    }

    /**
     * Adds todo task from persistent storage to taskList.
     *
     * @param lineSplit Task object split in string array format.
     */
    private static Todo addToDo(String[] lineSplit) {
        String description = lineSplit[INDEX_TASK_NAME].trim();
        String date = lineSplit[INDEX_TASK_DATE_START].trim();
        Todo todo = new Todo(description, date);
        if (checkDone(lineSplit[INDEX_TASK_STATUS])) {
            todo.setStatus();
        }
        return todo;
    }

    /**
     * Adds deadline task from persistent storage to taskList.
     *
     * @param lineSplit Task object split in string array format.
     */
    private static Deadline addDeadline(String[] lineSplit) {
        String description = lineSplit[INDEX_TASK_NAME].trim();
        String date = lineSplit[INDEX_TASK_DATE_START].trim() + SPACE + lineSplit[INDEX_TASK_TIME_START].trim();
        Deadline deadline = new Deadline(description, date);
        if (checkDone(lineSplit[INDEX_TASK_STATUS])) {
            deadline.setStatus();
        }
        return deadline;
    }

    /**
     * Adds event task from persistent storage to taskList.
     *
     * @param lineSplit Task object split in string array format.
     */
    private static Event addEvent(String[] lineSplit) {
        String description = lineSplit[INDEX_TASK_NAME].trim();
        String startDate = lineSplit[INDEX_TASK_DATE_START].trim() + SPACE + lineSplit[INDEX_TASK_TIME_START];
        String endDate = lineSplit[INDEX_TASK_DATE_END].trim() + SPACE + lineSplit[INDEX_TASK_TIME_END];
        Event event = new Event(description, startDate, endDate);
        if (checkDone(lineSplit[INDEX_TASK_STATUS])) {
            event.setStatus();
        }
        return event;
    }

    /**
     * Adds Assignment task from persistent storage to taskList.
     *
     * @param lineSplit    Task object split in string array format.
     * @param semesterList Instance of SemesterList containing list of semesters.
     */
    private static Assignment addAssignment(String[] lineSplit, SemesterList semesterList) {
        String moduleCode = lineSplit[INDEX_ASSIGNMENT_MODULE_CODE];
        String description = lineSplit[INDEX_ASSIGNMENT_DESCRIPTION];
        String deadlineDate = lineSplit[INDEX_ASSIGNMENT_DEADLINE_DATE];
        String deadlineTime = lineSplit[INDEX_ASSIGNMENT_DEADLINE_TIME];
        String deadlineDateTime = deadlineDate + " " + deadlineTime;
        Assignment assignment = new Assignment(moduleCode, description, deadlineDateTime);
        if (checkDone(lineSplit[INDEX_TASK_STATUS])) {
            assignment.setStatus();
        }
        addToModule(semesterList, moduleCode, assignment);
        return assignment;
    }

    /**
     * Iterates through each module and adds assignment or assessment to correct module.
     *
     * @param semesterList Instance of SemesterList containing list of semesters.
     * @param moduleCode   String containing module code of assignment.
     * @param task         Assignment or Assessment Object to be added.
     */
    private static void addToModule(SemesterList semesterList, String moduleCode, Task task) {
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
     * @return
     */
    private static Assessment addAssessment(String[] lineSplit, SemesterList semesterList) {
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
        addToModule(semesterList, moduleCode, assessment);
        return assessment;
    }

    /**
     * Converts ArrayList of Task objects into string data.
     *
     * @param tasks ArrayList containing task objects
     * @return ArrayList of strings representing data strings.
     */
    public static ArrayList<String> tasksToData(ArrayList<Task> tasks) {
        ArrayList<String> data = new ArrayList<>();
        for (Task task : tasks) {
            data.add(task.toStorageString());
        }
        return data;

    }

    /**
     * Processes String of line obtained from tracker.txt.
     *
     * @param data ArrayList of data from tracker.txt.
     * @return ArrayList of Tracker objects.
     */
    public static ArrayList<Tracker> dataToTrackerList(ArrayList<String> data) throws OofException {
        ArrayList<Tracker> trackers = new ArrayList<>();
        for (String datum : data) {
            trackers.add(processLine(datum));
        }
        return trackers;
    }

    /**
     * Processes String of line obtained from tracker.txt.
     *
     * @param line String from tracker.txt.
     * @return Tracker object updated from data found in line.
     * @throws OofException if file is corrupted.
     */
    private static Tracker processLine(String line) throws OofException {
        SimpleDateFormat readFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date start;
        String[] processed = line.split(DELIMITER_TRACKER);
        String moduleCode = processed[INDEX_TRACKER_MODULECODE];
        int taskIndex = Integer.parseInt(processed[INDEX_TRACKER_TASKINDEX]);
        String description = processed[INDEX_TRACKER_DESCRIPTION];
        String startDate = processed[INDEX_TRACKER_STARTDATE];
        String lastUpdated = processed[INDEX_TRACKER_LASTUPDATED];
        long timeTaken = Long.parseLong(processed[INDEX_TRACKER_TIMETAKEN]);

        try {
            if (startDate.equals("null")) {
                start = null;
            } else {
                start = readFormat.parse(startDate);
            }
            Date updated = readFormat.parse(lastUpdated);
            return new Tracker(moduleCode, taskIndex, description, start, updated, timeTaken);
        } catch (ParseException e) {
            throw new OofException(("Unable to process stored Tracker data."));
        }
    }

    /**
     * Checks if the Task has already been marked as done.
     *
     * @param line Task object in string format.
     * @return true if the Task object has already been marked as done, false otherwise.
     */
    public static boolean checkDone(String line) {
        return line.equals(DONE);
    }

    /**
     * Converts TrackerList into ArrayList of Strings.
     *
     * @param trackerList Instance of trackerList that stores tracker objects
     * @return ArrayList containing data strings of tracker data.
     */
    public static ArrayList<String> trackerListToData(ArrayList<Tracker> trackerList) {
        ArrayList<String> data = new ArrayList<>();
        for (Tracker tracker : trackerList) {
            data.add(tracker.toStorageString());
        }
        return data;
    }
}
