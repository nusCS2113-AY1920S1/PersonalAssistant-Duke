package seedu.hustler.task;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

import seedu.hustler.data.CommandLog;
import seedu.hustler.Hustler;
import seedu.hustler.data.AvatarStorage;
import seedu.hustler.data.Schedule;
import seedu.hustler.game.achievement.AchievementList;
import seedu.hustler.game.achievement.AddTask;
import seedu.hustler.game.achievement.DoneTask;
import seedu.hustler.task.variables.Difficulty;
import seedu.hustler.ui.Ui;
import static seedu.hustler.game.achievement.AddTask.addAchievementLevel;
import static seedu.hustler.game.achievement.DoneTask.doneAchievementLevel;
import static seedu.hustler.parser.DateTimeParser.getDateTime;

/**
 * A list of tasks that has a java ArrayList at its core. Contains methods
 * that add, remove and perform operations on elements
 * of the list like mark as done.
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
     * Schedule instance to plan schedule.
     */
    private Schedule schedule = new Schedule();

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
     * @return length of tasklist.
     */
    public int size() {
        return list.size();
    }

    /**
     * Checks whether list is empty or not.
     *
     * @return true or false to whether the internal ArrayList is empty or not.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Adds a task to the ArrayList based on the task type and task description.
     * Parses the description in case of event or deadline.
     * Handles exceptions.
     *
     * @param taskType the type of task eg. todo, event, deadline
     * @param taskDescriptionFull the description that follows the task type.
     */
    public void add(String taskType, String taskDescriptionFull) {
        List<String> splitInput = Arrays.asList(taskDescriptionFull.split(" "));
        String difficulty = "";
        String tag = "";
        if (splitInput.contains("/d")) {
            int dIndex = splitInput.indexOf("/d") + 1;
            try {
                difficulty = splitInput.get(dIndex);
            } catch (ArrayIndexOutOfBoundsException e) {
                difficulty = "";
            }
        }
        if (splitInput.contains("/tag")) {
            int tIndex = splitInput.indexOf("/tag") + 1;
            try {
                tag = splitInput.get(tIndex);
            } catch (ArrayIndexOutOfBoundsException e) {
                tag = "";
            }
        }
        String onlyDescription = getDescription(splitInput);
        boolean checkAnomaly = true;
        if (taskType.equals("todo") && !DetectAnomalies.test(new ToDo(taskDescriptionFull), list)) {
                list.add(new ToDo(onlyDescription, difficulty, tag, LocalDateTime.now()));
            checkAnomaly = false;
        } else if (taskType.equals("deadline")) {
            try {
                String timeStr = getTime(splitInput);
                LocalDateTime by = getDateTime(timeStr);
                if (!DetectAnomalies.test(new Deadline(taskDescriptionFull, by), list)) {
                    list.add(new Deadline(onlyDescription, by, difficulty, tag, LocalDateTime.now()));
                    String taskDate = getOnlyDate(splitInput);
                    if (Schedule.isValidDate(taskDate)) {
                        schedule.addToSchedule(list.get(list.size() - 1), schedule.convertStringToDate(taskDate));
                    }
                    checkAnomaly = false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                ui.wrong_description_error();
                return;
            } catch (ParseException ignore) {
                return;
            }
        } else if (taskType.equals("event")) {
            try {
                String timeStr = getTime(splitInput);
                LocalDateTime at = getDateTime(timeStr);
                if (!DetectAnomalies.test(new Event(taskDescriptionFull, at), list)) {
                    list.add(new Event(onlyDescription, at, difficulty, tag, LocalDateTime.now()));
                    String taskDate = getOnlyDate(splitInput);
                    if (Schedule.isValidDate(taskDate)) {
                        schedule.addToSchedule(list.get(list.size() - 1), schedule.convertStringToDate(taskDate));
                    }
                    checkAnomaly = false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                ui.wrong_description_error();
                return;
            } catch (ParseException ignore) {
                return;
            }
        }
        if (!CommandLog.isRestoring()) {
            if (!checkAnomaly) {
                AddTask.increment();
                AddTask.updateAchievementLevel();
                AddTask.updatePoints();
                AchievementList.updateAddTask(addAchievementLevel);
                String output = "\t  " + list.get(list.size() - 1).toString();
                System.out.println("\t_____________________________________");
                System.out.println("\tGot it. I've added this task:");
                System.out.println(output);
                System.out.println("\tNow you have " + list.size() + " tasks in the list.");
                System.out.println("\t_____________________________________\n\n");
            } else {
                System.out.println("Task clashes with another existing task in the list!");
            }
        }
    }

    /**
     * Returns the core ArrayList inside the TaskList object.
     *
     * @return ArrayList of Tasks.
     */
    public ArrayList<Task> return_list() {
        return list;
    }

    /**
     * Marks a task at index as done.
     *
     * @param i index of the task to mark as done.
     * @throws IndexOutOfBoundsException if an out of bounds index is requested.
     */
    public void doTask(int i) {
        try {
            list.get(i).markAsDone();
            if (list.get(i).isDone) {
                if (!CommandLog.isRestoring()) {
                    DoneTask.increment();
                    DoneTask.updateAchievementLevel();
                    DoneTask.updatePoints();
                    AchievementList.updateDoneTask(doneAchievementLevel);
                    System.out.println("\t_____________________________________");
                    System.out.println("\tNice! I've marked this task as done:");
                    System.out.println("\t  " + (i + 1) + ". " + list.get(i).toString());
                    System.out.println("\t_____________________________________\n\n");
                }
                Hustler.avatar.gainXp();
                AvatarStorage.save(Hustler.avatar);
            }
        } catch (IndexOutOfBoundsException e) {
            ui.task_doesnt_exist_error();
        } catch (IOException ignore) {
            return;
        }
    }

    /**
     * Removes task at index.
     *
     * @param i index at which task is removed.
     * @throws IndexOutOfBoundsException if an out of bounds index is requested.
     */
    public void removeTask(int i) {
        try {
            final Task lastTask = list.get(i);
            list.remove(i);
            System.out.println("\t_____________________________________");
            System.out.println("\tNoted. I have removed this task:");
            System.out.println("\t  " + (i + 1) + ". " + lastTask.toString());
            System.out.println("\tNow there are " + list.size() + " tasks left.");
            System.out.println("\t_____________________________________\n\n");
        } catch (IndexOutOfBoundsException e) {
            ui.task_doesnt_exist_error();
        }
    }

    /**
     * Snoozes task at index.
     *
     * @param i index at which task is snoozed.
     * @param userInput full description of the user's input.
     * @throws IndexOutOfBoundsException if an out of bounds index is requested.
     */
    public void snoozeTask(int i, String[] userInput) {
        try {
            if (userInput[2].contains("/")) {
                LocalDateTime localDateTime = getDateTime(userInput[2] + " " + userInput[3]);
                list.get(i).setDateTime(localDateTime);
            } else {
                int num = Integer.parseInt(userInput[2]);
                LocalDateTime ldt = list.get(i).getDateTime();

                switch (userInput[3]) {
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
                    System.out.println("You have typed in the wrong format. Please re-enter the snooze command.");
                    return;
                }
            }
            System.out.println("\t_____________________________________");
            System.out.println("\tGot it. You have snoozed the task.");
            System.out.println("\t" + list.get(i).toString());
            System.out.println("\t_____________________________________");
        } catch (IndexOutOfBoundsException e) {
            ui.task_doesnt_exist_error();
        }
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
        case "chronological":
            TreeMap<LocalDateTime,Task> toDoList = new TreeMap<>();
            TreeMap<LocalDateTime,Task> otherTasksList = new TreeMap<>();

            for (Task task : list) {
                if (task instanceof ToDo) {
                    toDoList.put(task.getInputDateTime(),task);
                } else {
                    otherTasksList.put(task.getDateTime(),task);
                }
            }

            list.clear();
            for (Map.Entry<LocalDateTime,Task> entry : toDoList.entrySet()) {
                list.add(entry.getValue());
            }
            for (Map.Entry<LocalDateTime,Task> entry : otherTasksList.entrySet()) {
                list.add(entry.getValue());
            }
            break;
        case "prioritize":
            Collections.sort(list, (t1, t2) -> {
                if (t1.getDifficulty().toString().equals(t2.getDifficulty().toString())) {
                    return 0;
                } else if (t1.getDifficulty().toString().equals("[H]")) {
                    return -1;
                } else if (t1.getDifficulty().toString().equals("[M]") &&
                        t2.getDifficulty().toString().equals("[L]")) {
                    return -1;
                } else {
                    return 1;
                }
            });
            break;
        }
        System.out.println("\t_____________________________________");
        System.out.println("\tTask list has been successfully sorted!");
        displayList();
    }

    /**
     * Displays the list of tasks contained in the object.
     */
    public void displayList() {
        if (list.isEmpty()) {
            ui.show_empty_list_error();
            return;
        }

        System.out.println("\t_____________________________________");
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + list.get(i).toString());
        }
        System.out.println("\t_____________________________________\n\n");
    }

    /**
     * Finds task which contains a character sequence supplied.
     *
     * @param taskDescription a character sequence from which tasks will be found.
     */
    public void findTask(String taskDescription) {
        ArrayList<Integer> matchingTasks = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDescription().contains(taskDescription)
                || list.get(i).getTag().equalsIgnoreCase(taskDescription)) {
                matchingTasks.add(i);
            }
        }
        if (matchingTasks.isEmpty()) {
            ui.task_doesnt_exist_error();
            return;
        }
        System.out.println("\t_____________________________________");
        System.out.println("\tFound " + matchingTasks.size() + ". Here you go.");
        for (Integer id : matchingTasks) {
            System.out.println("\t  " + (id + 1) + ". " + list.get(id).toString());
        }
        System.out.println("\t_____________________________________\n\n");
    }

    public Task getLastTask() {
        return this.list.get(list.size() - 1);
    }

    private String getDescription(List<String> splitInput) {
        String description = "";
        for (String str : splitInput) {
            if (str.equals("/d") || str.equals("-tag") || str.equals("/by")
                || str.equals("/at")) {
                break;
            }
            description += str + " ";
        }
        return description.trim();
    }

    private String getTime(List<String> splitInput) {
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

    private String getOnlyDate(List<String> splitInput) {
        String date = "";
        for (int i = 0; i < splitInput.size(); i++) {
            if (splitInput.get(i).contains("/by") || (splitInput.get(i).contains("/at"))) {
                date += splitInput.get(i + 1);
                break;
            }
        }
        return date.trim();
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
