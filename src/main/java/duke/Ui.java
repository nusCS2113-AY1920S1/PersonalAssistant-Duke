package duke;

import duke.Parser.ParserCommand;
import duke.Parser.Parser;
import duke.module.Lesson;
import duke.module.Schedule;
import duke.sports.ManageStudents;
import duke.sports.MyPlan;
import duke.task.TaskList;
import duke.data.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

public class Ui {
    /**
     * Declaring new Parser type.
     */
    private ParserCommand parser = new ParserCommand();

    /**
     * A method to run the program.
     */
    public void execute() {
        Scanner sc = new Scanner(System.in);
        welcome();
        while (true) {
            if (sc.hasNextLine()) {
                String command = sc.nextLine();
                if (command.equals("bye")) {
                    goodbye();
                    System.exit(0);
                } else if (command.equals("home")) {
                    mainMenu();
                } else {
                    parser.parseCommand(command);
                }
            }
        }
    }

    /**
     * This function prints out the welcome message of Duke.
     */
    public void welcome() {
        System.out.println("Hello! I'm Duke, your personal coach manager!\n"
            + "What can I do for you?");
    }

    /**
     * Displays main menu on command line.
     */
    public void mainMenu() {
        System.out.println("SPORTS MANAGER\n"
            + "1. View Training Schedule\n"
            + "2. Manage Students\n"
            + "3. Training Circuits");
    }

    /**
     * This function prints out the goodbye message of Duke.
     */
    public void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * This function prints out the heading when option 1 is chosen.
     */
    public void trainingScheduleHeading() {
        System.out.flush();
        System.out.println("TRAINING SCHEDULE:\n");
    }

    /**
     * This function prints out the heading when option 2 is chosen.
     */
    public void manageStudentsHeading() {
        System.out.flush();
        System.out.println("MANAGE STUDENTS:\n"
            + "1. Student List - View all students available "
            + "and edit student particulars (Cmd: student list)\n"
            + "2. Add student - Adding a new student to the list "
            + "with main details (Cmd: student add [name],[age],[address]) (\n"
            + "3. Remove Student - Remove a student in a list "
            + "(Cmd: student delete [index of student in the list])\n"
            + "4. Search Student - Finding a particular student in the list "
            + "(Cmd: student search [name])");
    }

    /**
     * This function prints out the heading when option 3 is chosen.
     */
    public void trainingProgramHeading() {
        System.out.flush();
        System.out.println("TRAINING PROGRAM:\n"
            + "1. Plan list - View all the plans available "
            + "and check a plan or edit it (cmd: TBC)\n"
            + "2. Create plan - Create a new plan of a "
            + "specified intensity level (Cmd: plan new [intensity level])"
            + "3. Edit plan - Edit a specified plan by adding new "
            + "activities or switching activity positions "
            + "(Cmd: plan edit [intensity level] [plan number])");
    }
    /**
     * Displays student from student list that is matching to search.
     *
     * @param foundStudent Name of student that has been found.
     * @return Student that has been found
     */
    public String printFoundStudent(final String foundStudent) {
        return "Here are the matching names in your list: " + foundStudent;
    }

    /**
     * Prints message when asking for date for goal.
     */
    public void showGoalPromptDate() {
        System.out.println("Please enter the date of the day "
            + "in this format: dd/MM/yyyy");
    }

    /**
     * Prints all possible actions with goal once entered.
     * @param goalDate The date of the day
     */
    public void showGoalAllActions(String goalDate) {
        System.out.println(
            "\nWhat would you like to do on " + goalDate + "?\n"
                + "1. View goals of the day\n"
                + "2. Add a goal of the day\n"
                + "3. Delete a goal of the day\n"
                + "4. Clear all goals of the day\n"
                + "5. Quit goal of the day");
    }

    /**
     * Prints message when asking for goal to add.
     * @param goalDate The date of the day
     */
    public void showGoalPromptAddGoal(String goalDate) {
        System.out.println("To add a goal to "
            + goalDate + ", enter the goal.");
    }

    /**
     * Prints message when asking for goal to delete.
     * @param goalDate The date of the day
     */
    public void showGoalPromptDeleteGoal(String goalDate) {
        System.out.println("To delete a goal from "
            + goalDate + ", enter the goal.");
    }

    /**
     * Prints message when quitting goal of the day.
     */
    public void showQuitGoal() {
        System.out.println(
            "You have quit the goal of the day.");
    }

    /**
     * Prints message to show help for goal of the day.
     */
    public void showHelpGoal() {
        System.out.println(
            "Actions you can take now: \n"
            + "Enter the date in this format: dd/MM/yyyy\n"
            + "Press 1 to view goals of the day\n"
            + "Press 2 to add a goal of the day\n"
            + "Press 3 to delete a goal of the day\n"
            + "Press 4 to clear all goals of the day\n"
            + "Press 5 to quit goal of the day");
    }

    /**
     * Prints message asking for full command.
     */
    public void showFullCommand() {
        System.out.println("Please enter the full command.");
    }

    /**
     * Prints message asking for correct format.
     */
    public void showCorrectFormat() {
        System.out.println(
            "Please enter the details in the correct format.");
    }

    /**
     * Prints message showing don't know what it means.
     */
    public void showDontKnow() {
        System.out.println("\u2639 OOPS!!! I'm sorry,"
            + "but I don't know what that means :-(");
    }

    /**
     * Prints message when asking for date for lesson.
     */
    public void showLessonPromptDate() {
        System.out.println("Please enter the date of the day "
            + "in this format: dd/MM/yyyy");
    }

    /**
     * Prints all possible actions with lesson once entered.
     * @param lessonDate The date of the day
     */
    public void showLessonAllActions(String lessonDate) {
        System.out.println(
            "\nWhat would you like to do on " + lessonDate + "?\n"
                + "1. View lessons of the day\n"
                + "2. Add a lesson of the day\n"
                + "3. Delete a lesson of the day\n"
                + "4. Clear all lessons of the day\n"
                + "5. Quit lesson of the day");
    }

    /**
     * Prints message when asking for lesson to add.
     * @param lessonDate The date of the day
     */
    public void showLessonPromptAddLesson(String lessonDate) {
        System.out.println("To add a lesson to "
            + lessonDate + ", enter the lesson.");
    }

    /**
     * Prints message when asking for lesson to delete.
     * @param lessonDate The date of the day
     */
    public void showLessonPromptDeleteLesson(String lessonDate) {
        System.out.println("To delete a lesson from "
            + lessonDate + ", enter the lesson.");
    }

    /**
     * Prints message when quitting lesson of the day.
     */
    public void showQuitLesson() {
        System.out.println(
            "You have quit the lesson of the day.");
    }
}
