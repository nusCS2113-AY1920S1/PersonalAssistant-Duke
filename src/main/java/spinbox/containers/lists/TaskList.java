package spinbox.containers.lists;

import spinbox.DateTime;
import spinbox.exceptions.CorruptedDataException;
import spinbox.datapersistors.storage.Storage;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.entities.items.tasks.Deadline;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Exam;
import spinbox.entities.items.tasks.Lab;
import spinbox.entities.items.tasks.Lecture;
import spinbox.entities.items.tasks.Schedulable;
import spinbox.entities.items.tasks.Task;
import spinbox.entities.items.tasks.Todo;
import spinbox.entities.items.tasks.Tutorial;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskList extends SpinBoxList<Task> {
    private static final Logger LOGGER = Logger.getLogger(SpinBoxList.class.getName());
    private static final String LOG_LOAD_DATA = "Load data from local storage.";
    private static final String LOG_SAVE_DATA = "Saved data into local storage.";
    private static final String LOG_SORT_LIST = "Sorted list.";
    private static final String LOG_VIEW_LIST = "View list.";
    private static final String LOG_CONTAINS_KEYWORD = "Found list of items that contain keyword.";
    private static final String LOG_CORRUPTED = "Corrupted task";
    private static final String TASK_LIST_FILE_NAME = "/tasks.txt";
    private static final String DELIMITER_FILTER = " \\| ";
    private static final String VIEW_TASK_LIST_HEADER = "Here are the tasks in your module:";
    private static final String TASKS_CONTAIN = "Here are the tasks that contain ";

    /**
     * Constructor for TaskList.
     * @param parentName The module code of the list.
     * @throws FileCreationException Error in creating the file to store data.
     */
    public TaskList(String parentName) throws FileCreationException {
        super(parentName);
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(true);
        localStorage = new Storage(DIRECTORY_NAME + this.getParentCode() + TASK_LIST_FILE_NAME);
    }

    /**
     * Comparator that sorts tasks by:
     * 1. Not yet done tasks before done tasks.
     * 2. Schedulable tasks before non-schedulable tasks.
     * 2a. For schedulable tasks: Earlier start date before later start date.
     * 2b. For non-schedulable tasks: Alphabetical order.
     */
    public static class TaskComparator implements Comparator<Task> {
        @Override
        public int compare(Task a, Task b) {
            DateTime startDateA = null;
            DateTime startDateB = null;

            if (!a.getDone() && b.getDone()) {
                return -1;
            } else if (a.getDone() && !b.getDone()) {
                return 1;
            }

            if (a.isSchedulable()) {
                startDateA = ((Schedulable)a).getStartDate();
            }
            if (b.isSchedulable()) {
                startDateB = ((Schedulable)b).getStartDate();
            }

            if (startDateA == null && startDateB == null) {
                return a.getName().compareToIgnoreCase(b.getName());
            } else if (startDateA == null) {
                return 1;
            } else if (startDateB == null) {
                return -1;
            } else {
                return startDateA.compareTo(startDateB);
            }
        }
    }

    /**
     * Method that will sort the list according to comparator.
     */
    public void sort() {
        LOGGER.entering(getClass().getName(), "sort");
        list.sort(new TaskComparator());
        LOGGER.fine(LOG_SORT_LIST);
        LOGGER.exiting(getClass().getName(), "sort");
    }

    /**
     * Populates data into the task list from the list's localStorage.
     * @throws DataReadWriteException loadData fail due to I/O Error..
     * @throws CorruptedDataException Thrown when a user manually edits the .txt file incorrectly.
     */
    @Override
    public void loadData() throws DataReadWriteException, CorruptedDataException {
        LOGGER.entering(getClass().getName(), "loadData");

        List<String> savedData = localStorage.loadData();

        for (String datum : savedData) {
            String[] arguments = datum.split(DELIMITER_FILTER);
            switch (arguments[0]) {
            case "T":
                Todo todo = new Todo();
                todo.fromStoredString(datum);
                this.addFromStorage(todo);
                break;
            case "D":
                Deadline deadline = new Deadline();
                deadline.fromStoredString(datum);
                this.addFromStorage(deadline);
                break;
            case "E":
                Event event = new Event();
                event.fromStoredString(datum);
                this.addFromStorage(event);
                break;
            case "EXAM":
                Exam exam = new Exam();
                exam.fromStoredString(datum);
                this.addFromStorage(exam);
                break;
            case "LAB":
                Lab lab = new Lab();
                lab.fromStoredString(datum);
                this.addFromStorage(lab);
                break;
            case "LEC":
                Lecture lecture = new Lecture();
                lecture.fromStoredString(datum);
                this.addFromStorage(lecture);
                break;
            case "TUT":
                Tutorial tutorial = new Tutorial();
                tutorial.fromStoredString(datum);
                this.addFromStorage(tutorial);
                break;
            default:
                LOGGER.severe(LOG_CORRUPTED);
                throw new CorruptedDataException();
            }
        }

        LOGGER.fine(LOG_LOAD_DATA);
        LOGGER.exiting(getClass().getName(), "loadData");
    }

    /**
     * Saves the current task list data into the list's localStorage.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     */
    @Override
    public void saveData() throws DataReadWriteException {
        LOGGER.entering(getClass().getName(), "saveData");
        List<String> dataToSave = new ArrayList<>();
        for (Task task: this.getList()) {
            dataToSave.add(task.storeString());
        }
        localStorage.writeData(dataToSave);
        LOGGER.fine(LOG_SAVE_DATA);
        LOGGER.exiting(getClass().getName(), "saveData");
    }

    /**
     * Gives the list of tasks in string format.
     * @return Returns list of tasks that have been converted to string format.
     */
    @Override
    public List<String> viewList() {
        LOGGER.entering(getClass().getName(), "viewList");
        LOGGER.fine(LOG_VIEW_LIST);
        LOGGER.exiting(getClass().getName(), "viewList");
        return outputNumberedListWithHeader(VIEW_TASK_LIST_HEADER, this.getList());
    }

    /**
     * Check the list for tasks with names that contain keyword.
     * @param keyword Keyword to be searched.
     * @return  A list of strings containing the the string form of tasks that contain keyword.
     */
    @Override
    public List<String> containsKeyword(String keyword) {
        LOGGER.entering(getClass().getName(), "containsKeyword");
        List<Task> contains = new ArrayList<>();
        for (Task task : this.getList()) {
            if (task.getName().toLowerCase().contains(keyword.toLowerCase())) {
                contains.add(task);
            }
        }

        contains.sort(new TaskComparator());

        LOGGER.fine(LOG_CONTAINS_KEYWORD);
        LOGGER.entering(getClass().getName(), "containsKeyword");
        return outputNumberedListWithHeader(TASKS_CONTAIN + keyword, contains);
    }
}
