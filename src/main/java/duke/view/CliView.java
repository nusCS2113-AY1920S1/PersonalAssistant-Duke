package duke.view;

import duke.command.ExitCommand;
import duke.exceptions.DukeException;
import duke.parser.ParserCommand;
import duke.util.ApacheLogger;

import java.util.Scanner;

public class CliView {

    /**
     * Represents the ExitCommand class.
     */
    private ExitCommand exitCommand = new ExitCommand();

    /**
     * Scanner object to read user input.
     */
    private Scanner scan = new Scanner(System.in);

    /**
     * Declaring new parser type.
     */
    private ParserCommand parser = new ParserCommand();

    /**
     * A method to run the program.
     */
    public void execute() {
        showWelcome();
        while (true) {
            showMainMenu();
            final int goodBye = 4;
            if (scan.hasNextLine()) {
                String input = scan.nextLine();
                if (input.equals(String.valueOf(goodBye))) {
                    showGoodBye();
                    ApacheLogger.logMessage("CliView", "Exit Coach Manager");
                    exitCommand.exitProgram();
                } else if (input.equals("home")) {
                    showMainMenu();
                } else {
                    parser.parseCommand(input);
                }
            }
        }
    }

    /**
     * This function prints out the welcome message of Duke.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Duke, your personal coach manager!\n"
            + "What can I do for you?");
    }

    /**
     * Reads in an entire line of user input.
     *
     * @return The entire line of user input.
     */
    public String readCommand() {
        return scan.nextLine();
    }

    /**
     * Displays main menu on command line.
     */
    public void showMainMenu() {
        printLine();
        System.out.println("SPORTS MANAGER\n"
            + "1. View Training Schedule\n"
            + "2. Manage Students\n"
            + "3. Training Circuits\n"
            + "4. Exit");
    }

    /**
     * Print line.
     */
    public void printLine() {
        System.out.println("_________________________"
            + "___________________________");
    }

    /**
     * This function prints out the goodbye message of Duke.
     */
    public void showGoodBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * This function prints out the heading when option 1 is chosen.
     */
    public void trainingScheduleHeading() {
        System.out.flush();
        System.out.println("TRAINING SCHEDULE:\n"
            + "1. Daily Schedule\n"
            + "2. Monthly Schedule\n"
            + "3. Back");
    }

    /**
     * This function prints out the heading when
     * Daily Schedule option is chosen.
     */
    public void dailyScheduleHeading() {
        System.out.flush();
        System.out.println("DAILY SCHEDULE:\n"
            + "1. Classes of the day\n"
            + "2. Goals of the day\n"
            + "3. Lessons learnt of the day\n"
            + "4. Back");
    }

    /**
     * This function prints out the heading when option 2 is chosen.
     */
    public void manageStudentsHeading() {
        System.out.flush();
        printLine();
        System.out.println("MANAGE STUDENTS:\n"
            + "1. Student List \n"
            + "2. Add student \n"
            + "3. Delete Student \n"
            + "4. Student Progress \n"
            + "5. View student details \n"
            + "6. Exit ");

    }

    /**
     * This function prints the student's progress heading.
     */
    public void studentProgressHeading() {
        printLine();
        System.out.println("STUDENT PROGRESS:\n"
            + "1. Add progress - (Cmd: add) \n"
            + "2. View Student Progress - (Cmd: view)\n"
            + "3. Exit Page - (Cmd - back)");
    }

    /**
     * This function prints out the heading when option 3 is chosen.
     */
    public void trainingProgramHeading() {
        System.out.flush();
        printLine();
        System.out.println("TRAINING PROGRAM:\n"
            + "1. Plan list - View all the plans available "
            + " (Choose a plan from the list)\n"
            + "2. Create plan - Create a new plan of a "
            + "specified intensity level (Cmd: plan new [intensity level])\n"
            + "3. Edit plan - Edit a specified plan by adding new "
            + "activities or switching activity positions "
            + "(Choose a plan from the list)\n"
            + "4. Delete plan(Choose a plan from the list)\n"
            + "5. Back");
        printLine();
    }

    /**
     * Show plan list when option 1 is chosen from trainingProgramHeading.
     */
    public void planListHeading() {
        System.out.flush();
        System.out.println("Choose a plan from the list below: ");
    }

    /**
     * Prompt user that a specified plan is not present.
     */
    public void planNotFound() {
        System.out.flush();
        System.out.println("Plan is not found.");
    }

    /**
     * Prompt user input when option 2 is chosen from trainingProgramHeading.
     */
    public void createPlanHeading() {
        System.out.flush();
        printLine();
        System.out.println("Input an intensity level for the new plan:\n"
            + "high, moderate, relaxed");
        printLine();
    }

    /**
     * Prompt user to choose the correct option from trainingProgramHeading.
     */
    public void showCorrectPlanHeading() {
        System.out.flush();
        System.out.println("Choose only options 1 to 5 for training plans");
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
     *
     * @param goalDate The date of the day
     */
    public void showGoalAllActions(final String goalDate) {
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
     *
     * @param goalDate The date of the day
     */
    public void showGoalPromptAddGoal(final String goalDate) {
        System.out.println("To add a goal to "
            + goalDate + ", enter the goal.");
    }

    /**
     * Prints message when asking for goal to delete.
     *
     * @param goalDate The date of the day
     */
    public void showGoalPromptDeleteGoal(final String goalDate) {
        System.out.println("To delete a goal from "
            + goalDate + ", enter the goal.");
    }

    /**
     * Prints message when quitting goal of the day.
     */
    public void showQuitGoal() {
        System.out.println("________________________________"
            + "________________________________");
        System.out.println(
            "You have quit the goal of the day.");
        System.out.println("________________________________"
            + "________________________________");
    }

    /**
     * Prints message asking for full command.
     */
    public void showFullCommand() {
        System.out.println("________________________________"
            + "________________________________");
        System.out.println("Please enter the full command.");
        System.out.println("________________________________"
            + "________________________________");
    }

    /**
     * Prints message asking for correct format.
     */
    public void showCorrectFormat() {
        System.out.println("________________________________"
            + "________________________________");
        System.out.println(
            "Please enter the details in the correct format.");
        System.out.println("________________________________"
            + "________________________________");
    }

    /**
     * Prints message asking for correct format.
     */
    public void showCorrectCommand() {
        System.out.println("________________________________"
            + "________________________________");
        System.out.println(
            "Please enter the correct command.");

    }

    /**
     * Prints message showing don't know what it means.
     */
    public void showDontKnow() {
        System.out.println("________________________________"
            + "________________________________");
        System.out.println("OOPS!!! I'm sorry,"
            + "but I don't know what that means :-(");
        System.out.println("________________________________"
            + "________________________________");
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
     *
     * @param lessonDate The date of the day
     */
    public void showLessonAllActions(final String lessonDate) {
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
     *
     * @param lessonDate The date of the day
     */
    public void showLessonPromptAddLesson(final String lessonDate) {
        System.out.println("To add a lesson to "
            + lessonDate + ", enter the lesson.");
    }

    /**
     * Prints message when asking for lesson to delete.
     *
     * @param lessonDate The date of the day
     */
    public void showLessonPromptDeleteLesson(final String lessonDate) {
        System.out.println("To delete a lesson from "
            + lessonDate + ", enter the lesson.");
    }

    /**
     * Prints message when quitting lesson of the day.
     */
    public void showQuitLesson() {
        System.out.println("________________________________"
            + "________________________________");
        System.out.println(
            "You have quit the lesson of the day.");
        System.out.println("________________________________"
            + "________________________________");
    }

    /**
     * Prints message when asking for proper intensity level.
     */
    public void showIntensityLevel() {
        System.out.println("Please input a proper "
            + "intensity level: high, moderate, relaxed");
    }

    /**
     * Prints message when no plans are available.
     */
    public void noPlanInMap() {
        System.out.println("No plans available.");
    }

    /**
     * Prints message when asking for correct intensity level and plan number.
     */
    public void showIntensityAndNumber() {
        System.out.println("Please input the correct"
            + " intensity and plan number.");
    }

    /**
     * Prints message when plan is successfully removed.
     */
    public void showPlanRemoved() {
        System.out.println("Plan successfully removed.");
    }

    /**
     * Prints message when plan is successfully created.
     */
    public void showPlanCreated() {
        System.out.println("Plan successfully created.");
    }

    /**
     * Prints message if no activity has been added to show.
     */
    public void showNoActivity() {
        System.out.println("No activity has been added.");
    }

    /**
     * Prints message when plan is being created.
     *
     * @param i The intensity of the plan to be created.
     */
    public void showPlanCreating(final String i) {
        printLine();
        System.out.println("Creating plan of " + i + " intensity.\n"
            + "Please input activity in the format of "
            + "[activity(Eg: push-ups)] [number of sets] [number of reps].");
        printLine();
    }

    /**
     * Prints message to prompt user for activity format.
     */
    public void showAddActivityPrompt() {
        printLine();
        System.out.println("Please input activity in the format of "
               + "[activity(Eg: push-ups)] [number of sets] [number of reps].");
        printLine();
    }

    /**
     * Prints message when activity is successfully added.
     */
    public void showActivityAdded() {
        System.out.println("Successfully added activity: ");
    }

    /**
     * Prints message to show all plans loaded into the current list.
     *
     * @param plans The plans to be printed out.
     */
    public void showViewPlan(final String plans) {
        System.out.println(plans);
    }

    /**
     * Prints message to prompt the user on what to do next.
     */
    public void showPlanPrompt1() {
        System.out.println("Continue adding activities "
            + "or finalize plan.");
    }

    /**
     * Prints message to prompt the user on what to do next.
     */
    public void showPlanPrompt2() {
        System.out.println("Input new activity, finalize "
            + "the plan(finalize), look at the list(show)"
            + ", edit the positions(switch) or "
            + "cancel plan creation(cancel)");
    }

    /**
     * Prints message to prompt user to edit plan.
     */
    public void showEditPlanPrompt1() {
        System.out.println("Input new activity(add), look at the list(show)"
                + ", edit activity positions(switch) or"
                + " finalize the plan(finalize)");
    }

    /**
     * Prompt user to input position numbers for activities.
     */
    public void showEditPlanPrompt2() {
        System.out.println("Choose the activity and "
            + "the position to switch to.");
    }

    /**
     * Prompt user that switching is successful.
     *
     * @param a initial activity position
     * @param b final activity position
     */
    public void showSuccessfulSwitch(final int a, final int b) {
        System.out.println("Successfully switched positions for activity "
            + a + " to position " + b);
    }

    /**
     * Prints message when editing is finalized.
     */
    public void showEditPlanSuccessful() {
        printLine();
        System.out.println("Plan successfully changed.");
        printLine();
    }

    /**
     * Prompt users when there are not enough activities added.
     */
    public void showNotEnoughActivitiesForSwitch() {
        System.out.println("Add more activities before switching positions");
    }

    /**
     * Prompt user to input correct activity position number.
     */
    public void showInputCorrectPositionNumber() {
        System.out.println("Input the correct position numbers.");
    }

    /**
     * Prints message when asking for date for schedule.
     */
    public void showSchedulePromptDate() {
        System.out.println("Please enter the date of the day "
            + "in this format: dd/MM/yyyy");
    }

    /**
     * Prints all possible actions with schedule once entered.
     *
     * @param scheduleDate The date to see classes of the day
     */
    public void showScheduleAllActions(final String scheduleDate) {
        System.out.println(
            "\nWhat would you like to do on " + scheduleDate + "?\n"
                + "1. View all classes\n"
                + "2. Add a class\n"
                + "3. Delete a class\n"
                + "4. Clear all classes\n"
                + "5. Quit classes");
    }

    /**
     * Prints message when quitting class.
     */
    public void showQuitClass() {
        System.out.println(
            "You have quit class.");
    }

    /**
     * Prints message when asking for the start time of a class.
     */
    public void showPromptStartTime() {
        System.out.println(
            "Please enter the start time in this format: dd/MM/yyyy HHmm");
    }

    /**
     * Prints message when asking for the end time of a class.
     */
    public void showPromptEndTime() {
        System.out.println(
            "Please enter the end time in this format: dd/MM/yyyy HHmm");
    }

    /**
     * Prints message when asking for the location of a class.
     */
    public void showPromptClassLocation() {
        System.out.println("Please enter the location the class will be held");
    }

    /**
     * Prints message when asking for the name of a class.
     */
    public void showPromptClassName() {
        System.out.println("Please enter the name of the class.");
    }

    /**
     * Prints error message when there is incomplete daily list.
     */
    public void showEmptyList() {
        System.out.println("Sorry, there are no days matching "
            + "what you have entered.");
    }

    /**
     * Adding student format.
     */
    public void addStudentFormat() {
        System.out.println("Insert [Name],[Age],[Address] to add new student.\n");
    }

    /**
     * Method prints out string in system.
     *
     * @param s message to be printed out
     */
    public void message(String s) {
        System.out.println(s);
    }

    /**
     * Method prints a buffer line between sections.
     */
    public void bufferLine() {
        System.out.println("--------------------------");
    }

    /**
     * Show generic error message for exceptions.
     *
     * @param msg The error discovered
     */
    public void errMessage(String msg) {
        DukeException dukeException = new DukeException();
        dukeException.errMessage(msg);
    }
}
