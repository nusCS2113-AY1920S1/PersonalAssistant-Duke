package duke.parser;

import duke.data.ScheduleStorage;
import duke.exceptions.DukeException;
import duke.view.CliView;
import duke.data.Storage;
import duke.models.Schedule;
import duke.view.CliViewSchedule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import duke.util.ApacheLogger;

//@@author Sfloydzy
public class ParserSchedule {
    /**
     * Constants to represent the index 3.
     */
    private final int indexThree = 3;
    /**
     * Constants to represent the index 4.
     */
    private final int indexFour = 4;
    /**
     * Constants to represent the index 5.
     */
    private final int indexFive = 5;

    /**
     * Boolean status to check if the class can exit.
     */
    private boolean isRunning = true;
    /**
     * The ui object responsible for showing things to the user.
     */
    private CliViewSchedule cliViewSchedule;
    /**
     * The schedule object.
     */
    private Schedule schedule;
    /**
     * The scanner object responsible for taking in user input.
     */
    private Scanner sc;

    private ScheduleStorage scheduleStorage;

    /**
     * Constructor for ParserSchedule.
     *
     * @throws FileNotFoundException if file does not exist
     * @throws ParseException        if user input is not in the correct format
     */
    public ParserSchedule() {
        cliViewSchedule = new CliViewSchedule();
        sc = new Scanner(System.in);
        schedule = new Schedule();
    }

    /**
     * Method to run when entering daily schedule.
     *
     * @throws ParseException if user input is not in the correct format
     */
    public void dailySchedule() {
        cliViewSchedule.showSchedulePromptDate();
        sc.nextLine();
        String scheduleDate = sc.nextLine();
        // String day = schedule.getDay(scheduleDate);
        // schedule of timeslot.txt cannot be loaded
        // todo must fix the storage loading capabilities
        String day = "empty";
        if (day.equals("empty")) {
            isRunning = false;
            cliViewSchedule.showEmptyList();
        }
        while (isRunning) {
            //            try {
            //                cliViewSchedule.showScheduleAllActions(scheduleDate);
            //                int executeType = sc.nextInt();
            //                sc.nextLine();  // This line you have
            //                // to add (It consumes the \n character)
            //                switch (executeType) {
            //                case 1:
            //                    System.out.println(schedule.getDay(scheduleDate));
            //                    break;
            //
            //                case 2:
            //                    cliViewSchedule.showPromptStartTime();
            //                    cliViewSchedule.showPromptEndTime();
            //                    cliViewSchedule.showPromptClassLocation();
            //                    String className = sc.nextLine();
            //                    String startTime = sc.nextLine();
            //                    String endTime = sc.nextLine();
            //                    String location = sc.nextLine();
            //                    cliViewSchedule.showPromptClassName();
            //                    System.out.println(schedule.addClass(startTime,
            //                        endTime, location,
            //                        className, scheduleStorage));
            //                    break;
            //
            //                case indexThree:
            //                    cliViewSchedule.showPromptStartTime();
            //                    String delstartTime = sc.nextLine();
            //                    cliViewSchedule.showPromptClassName();
            //                    String delclassName = sc.nextLine();
            //                    System.out.println(
            //                        schedule.delClass(
            //                            delstartTime, delclassName, scheduleStorage));
            //                    break;
            //
            //                case indexFour:
            //                    System.out.println(
            //                        schedule.delAllClass(scheduleDate, scheduleStorage));
            //                    break;
            //
            //                case indexFive:
            //                    isRunning = false;
            //                    cliViewSchedule.showQuitClass();
            //                    break;
            //                default:
            //                    cliViewSchedule.showDontKnow();
            //                }
            //            } catch (ArrayIndexOutOfBoundsException e) {
            //                cliViewSchedule.showFullCommand();
            //            } catch (ParseException e) {
            //                cliViewSchedule.showCorrectFormat();
            //            }
        }
    }

    /**
     * Method to run when entering weekly schedule.
     */
    public void weeklySchedule() {
        System.out.println(schedule.getWeek());

    }

    /**
     * Method selects the month.
     *
     * @param month the selected month
     */
    public void selectMonth(int month) {
        if (month > 12 || month < 0) {
            cliViewSchedule.message("Invalid month");
        } else if (month < 12) {
            cliViewSchedule.bufferLine();
            schedule.getMonth(month - 1);
            cliViewSchedule.bufferLine();
            cliViewSchedule.message("Enter the date of the day you want to plan!");
            int day = sc.nextInt();
            schedule.getTable(day, month);
        }
    }

    /**
     * Method to run when entering monthly schedule.
     */
    public void monthlySchedule() {
        boolean runMonth = true;
        cliViewSchedule.printMonthMenu();
        while (runMonth) {
            String input = sc.next();
            if (input.equals("back")) {
                runMonth = false;
            } else if (input.equals("help")) {
                cliViewSchedule.printMonthMenu();
            } else {
                try {
                    int month = Integer.parseInt(input);
                    selectMonth(month - 1);
                } catch (NumberFormatException e) {
                    cliViewSchedule.showDontKnow();
                }
            }
            cliViewSchedule.printMonthMenu();
        }
    }

    //@@author Sfloydzy

    /**
     * Method to parse the command input from the menu.
     *
     * @throws ParseException        Parse
     * @throws FileNotFoundException File not found
     */
    public void parseCommand() throws ParseException, IOException {
        final int dailySchedule = 1;
        final int weeklySchedule = 2;
        final int monthlySchedule = 3;
        final int back = 4;
        int input;
        boolean runSchedule = true;
        try {
            while (runSchedule) {
                cliViewSchedule.trainingScheduleHeading();
                input = sc.nextInt();
                switch (input) {
                case dailySchedule:
                    boolean runDaily = true;
                    while (runDaily) {
                        cliViewSchedule.dailyScheduleHeading();
                        input = sc.nextInt();
                        if (input == 1) { //access daily schedule
                            dailySchedule();
                        } else if (input == 2) { //access daily goals
                            new ParserGoal().runGoal();
                        } else if (input == 3) { //access daily lessons
                            new ParserLesson().runLesson();
                        } else if (input == 4) { //back
                            runDaily = false;
                        } else {
                            cliViewSchedule.showDontKnow();
                        }
                    }
                    break;
                case weeklySchedule:
                    weeklySchedule();
                    break;
                case monthlySchedule:
                    monthlySchedule();
                    break;
                case back:
                    runSchedule = false;
                    break;
                default:
                    cliViewSchedule.showDontKnow();
                }
            }
        } catch (InputMismatchException e) {
            cliViewSchedule.showCorrectFormat();
        }
    }
}
