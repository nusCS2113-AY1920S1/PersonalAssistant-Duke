package duke.parser;

import duke.CliView;
import duke.data.Storage;
import duke.module.Schedule;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

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
    private CliView cliView;
    /**
     * The schedule object.
     */
    private Schedule schedule;
    /**
     * The scanner object responsible for taking in user input.
     */
    private Scanner sc;
    /**
     * The storage object for Schedule.
     */
    private Storage scheduleStorage;

    /**
     * Constructor for ParserSchedule.
     *
     * @throws FileNotFoundException if file does not exist
     * @throws ParseException        if user input is not in the correct format
     */
    public ParserSchedule() throws FileNotFoundException, ParseException {
        cliView = new CliView();
        scheduleStorage = new Storage(
            ".\\src\\main\\java\\duke\\data\\timeslots.txt");
        schedule = new Schedule(scheduleStorage.loadSchedule());
        sc = new Scanner(System.in);
    }

    /**
     * Method to run when entering daily schedule.
     *
     * @throws ParseException if user input is not in the correct format
     */
    public void dailySchedule() throws ParseException {
        cliView.showSchedulePromptDate();
        String scheduleDate = sc.next();
        schedule.getDay(scheduleDate);
        while (isRunning) {
            try {
                cliView.showScheduleAllActions(scheduleDate);
                int executeType = sc.nextInt();
                sc.nextLine();  // This line you have
                // to add (It consumes the \n character)
                switch (executeType) {
                case 1:
                    System.out.println(schedule.getDay(scheduleDate));
                    break;

                case 2:
                    cliView.showPromptStartTime();
                    cliView.showPromptEndTime();
                    cliView.showPromptClassLocation();
                    String className = sc.nextLine();
                    String startTime = sc.nextLine();
                    String endTime = sc.nextLine();
                    String location = sc.nextLine();
                    cliView.showPromptClassName();
                    System.out.println(schedule.addClass(startTime,
                        endTime, location,
                        className, scheduleStorage));
                    break;

                case indexThree:
                    cliView.showPromptStartTime();
                    String delstartTime = sc.nextLine();
                    cliView.showPromptClassName();
                    String delclassName = sc.nextLine();
                    System.out.println(
                        schedule.delClass(
                            delstartTime, delclassName, scheduleStorage));
                    break;

                case indexFour:
                    System.out.println(
                        schedule.delAllClass(scheduleDate, scheduleStorage));
                    break;

                case indexFive:
                    isRunning = false;
                    cliView.showQuitClass();
                    break;
                default:
                    cliView.showDontKnow();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                cliView.showFullCommand();
            } catch (ParseException e) {
                cliView.showCorrectFormat();
            }
        }
    }

    /**
     * Method to run when entering weekly schedule.
     */
    public void weeklySchedule() {
        System.out.println(schedule.getWeek());
    }

    /**
     * Method to run when entering monthly schedule.
     */
    public void monthlySchedule() {
        System.out.println(schedule.getMonth());
    }
}
