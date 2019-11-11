package seedu.hustler.task;

import seedu.hustler.Hustler;
import seedu.hustler.game.achievement.AddTask;
import seedu.hustler.game.achievement.DoneTask;
import seedu.hustler.logic.parser.DateTimeParser;
import seedu.hustler.ui.Ui;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Map;


import static seedu.hustler.logic.parser.DateTimeParser.getDateTime;

/**
 * A list of tasks that has a java ArrayList at its core.
 * Contains methods that add, remove and perform operations
 * on elements of the list like mark as done.
 */
public class TaskList {
    /**
     * ArrayList of Tasks.
     */
    private ArrayList<Task> list;

    /**
     * Ui instance that communicates errors with the user.
     */
    private Ui ui = new Ui();

    /**
     * Initializes list.
     */
    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    /**
     * Returns a task at a particular index.
     *
     * @param index index of the task you want
     * @return task at that index
     */
    public Task get(int index) {
        return list.get(index);
    }

    /**
     * Returns size of task list.
     *
     * @return length of task list.
     */
    public int size() {
        return list.size();
    }

    /**
     * Adds a new Task to the task list.
     *
     * @param task new Task to be added.
     */
    public void add(Task task) {
        list.add(task);
        updateBusybeeAchievement();
    }

    /**
     * Adds a task to the ArrayList based on the task type and task description.
     * Parses the description in case of event or deadline.
     *
     * @param taskType the type of task eg. todo, event, deadline
     * @param taskDescriptionFull the description that follows the task type.
     */
    public void add(String taskType, String taskDescriptionFull) {
        List<String> splitInput = Arrays.asList(taskDescriptionFull.split(" "));
        if (DetectAnomalies.test(taskType, splitInput, list)) {
            ui.showTaskClash();
            return;
        }

        String difficulty = "";
        String tag = "";
        if (splitInput.contains("/d")) {
            int difficultyIndex = splitInput.indexOf("/d") + 1;
            difficulty = splitInput.get(difficultyIndex);
        }
        if (splitInput.contains("/tag")) {
            int tagIndex = splitInput.indexOf("/tag") + 1;
            tag = splitInput.get(tagIndex);
        }

        if (taskType.equals("todo")) {
            addToDo(splitInput, difficulty, tag);
        } else if (taskType.equals("deadline")) {
            addDeadline(splitInput, difficulty, tag);
        } else if (taskType.equals("event")) {
            addEvent(splitInput, difficulty, tag);
        }
        updateBusybeeAchievement();
    }

    /**
     * Checks if the user have met Busybee achievement conditions and
     * update accordingly.
     */
    public void updateBusybeeAchievement() {
        AddTask.increment();
        AddTask.updateAchievementLevel();
        AddTask.updatePoints();
        Hustler.achievementList.updateBusyBee();
        ui.showTaskAdded(list);
    }

    /**
     * Checks if the user have met Completionist achievement conditions and
     * update accordingly.
     */
    public void updateCompletionistAchievement() {
        DoneTask.increment();
        DoneTask.updateAchievementLevel();
        DoneTask.updatePoints();
        Hustler.achievementList.updateCompletionist();
    }

    /**
     * Adds a Todo task to the task list.
     */
    public void addToDo(List<String> splitInput, String difficulty, String tag) {
        String onlyDescription = getDescription(splitInput);
        list.add(new ToDo(onlyDescription, difficulty, tag, LocalDateTime.now()));
    }

    /**
     * Adds a Deadline task to the task list.
     */
    public void addDeadline(List<String> splitInput, String difficulty, String tag) {
        String onlyDescription = getDescription(splitInput);
        LocalDateTime time = getDateTime(getTimeString(splitInput));

        if (splitInput.contains("/every")) {
            int everyIndex = splitInput.indexOf("/every");
            String frequency = splitInput.get(everyIndex + 1) + " " + splitInput.get(everyIndex + 2);
            int numOfMin = convertToMinute(frequency);
            list.add(new RecurringDeadline(onlyDescription, time, difficulty, tag,
                    LocalDateTime.now(), frequency, numOfMin, false));
        } else {
            list.add(new Deadline(onlyDescription, time, difficulty, tag, LocalDateTime.now()));
        }
    }

    /**
     * Adds an Event task to the task list.
     */
    public void addEvent(List<String> splitInput, String difficulty, String tag) {
        String onlyDescription = getDescription(splitInput);
        LocalDateTime time = getDateTime(getTimeString(splitInput));

        if (splitInput.contains("/every")) {
            int everyIndex = splitInput.indexOf("/every");
            String frequency = splitInput.get(everyIndex + 1) + " " + splitInput.get(everyIndex + 2);
            int numOfMin = convertToMinute(frequency);
            list.add(new RecurringEvent(onlyDescription, time, difficulty, tag,
                    LocalDateTime.now(), frequency, numOfMin, false));
        } else {
            list.add(new Event(onlyDescription, time, difficulty, tag, LocalDateTime.now()));
        }
    }

    /**
     * Returns the core ArrayList inside the TaskList object.
     *
     * @return ArrayList of Tasks.
     */
    public ArrayList<Task> returnList() {
        return list;
    }

    /**
     * Marks a task at index as done.
     *
     * @param i index of the task to mark as done.
     */
    public void doTask(int i) {
        list.get(i).markAsDone();
        updateCompletionistAchievement();
        ui.showTaskDone(list.get(i).toString());
    }

    /**
     * Removes task at index.
     *
     * @param i index at which task is removed.
     */
    public void removeTask(int i) {
        String taskDescription = list.get(i).toString();
        list.remove(i);
        ui.showTaskRemoved(list, taskDescription);
    }

    /**
     * Clears all the tasks in the task list.
     */
    public void clearList() {
        if (list.isEmpty()) {
            ui.showListEmpty();
        } else {
            list.clear();
            ui.showListCleared();
        }
    }

    /**
     * Clear all done tasks in the task list.
     */
    public void clearDone() {
        if (list.isEmpty()) {
            ui.showListEmpty();
        } else {
            list.removeIf(Task::isCompleted);
            ui.showCompletedCleared(list);
        }
    }

    /**
     * Snoozes task at index.
     *
     * @param i index at which task is snoozed.
     * @param userInput full description of the user's input.
     */
    public void snoozeTask(TaskList list, int i, String[] userInput) {
        if (userInput[1].contains("/")) {
            LocalDateTime localDateTime = getDateTime(userInput[1] + " " + userInput[2]);
            list.get(i).setDateTime(localDateTime);
        } else {
            int num = Integer.parseInt(userInput[1]);
            LocalDateTime ldt = list.get(i).getDateTime();

            switch (userInput[2]) {
            case "minutes":
                list.get(i).setDateTime(ldt.plusMinutes(num));
                break;
            case "hours":
                list.get(i).setDateTime(ldt.plusHours(num));
                break;
            case "days":
                list.get(i).setDateTime(ldt.plusDays(num));
                break;
            case "weeks":
                list.get(i).setDateTime(ldt.plusWeeks(num));
                break;
            case "months":
                list.get(i).setDateTime(ldt.plusMonths(num));
                break;
            default:
                break;
            }
        }
        ui.showTaskSnoozed(list.get(i).toString());
    }

    /**
     * Sorts the task list based on the user's preference.
     * There are 3 ways in which tasks can be sorted:
     * 1. /normal sorts the tasks based on when the user input the tasks.
     * 2. /chronological sorts the tasks based on the date and time of the tasks.
     * 3. /prioritize sorts the tasks based on the difficulty of the tasks.
     *
     * @param sortType the user's preferred sorting order.
     */
    public void sortTask(String sortType) {
        switch (sortType) {
        case "normal":
            TreeMap<LocalDateTime,Task> normalList = new TreeMap<>();
            for (Task task : list) {
                normalList.put(task.getInputDateTime(),task);
            }

            list.clear();
            for (Map.Entry<LocalDateTime,Task> entry : normalList.entrySet()) {
                list.add(entry.getValue());
            }
            break;
        case "datetime":
            TreeMap<LocalDateTime,ArrayList<Task>> toDoList = new TreeMap<>();
            TreeMap<LocalDateTime,ArrayList<Task>> otherTasksList = new TreeMap<>();

            for (Task task : list) {
                if (task instanceof ToDo) {
                    LocalDateTime inputDateTime = task.getInputDateTime();
                    ArrayList<Task> tasks = toDoList.get(inputDateTime);

                    // If list does not exist create it
                    if (tasks == null) {
                        tasks = new ArrayList<>();
                        tasks.add(task);
                        toDoList.put(inputDateTime,tasks);
                    } else {
                        tasks.add(task);
                    }
                } else {
                    LocalDateTime dateTime = task.getDateTime();
                    ArrayList<Task> tasks = otherTasksList.get(dateTime);

                    // If list does not exist create it
                    if (tasks == null) {
                        tasks = new ArrayList<>();
                        tasks.add(task);
                        otherTasksList.put(dateTime,tasks);
                    } else {
                        tasks.add(task);
                    }
                }
            }

            list.clear();
            for (Map.Entry<LocalDateTime, ArrayList<Task>> entry : toDoList.entrySet()) {
                list.addAll(entry.getValue());
            }
            for (Map.Entry<LocalDateTime, ArrayList<Task>> entry : otherTasksList.entrySet()) {
                list.addAll(entry.getValue());
            }
            break;
        case "priority":
            Collections.sort(list, (t1, t2) -> {
                if (t1.getDifficulty().toString().equals(t2.getDifficulty().toString())) {
                    return 0;
                } else if (t1.getDifficulty().toString().equals("[HIGH]")) {
                    return -1;
                } else if (t1.getDifficulty().toString().equals("[MED]")
                        && t2.getDifficulty().toString().equals("[LOW]")) {
                    return -1;
                } else {
                    return 1;
                }
            });
            break;
        default:
            break;
        }
        ui.showListSorted(list);
    }

    /**
     * Displays the list of tasks contained in the object.
     */
    public void displayList() {
        if (list.isEmpty()) {
            ui.showEmptyListError();
        } else {
            ui.showTaskList(list);
        }
    }

    /**
     * Finds task which contains a character sequence supplied.
     *
     * @param taskDescription a character sequence from which tasks will be found.
     */
    public ArrayList<Task> findTasks(String taskDescription) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : list) {
            if (task.getDescription().contains(taskDescription)
                || task.getTag().equalsIgnoreCase(taskDescription)) {
                matchingTasks.add(task);
            } else if (task.getDateTime() != null) {
                if (DateTimeParser.convertDateTimeToDate(task.getDateTime()).equals(taskDescription)
                    || DateTimeParser.getTimeOnly(task.getDateTime()).equals(taskDescription)) {
                    matchingTasks.add(task);
                }
            }
        }
        return matchingTasks;
    }

    /**
     * Converts the frequency into number of minutes.
     *
     * @param frequency string that denotes the frequency.
     * @return number of minutes.
     */
    public static int convertToMinute(String frequency) {
        int number = Integer.parseInt(frequency.split(" ")[0]);
        String period = frequency.split(" ")[1];
        int minutes;

        switch (period) {
        case "minutes":
            minutes = number;
            break;
        case "hours":
            minutes = number * 60;
            break;
        case "days":
            minutes = number * 60 * 24;
            break;
        case "weeks":
            minutes = number * 60 * 24 * 7;
            break;
        case "months":
            minutes = number * 60 * 24 * 7 * 28;
            break;
        default:
            minutes = 0;
            break;
        }
        return minutes;
    }

    /**
     * Obtains the last task in the task list.
     * @return last task in the task list.
     */
    public Task getLastTask() {
        return this.list.get(list.size() - 1);
    }

    /**
     * Returns only the task description.
     */
    public static String getDescription(List<String> splitInput) {
        String description = "";
        for (String str : splitInput) {
            if (str.equals("/d") || str.equals("/tag") || str.equals("/by")
                || str.equals("/at")) {
                break;
            }
            description += str + " ";
        }
        return description.trim();
    }

    /**
     * Returns only the string denoting the date and time of the Task.
     */
    public static String getTimeString(List<String> splitInput) {
        String time = "";
        for (int i = 0; i < splitInput.size(); i++) {
            if (splitInput.get(i).contains("/by") || (splitInput.get(i).contains("/at"))) {
                for (int j = i + 1; j < i + 3; j++) {
                    time += splitInput.get(j) + " ";
                }
                break;
            }
        }
        return time.trim();
    }

    /**
     * Checks whether two instances of TaskList are equal.
     *
     * @param temp TaskList instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(TaskList temp) {
        if (this.size() != temp.size()) {
            System.out.println("Length not equal");
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(temp.get(i))) {
                System.out.println(this.get(i).description + temp.get(i).description + "?");
                return false;
            }
        }
        return true;
    }
}
