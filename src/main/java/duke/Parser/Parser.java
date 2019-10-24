package duke.Parser;

import duke.data.Storage;
import duke.module.Reminder;
import duke.module.Schedule;
import duke.task.TaskList;
import duke.Ui;
import duke.sports.ManageStudents;
import duke.sports.MyClass;
import duke.sports.MyPlan;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;

/**
 * Parser is the controller for the string inputs received
 * by the standard input.
 */
public class Parser {

    /**
     * The ui object responsible for showing things to the user.
     */
    private Ui ui = new Ui();

    /**
     * Constants to represent the index 3.
     */
    public static final int INDEX_THREE = 3;
    /**
     * Constants to represent the index 4.
     */
    public static final int INDEX_FOUR = 4;
    /**
     * Constants to represent the index 5.
     */
    public static final int INDEX_FIVE = 5;
    /**
     * Constants to represent the index 6.
     */
    public static final int INDEX_SIX = 6;
    /**
     * Constants to represent the index 7.
     */
    public static final int INDEX_SEVEN = 7;
    /**
     * Constants to represent the index 10.
     */
    public static final int INDEX_TEN = 10;

    /**
     * This function takes the standard input defined by the user and
     * parses it into instructions for the Storage to read.
     *
     * @param io       The input from command line
     * @param tasks    The TaskList object that manages tasks
     * @param storage  The main class that writes to or from files
     * @param students The student object that manages students in classes
     * @param schedule Manages all the task in the day/week/month
     * @param plan     The training circuit plans
     * @throws FileNotFoundException File not found
     * @throws ParseException        Parse
     */
    public void parseInput(final String io,
                           final TaskList tasks,
                           final Storage storage,
                           final ManageStudents students,
                           final Schedule schedule,
                           final MyPlan plan)
        throws FileNotFoundException, ParseException {
        int index = 1;
        String input = io;
        String[] word = io.split(" ");
        String cmd = word[0];

        switch (cmd) {

        case "reminder":
            try {
                index = input.indexOf("before");
                Date date = tasks.dateConvert(
                    input.substring(index + INDEX_SEVEN));
                Reminder reminder = new Reminder(date);
                reminder.getReminders(tasks);
            } catch (StringIndexOutOfBoundsException e) {
                System.err.println("Incorrect format");
            }
            break;

        case "delete":
            index = Integer.parseInt(input.substring(INDEX_SEVEN)) - 1;
            tasks.deleteTask(index);
            storage.updateFile(tasks.getList());
            break;

        case "find":
            String searchWord = input.substring(INDEX_FIVE);
            tasks.findTask(searchWord);
            break;

        case "date":
            String searchDate = input.substring(INDEX_FIVE);
            if (searchDate.length() < INDEX_TEN) {
                System.out.println("Please enter input in the form: "
                    + "date dd/MM/YYYY");
            } else {
                tasks.findDate(searchDate);
            }
            break;

        case "class":
            try {
                index = input.indexOf("/every");
                String info = input.substring(INDEX_SIX, index - 1);
                String day = input.substring(index + INDEX_SEVEN);
                MyClass myclass = new MyClass(info, false, day);
                tasks.addTask(myclass, "C");
                storage.saveFile("C", myclass, myclass.getDay());
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println(
                    "\u2639 OOPS!!! "
                        + "Please enter input in the form:"
                        + "class XXX /every YYY");
            }
            break;

        case "schedule":
            Storage scheduleStorage = new Storage(
                ".\\src\\main\\java\\duke\\data\\timeslots.txt");

            try {
                if (word[1].equals("view-week")) {
                    System.out.println(schedule.getWeek());
                } else if (word[1].equals("view-month")) {
                    System.out.println(schedule.getMonth());
                } else if (word[1].equals("view-day")) {
                    try {
                        System.out.println(schedule.getDay(word[2]));
                    } catch (ArrayIndexOutOfBoundsException
                        | ParseException e) {
                        System.err.println("Enter a date please.");
                    }
                } else if (word[1].equals("add")) {
                    String startTime = word[2] + " " + word[INDEX_THREE];
                    String endTime = word[INDEX_FOUR] + " " + word[INDEX_FIVE];
                    String location = word[INDEX_SIX];
                    String className = word[INDEX_SEVEN];
                    System.out.println(schedule.addClass(
                        startTime,
                        endTime,
                        location,
                        className,
                        tasks,
                        scheduleStorage));
                } else if (word[1].equals("delete")) {
                    String startTime = word[2] + " " + word[INDEX_THREE];
                    String className = word[INDEX_FOUR];
                    System.out.println(
                        schedule.delClass(
                            startTime, className, scheduleStorage));
                } else if (word[1].equals("delete-all")) {
                    String date = word[2];
                    System.out.println(
                        schedule.delAllClass(date, scheduleStorage));
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                ui.showFullCommand();
            }
            break;
        default:
            ui.showDontKnow();
            break;
        }
    }
}
