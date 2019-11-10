package chronologer.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

import chronologer.ui.UiMessageHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The TaskList class handles all operations performed on the TaskList as well
 * as stores the TaskList.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class TaskList {

    private static final String ZERO_PADDING = "0";
    private static final String EMPTY = "";
    private static final String DEADLINE_TIMING_PREFIX = "D: ";
    private static final String START_TIME_PREFIX = "S: ";
    private static final String END_TIME_PREFIX = "E: ";
    private ArrayList<Task> listOfTasks;
    private ObservableList<Task> observableListOfTasks;

    public TaskList(ArrayList<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
        this.observableListOfTasks = FXCollections.observableArrayList(listOfTasks);
    }

    /**
     * This custom comparator allows the sorting of both deadlines and events.
     */
    private static final Comparator<Task> DateComparator = (firstDate, secondDate) -> {
        if (firstDate.startDate == null && secondDate != null) {
            return -1;
        } else if (firstDate.startDate != null && secondDate.startDate == null) {
            return 1;
        }
        assert firstDate.startDate != null;
        if (firstDate.startDate.isBefore(secondDate.startDate)) {
            return -1;
        } else if (firstDate.startDate.isEqual(secondDate.startDate)) {
            return 0;
        } else {
            return 1;
        }
    };

    /**
     * This custom comparator allows the sorting of both deadlines and events.
     */
    private static final Comparator<Task> PriorityComparator = (firstPriority, secondPriority) -> {
        if (firstPriority.priority.equals(Priority.HIGH) && secondPriority.priority.equals(Priority.MEDIUM)) {
            return -1;
        } else if (firstPriority.priority.equals(Priority.MEDIUM) && secondPriority.priority.equals(Priority.MEDIUM)) {
            return 1;
        } else {
            return 0;
        }
    };

    /**
     * This function allows the use to add a particular task.
     *
     * @param task contains the task that needs to be added.
     */
    public void add(Task task) {
        listOfTasks.add(task);
        observableListOfTasks.add(task);
    }

    /**
     * This function allows the use to delete a particular task.
     *
     * @param indexOfTask this is the index of the task which needs to be deleted.
     */
    public Task delete(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        listOfTasks.remove(task);
        observableListOfTasks.remove(task);
        return task;
    }

    /**
     * This function allows the user to find tasks with a particular keyword.
     *
     * @param keyWord this string contains the keyword the user is searching for.
     */
    public ArrayList<Task> find(String keyWord) {
        ArrayList<Task> holdFoundTasks = new ArrayList<>();
        for (int i = 0; i < listOfTasks.size(); i++) {
            String findMatch = listOfTasks.get(i).toString();
            if (findMatch.contains(keyWord)) {
                holdFoundTasks.add(listOfTasks.get(i));
            }
        }
        return holdFoundTasks;
    }

    /**
     * Performs a check as to determine if the task being added has a clash with
     * another task already scheduled.
     *
     * @param taskToCheck the task trying to be added by the user.
     * @return boolean true if there is a clash, false if there is not clash.
     */
    public boolean isClash(Task taskToCheck) {
        for (Task task : listOfTasks) {
            if (task.isClash(taskToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function allows the user to mark a particular task as done.
     *
     * @param indexOfTask this is the index of the task which needs to be marked as
     *                    done.
     */
    public Task markAsDone(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        task.setDone(true);
        observableListOfTasks.add(task);
        return task;
    }

    /**
     * Marks a task to be ignored and have reminders to stop showing up for the
     * task.
     *
     * @param indexOfTask The index of the task to be marked
     * @return The marked task
     */
    public Task markAsIgnorable(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        task.setIgnored(true);
        observableListOfTasks.add(task);
        return task;
    }

    /**
     * Marks a task to no longer be ignored and have reminders to show up again.
     *
     * @param indexOfTask The index of the task to be marked
     * @return The marked task
     */
    public Task markAsUnignorable(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        task.setIgnored(false);
        observableListOfTasks.add(task);
        return task;
    }

    /**
     * This function allows the user to add a location to tasks.
     *
     * @param taskWithLocation is of String type which contains the desired date of
     *                         schedule.
     * @return sortDateList the sorted schedule of all the tasks on a particular
     *         date.
     */
    public Task addLocation(Integer indexOfTask, String taskWithLocation) {
        Task taskHasLocation = listOfTasks.get(indexOfTask);
        taskHasLocation.setLocation("Location of the task is " + taskWithLocation);
        observableListOfTasks.add(taskHasLocation);
        return taskHasLocation;
    }

    /**
     * This function allows the user to obtain the tasks on a particular date.
     *
     * @param dayToFind is the desired date of schedule.
     * @return sortDateList the sorted schedule of all the tasks on a particular date.
     */
    public ArrayList<Task> schedule(String dayToFind) {
        assert dayToFind != null;
        LocalDate dateToFind = LocalDate.parse(dayToFind, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        ArrayList<Task> sortedDateList = new ArrayList<>();
        for (Task task : listOfTasks) {
            if (task.startDate == null && task.endDate == null) {
                continue;
            }
            assert task.startDate != null;
            if (isOnDate(dateToFind, task.startDate)) {
                sortedDateList.add(task);
                continue;
            }
            if (task.endDate == null) {
                continue;
            }
            if (isWithinDates(dateToFind, task.startDate, task.endDate)) {
                sortedDateList.add(task);
            }
        }
        sortedDateList.sort(DateComparator);
        return sortedDateList;
    }

    private boolean isOnDate(LocalDate dateToCheck, LocalDateTime startDate) {
        return (dateToCheck.isEqual(startDate.toLocalDate()));
    }

    private boolean isWithinDates(LocalDate dateToCheck, LocalDateTime startDate, LocalDateTime endDate) {
        return !(dateToCheck.isBefore(startDate.toLocalDate()) || dateToCheck.isAfter(endDate.toLocalDate()));
    }

    /**
     * This function allows the user to obtain the tasks on a particular date sorted
     * by priority.
     *
     * @param dayToFind is of String type which contains the desired date of
     *                  schedule.
     * @return priorityList the tasks of the given day sorted by priority
     */
    public ArrayList<String> obtainPriorityList(String dayToFind) {
        ArrayList<Task> priorityList = new ArrayList<>();
        ArrayList<String> stringPriorityList = new ArrayList<>();
        for (Task listOfTask : listOfTasks) {
            if (listOfTask.toString().contains(dayToFind) && !listOfTask.priority.equals(Priority.LOW)) {
                priorityList.add(listOfTask);
            }
        }
        priorityList.sort(PriorityComparator);
        for (int i = 0; i < priorityList.size(); i++) {
            stringPriorityList.add(priorityList.get(i).toString());
        }
        return stringPriorityList;
    }

    /**
     * Fetches all tasks without dates.
     *
     * @return tasksWithoutDates tasks with no time constraint.
     */
    public ArrayList<String> obtainTasksWithoutDates() {
        ArrayList<String> tasksWithoutDates = new ArrayList<>();
        for (int i = 0; i < listOfTasks.size(); i++) {
            if (listOfTasks.get(i).startDate == null) {
                tasksWithoutDates.add(listOfTasks.get(i).getDescription());
            }
        }
        return tasksWithoutDates;
    }

    //@@author fauzt-reused
    /**
     * Retrieves all Event tasks in the main task list in chronologically-ordered list.
     *
     * @param deadlineDate is the cut-off time to search all prior relevant events
     * @return all the events in the main task list in chronological order
     */
    public ArrayList<Event> obtainEventList(LocalDateTime deadlineDate) {
        ArrayList<Event> eventList = new ArrayList<>();
        for (Task task : listOfTasks) {
            if (isAnEventBeforeDeadline(task, deadlineDate)) {
                eventList.add((Event) task);
            }
        }
        Collections.sort(eventList);

        return eventList;
    }

    private boolean isAnEventBeforeDeadline(Task task, LocalDateTime deadlineDate) {
        return task.getClass() == Event.class && task.startDate.isBefore(deadlineDate);
    }

    /**
     * Fetches all reminders for the current date. (Tasks within the next 3 days)
     *
     * @return Holds reminders for the current date.
     */
    public ArrayList<String> fetchReminders() {
        ArrayList<String> reminders = new ArrayList<>();
        for (Task task : listOfTasks) {
            if (task.isReminderTrigger()) {
                    if (task.getModCode().equals("")) {
                        reminders.add(task.getDescription().trim() + "\n"
                            + getStartTime(task)
                            + getEndTime(task));
                    } else {
                        reminders.add(task.getModCode().trim() + " "
                            + task.getDescription().trim() + "\n"
                            + getStartTime(task)
                            + getEndTime(task));
                    }
                }
            }
        return reminders;
    }

    /**
     * Allows the GUI to obtain the tasks on a particular date, but
     * only with description.
     *
     * @param dayToFind is of String type which contains the desired date of
     *                  schedule.
     * @return sortDateList which contains only the descriptions of the tasks.
     */
    public ArrayList<String> scheduleForDay(String dayToFind) {
        ArrayList<Task> obtainDescriptions = schedule(dayToFind);
        ArrayList<String> scheduleDescriptionOnly = new ArrayList<>();
        for (Task task : obtainDescriptions) {
            if (task.getModCode().equals("")) {
                scheduleDescriptionOnly.add(task.getDescription().trim() + "\n"
                    + getStartTime(task)
                    + getEndTime(task));
            } else {
                scheduleDescriptionOnly.add(task.getModCode().trim() + " "
                    + task.getDescription().trim() + "\n"
                    + getStartTime(task)
                    + getEndTime(task));
            }
        }
        return scheduleDescriptionOnly;
    }

    private String getStartTime(Task task) {
        String time;

        assert task.startDate != null;
        int hour = task.startDate.getHour();
        int minute = task.startDate.getMinute();
        time = getFormattedTime(hour, minute);
        if (task.endDate == null) {
            time = DEADLINE_TIMING_PREFIX + time;
        } else {
            time = START_TIME_PREFIX + time + " ";
        }

        return time;
    }

    private String getEndTime(Task task) {
        if (task.endDate == null) {
            return EMPTY;
        }

        String time;

        int hour = task.endDate.getHour();
        int minute = task.endDate.getMinute();
        time = getFormattedTime(hour, minute);
        time = END_TIME_PREFIX + time;

        return time;
    }

    private String getFormattedTime(int hour, int minute) {
        String time;
        String formattedHour = Integer.toString(hour);
        String formattedMinute = Integer.toString(minute);
        if (formattedHour.length() == 1) {
            formattedHour = ZERO_PADDING + formattedHour;
        }
        if (formattedMinute.length() == 1) {
            formattedMinute = ZERO_PADDING + formattedMinute;
        }
        time = formattedHour + formattedMinute;
        return time;
    }

    //@@ author
    /**
     * This function allows the user to edit the task description.
     *
     * @param indexOfTask    Location of task in the list
     * @param newDescription The new task description to be updated
     * @return taskToBeEdited The task that had its description edited
     */
    public Task editTaskDescription(int indexOfTask, String newDescription) {
        Task taskToBeEdited = listOfTasks.get(indexOfTask);
        observableListOfTasks.remove(taskToBeEdited);
        taskToBeEdited.setDescription(newDescription);
        observableListOfTasks.add(taskToBeEdited);
        return taskToBeEdited;
    }

    /**
     * Function to allow user to edit/add comments to existing tasks.
     *
     * @param indexOfTask Index of task in list
     * @param comment     Holds comment to be added/edited
     * @return taskToBeEdited The task that has its comment edited/added
     */
    public Task editTaskComment(int indexOfTask, String comment) {
        Task taskToBeEdited = listOfTasks.get(indexOfTask);
        observableListOfTasks.remove(taskToBeEdited);
        taskToBeEdited.setComment(comment);
        observableListOfTasks.add(taskToBeEdited);
        return taskToBeEdited;
    }

    public void updateGui(Task task) {
        observableListOfTasks.add(task);
        observableListOfTasks.remove(task);
    }

    public void updateListOfTasks(ArrayList<Task> updatedListOfTasks) {
        listOfTasks.clear();
        listOfTasks = updatedListOfTasks;
    }

    private ObservableList<Integer> currentSetting = FXCollections.observableArrayList(-2,-1);
    private int prevTheme = 0;
    private int prevWeek = -1;

    /**
     * Allows the user to change theme - either dark mode or light mode.
     * @param choiceOfTheme Holds the theme that the user wants.
     */
    public void updateTheme(int choiceOfTheme) {
        if (choiceOfTheme != prevTheme && choiceOfTheme != -2) {
            currentSetting.remove(0);
            currentSetting.add(0, choiceOfTheme);
            prevTheme = choiceOfTheme;
            UiMessageHandler.outputMessage("Theme changed!");
        } else {
            UiMessageHandler.outputMessage("Theme cannot be changed!");
        }
    }

    /**
     * Allows the user to view different weeks, weeks 0 - 18.
     * @param choiceOfWeek Holds the week the user wants.
     */
    public void updateWeek(int choiceOfWeek) {
        if (choiceOfWeek != prevWeek && choiceOfWeek != -2) {
            currentSetting.remove(1);
            currentSetting.add(1, choiceOfWeek);
            prevWeek = choiceOfWeek;
            UiMessageHandler.outputMessage("Week being viewed has changed!");
        } else {
            UiMessageHandler.outputMessage("You are viewing the same week!");
        }
    }

    public ArrayList<Task> getTasks() {
        return listOfTasks;
    }

    public ObservableList<Task> getObservableListOfTasks() {
        return observableListOfTasks;
    }

    public int getSize() {
        return listOfTasks.size();
    }

    public ObservableList<Integer> getCurrentSetting() {
        return currentSetting;
    }
}