package seedu.duke.task;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;


import seedu.duke.data.Schedule;
import java.util.Scanner;

import seedu.duke.ui.Ui;

import static seedu.duke.parser.DateTimeParser.getDateTime;

/**
 * A list of tasks that has a java ArrayList at its core. Contains methods
 * that add, remove and perform operations on elements
 * of the list like mark as done.
 */
public class TaskList {
    /**
     * ArrayList of Tasks.
     */
    private ArrayList<Task> list = new  ArrayList<Task>();

    /**
     * Ui instance that communicates errors with the user.
     */
    private Ui ui = new Ui();
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
        boolean checkAnomaly = true;
        // if tasktype is not ToDo
        if (taskType.equals("todo") && !DetectAnomalies.test(new ToDo(taskDescriptionFull), list)) {
            list.add(new ToDo(taskDescriptionFull));
            checkAnomaly = false;
        } else {
            // Extract task time and task description and initialize as deadline
            if (taskType.equals("deadline")) {
                try {
                    String taskDescription = taskDescriptionFull.split("/", 2)[0];
                    String taskTime = taskDescriptionFull.split("/", 2)[1].substring(3);
                    String taskDateOnly = taskTime.split(" ", 2)[0];
                    LocalDateTime localDateTime = getDateTime(taskTime);
                    if (!DetectAnomalies.test(new Deadline(taskDescriptionFull,localDateTime), list)) {
                        list.add(new Deadline(taskDescription,localDateTime));
                        checkAnomaly = false;
                        if (Schedule.isValidDate(taskDateOnly)) {
                            schedule.addToSchedule(list.get(list.size() - 1), schedule.convertStringToDate(taskDateOnly));
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    ui.wrong_description_error();
                    return;
                } catch (ParseException ignore) {
                    return;
                }
            } else if (taskType.equals("doafter")) {
                try {
                    String taskDescription = taskDescriptionFull.split("/", 2)[0];
                    String indexString = taskDescriptionFull.split("/", 2)[1].substring(6);
                    int indexInt = Integer.parseInt(indexString) - 1;
                    String after = list.get(indexInt).description;
                    boolean taskFound = false;

                    for (Task j: list) {
                        if (j.description.equals(after)) {
                            if (!DetectAnomalies.test(new DoAfter(taskDescription, after),list)) {
                                checkAnomaly = false;
                                list.add(new DoAfter(taskDescription, after));
                                taskFound = true;
                                break;
                            }
                        }
                    }
                    if (!taskFound) {
                        System.out.println();
                        System.out.println("        _____________________________________");
                        System.out.println("        Task: '" + after + "' not found!");
                        System.out.println("        _____________________________________");
                        System.out.println();
                        System.out.println();
                        return;
                    }
                } catch (ArrayIndexOutOfBoundsException e) { // if /after is not included in doafter command
                    ui.wrong_description_error();
                    return;
                }
            } else if (taskType.equals("event")) {
                try {
                    String taskDescription = taskDescriptionFull.split("/", 2)[0];
                    String taskTime = taskDescriptionFull.split("/", 2)[1].substring(3);
                    String taskDateOnly = taskTime.split(" ", 2)[0];
                    LocalDateTime localDateTime = getDateTime(taskTime);
                    if (!DetectAnomalies.test(new Event(taskDescriptionFull,localDateTime), list)) {
                        list.add(new Event(taskDescription, localDateTime));
                        checkAnomaly = false;
                        if (Schedule.isValidDate(taskDateOnly)) {
                            schedule.addToSchedule(list.get(list.size() - 1), schedule.convertStringToDate(taskDateOnly));
                        }
                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                    ui.wrong_description_error();
                    return;
                } catch (ParseException ignore) {
                    return;
                }
            } else if (taskType.equals("range")) {
                try {
                    String taskDescription = taskDescriptionFull.split("/", 2)[0];
                    String taskTime = taskDescriptionFull.split("/", 2)[1].substring(3);
                    String[] dateTime = taskTime.split(" and ");
                    LocalDateTime from = getDateTime(dateTime[0]);
                    LocalDateTime by = getDateTime(dateTime[1]);
                    if (!DetectAnomalies.test(new RangedTask(taskDescription,from,by),list)) {
                        list.add(new RangedTask(taskDescription, from, by));
                        checkAnomaly = false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    ui.wrong_description_error();
                    return;
                }
            } else if (taskType.equals("recur")) {
                try {
                    String taskDescription = taskDescriptionFull.split("/", 2)[0];
                    String taskTime = taskDescriptionFull.split("/",2)[1].substring(3).split(" every")[0];
                    LocalDateTime dateTime = getDateTime(taskTime);
                    String[] inputWords = taskDescriptionFull.split(" ");
                    int num = Integer.parseInt(inputWords[inputWords.length - 2]);
                    String frequency = inputWords[inputWords.length - 1];
                    int periodInMin = 0;

                    switch (frequency) {
                    case "minutes":
                        periodInMin = num;
                        break;
                    case "hours":
                        periodInMin = num * 60;
                        break;
                    case "days":
                        periodInMin = num * 60 * 24;
                        break;
                    case "weeks":
                        periodInMin = num * 60 * 24 * 7;
                        break;
                    case "months":
                        periodInMin = num * 60 * 24 * 7 * 4;
                        break;
                    default:
                        System.out.println("You have typed in the wrong format. Please re-add the recurring task.");
                    }
                    if (!DetectAnomalies.test(new RecurringTask(taskDescription, dateTime, num + " " + frequency, periodInMin),list)) {
                        list.add(new RecurringTask(taskDescription, dateTime, num + " " + frequency, periodInMin));
                        checkAnomaly = false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    ui.wrong_description_error();
                    return;
                }
            }
        }
        if (!checkAnomaly) {
            String output = "\t  " + list.get(list.size() - 1).toString();
            System.out.println("\t_____________________________________");
            System.out.println("\tGot it. I've added this task:");
            System.out.println(output);
            // Printing number of items in list
            System.out.println("\tNow you have " + list.size() + " tasks in the list.");
            System.out.println("\t_____________________________________\n\n");
        } else {
            System.out.println("Task clashes with another existing task in the list!");
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
                System.out.println("\t_____________________________________");
                System.out.println("\tNice! I've marked this task as done:");
                System.out.println("\t  " + (i + 1) + "." + list.get(i).toString());
                System.out.println("\t_____________________________________\n\n");
            }
        } catch (IndexOutOfBoundsException e) {
            ui.task_doesnt_exist_error();
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
            System.out.println("\t  " + (i + 1) + "." + lastTask.toString());
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
     * @throws IndexOutOfBoundsException if an out of bounds index is requested.
     */
    public void snoozeTask(int i) {
        try {
            System.out.println("\t_____________________________________");
            System.out.println("\tYou are requesting to snooze the following task:");
            System.out.println("\t" + list.get(i).toString() + "\n");
            System.out.println("\tPlease choose one of the following way to snooze this task.");
            System.out.println("\t1) Enter a number followed by minutes/hours/days/weeks/months");
            System.out.println("\t2) Enter the new date and time in the following format (dd/MM/yyyy HHmm)");
            System.out.println("\t_____________________________________");

            Scanner scanner = new Scanner(System.in);
            String rawInput = scanner.nextLine();
            String[] userInput = rawInput.split(" ");
            boolean failSnooze = false;

            if (userInput[0].contains("/")) {
                LocalDateTime localDateTime = getDateTime(rawInput);
                list.get(i).setDateTime(localDateTime);
            } else {
                int num = Integer.parseInt(userInput[0]);
                LocalDateTime ldt = list.get(i).getDateTime();

                switch (userInput[1]) {
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
                    failSnooze = true;
                }
            }

            if (!failSnooze) {
                System.out.println("\t_____________________________________");
                System.out.println("\tGot it. You have snoozed the task.");
                System.out.println("\t" + list.get(i).toString());
                System.out.println("\t_____________________________________");
            }
        } catch (IndexOutOfBoundsException e) {
            ui.task_doesnt_exist_error();
        }
    }

    /**
     * Displays the list of tasks contained in the object.
     */
    public void displayList() {
        // If user inputs list without appending list even once.
        if (list.isEmpty()) {
            ui.show_empty_list_error();
            return;
        }

        System.out.println("\t_____________________________________");
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t" + (i + 1) + "." + list.get(i).toString());
        }
        System.out.println("\t_____________________________________\n\n");
    }

    /**
     * Finds task which contains a character sequence supplied.
     *
     * @param taskDescription a character sequence from which tasks will be found.
     */
    public void findTask(String taskDescription) {
        ArrayList<Integer> matchingTasks = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDescription().contains(taskDescription)) {
                matchingTasks.add(Integer.valueOf(i));
            }
        }
        if (matchingTasks.isEmpty()) {
            ui.task_doesnt_exist_error();
            return;
        }
        System.out.println("\t_____________________________________");
        System.out.println("\tFound " + matchingTasks.size() + ". Here you go.");
        for (Integer id : matchingTasks) {
            System.out.println("\t  " + (id + 1) + "." + list.get(id).toString());
        }
        System.out.println("\t_____________________________________\n\n");
    }

    public Task getLastTask() {
        return this.list.get(list.size() - 1);
    }

    /**
     * Checks whether two instaces of TaskList are equal.
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
