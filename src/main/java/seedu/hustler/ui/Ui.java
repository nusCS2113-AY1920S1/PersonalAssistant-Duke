package seedu.hustler.ui;

import seedu.hustler.Hustler;
import seedu.hustler.data.CommandLog;
import seedu.hustler.task.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A class dedicated to performing interactions with the user.
 * Takes inputs and contains methods that output error messages.
 */
public class Ui {

    public static final String LINE = "\t_____________________________________";

    /**
     * Default constructor.
     */
    public Ui() {
    }

    /**
     * Print with formatting.
     */
    public void show_message(String message) {
        System.out.println(LINE);
        System.out.println("\t" + message);
        System.out.println(LINE);
    }

    /**
     * Prints an output message if list history was not saved.
     */
    public void show_save_error() {
        System.out.println(LINE);
        System.out.println("\tCouldn't saveAchievements file.");
        System.out.println(LINE);
    }

    /**
     * Prints a bye message if user enters bye.
     */
    public void show_bye_message() {
        System.out.println(LINE);
        System.out.println("\tBye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    /**
     * Prints an error message if user does not enter a valid command.
     */
    public void correct_command_error() {
        if (!CommandLog.isRestoring()) {
            System.out.println(LINE);
            System.out.println("\tPlease enter a valid command: add, "
                + "list, bye, find, delete.");
            System.out.println(LINE);
        }
    }

    /**
     * Prints an error message if the format of event or deadline
     * commands is not correct.
     */
    public void wrong_description_error() {
        System.out.println(LINE);
        System.out.println("\tDescription needs a '/' before by/at");
        System.out.println(LINE);
    }

    /**
     * Prints an error message if user performs an operation on a nonexistent
     * task.
     */
    public void task_doesnt_exist_error() {
        System.out.println(LINE);
        System.out.println("\tTask doesn't exist. Please choose another.");
        System.out.println(LINE);
    }

    /**
     * Prints error message if an empty list is asked to be displayed.
     */
    public void show_empty_list_error() {
        System.out.println(LINE);
        System.out.println("\tList is empty. Please type "
            + "another command apart from list.");
        System.out.println(LINE);
    }

    /**
     * Prints an error message if a command like find, delete, todo, deadline,
     * event or others is entered without a following description.
     */
    public void empty_description_error() {
        System.out.println(LINE);
        System.out.println("\tPlease enter a description after the command.");
        System.out.println(LINE);
    }

    /**
     * Prints an error message when the format of the date/time
     * entered for event or deadline is not correct.
     */
    public void date_time_error() {
        System.out.println(LINE);
        System.out.println("\tFormat of time is incorrect either in command "
            + "or saveAchievements file. Saving event/deadline as mentioned without date "
            + "time parsing.");
        System.out.println(LINE);
    }

    /**
     * Prints an error message when the format of date is wrong when showing schedule.
     */
    public void dateFormatError() {
        System.out.println(LINE);
        System.out.println("\tFormat of date invalid. Please type it in in DD/MM/YYYY");
        System.out.println(LINE);
    }

    /**
     * Prints the new name of the avatar and lets the user know that it has been changed.
     */
    public void showNameChangeSuccess() {
        System.out.println(LINE);
        System.out.println("\tName has been successfully changed!");
        System.out.println("\tYour new name is: " + Hustler.avatar.getName());
        System.out.println(LINE);
    }

    /**
     * Prints the statistics of the avatar.
     */
    public void showAvatarStatistics() {
        System.out.println(LINE);
        System.out.println(Hustler.avatar.toString());
        System.out.println(LINE);
    }

    /**
     * Prints an error message when the format of show command is wrong.
     */
    public void showFormatError() {
        System.out.println(LINE);
        System.out.println("\tShow command invalid. Please only type \"show <DD/MM/YYYY>\"");
        System.out.println(LINE);
    }

    /**
     * A hello message when hustler is ran.
     */
    public void show_opening_string() {
        String logo = " _   _   _    _     _____  ______   _       ___      ___\n"
                + "|  | |  | | |  |  |  / ____/ |__   __|  | |     |  __|  / __  \\\n"
                + "|  |_| | | |  |  |   |  \\___      |  |    | |      | |__  | |__/ /\n"
                + "|  _  | | |   |  |   \\____ \\     |  |    | |     |  __|  |  _  /\n"
                + "|  | | | |  \\_/ |    ___/  |     |  |    | |___ |  |__  | / \\ \\\n"
                + "|_| |_|  \\___/  /_____/      |_|     |____||____| |_|  \\_\\\n";

        System.out.println("Hello from\n" + logo);
    }

    /**
     * Prints a message if list is empty.
     */
    public void show_list_empty() {
        System.out.println(LINE);
        System.out.println("\tNothing to be cleared! Task list is already empty!");
        System.out.println(LINE);
    }

    /**
     * Prints a message when list is cleared.
     */
    public void show_list_cleared() {
        System.out.println(LINE);
        System.out.println("\tAll tasks in the task list has been cleared! List is now empty!");
        System.out.println(LINE);
    }

    /**
     * Prints a message when a task has been snoozed.
     * @param taskDescription description of the snoozed task.
     */
    public void show_task_snoozed(String taskDescription) {
        System.out.println(LINE);
        System.out.println("\tGot it. You have snoozed the task.");
        System.out.println("\t" + taskDescription);
        System.out.println(LINE);
    }

    /**
     * Prints the entire collection of tasks in task list.
     * @param list collection of tasks.
     */
    public void show_list(ArrayList<Task> list) {
        System.out.println(LINE);
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + list.get(i).toString());
        }
        System.out.println(LINE);
    }

    /**
     * Prints a message when a task has been added.
     * @param list collection of tasks.
     */
    public void show_task_added(ArrayList<Task> list) {
        System.out.println(LINE);
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + list.get(list.size() - 1).toString());
        System.out.println("\tNow you have " + list.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Prints a message when a task clashes with another task in the list.
     */
    public void show_task_clash() {
        System.out.println(LINE);
        System.out.println("Task clashes with another existing task in the list!");
        System.out.println(LINE);
    }

    /**
     * Prints a message when a task has been completed.
     * @param taskDescription description of the completed task.
     */
    public void show_task_done(String taskDescription) {
        System.out.println(LINE);
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t  " + taskDescription);
        System.out.println(LINE);
    }

    /**
     * Prints a message when a task has been removed.
     * @param list collection of tasks.
     * @param taskDescription description of the removed task.
     */
    public void show_task_removed(ArrayList<Task> list, String taskDescription) {
        System.out.println(LINE);
        System.out.println("\tNoted. I have removed this task:");
        System.out.println("\t  " + taskDescription);
        System.out.println("\tNow there are " + list.size() + " tasks left.");
        System.out.println(LINE);
    }

    /**
     * Prints the tasks that contains the searched keyword.
     * @param list collection of tasks.
     * @param matchingTasks list of integers which contains the matching tasks.
     */
    public void show_matching_tasks(ArrayList<Task> list, ArrayList<Integer> matchingTasks) {
        System.out.println(LINE);
        System.out.println("\tFound " + matchingTasks.size() + ". Here you go.");
        for (Integer id : matchingTasks) {
            System.out.println("\t  " + (id + 1) + ". " + list.get(id).toString());
        }
        System.out.println(LINE);
    }

    /**
     * Prints a message when the task list has been sorted.
     * @param list collection of tasks.
     */
    public void show_list_sorted(ArrayList<Task> list) {
        System.out.println(LINE);
        System.out.println("\tTask list has been successfully sorted!");
        show_list(list);
    }
}
