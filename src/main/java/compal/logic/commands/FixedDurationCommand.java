package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.FixedDurationTask;
import compal.tasks.TaskList;

import java.util.Scanner;

/**
 * Executes user command "fixeddurationtask".
 */
public class FixedDurationCommand extends Command implements CommandParser {

    private static final String TOKEN = "/on";
    private static final String TOKEN_HOUR = "/hr";
    private static final String TOKEN_MINUTE = "/min";
    private TaskList taskList;

    /**
     * Constructs FixedDurationCommand object.
     *
     * @param d Duke.
     */
    public FixedDurationCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Adds a FixedDurationTask into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Duke.DukeException If user input after "fixeddurationtask" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Duke.DukeException {
        Scanner scanner = new Scanner(userIn);
        String event = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            String date = getDate(restOfInput);
            String time = getTime(restOfInput);
            int hour = getHour(restOfInput);
            int minute = getMinute(restOfInput);
            taskList.addTask(new FixedDurationTask(description, date, time, hour, minute));
            int arrSize = taskList.arrlist.size() - 1;
            String descToPrint = taskList.arrlist.get(arrSize).toString();
            duke.ui.printg(descToPrint);
        } else {
            duke.ui.printg("InputError: Required input for FixedDuration command!");
            throw new Duke.DukeException("InputError: Required input for FixedDuration command!");
        }
    }

    /**
     * Returns the number of hours needed for the fixed duration task.
     * The fixed duration task has a duration in both hours and minutes.
     *
     * @param restOfInput User input string.
     * @return Number of hours needed to complete the fixed duration task.
     * @throws Duke.DukeException If no input for hour is found.
     */
    public int getHour(String restOfInput) throws Duke.DukeException {
        if (restOfInput.contains(TOKEN_HOUR)) {
            int startPoint = restOfInput.indexOf(TOKEN_HOUR);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                duke.ui.printg("HourInputError: Required hour input for FixedDuration command!");
                throw new Duke.DukeException("HourInputError: Required hour input for FixedDuration command!");
            }
            return scanner.nextInt();
        } else {
            duke.ui.printg("HourInputError: Required hour input for FixedDuration command!");
            throw new Duke.DukeException("HourInputError: Required hour input for FixedDuration command!");
        }
    }

    /**
     * Returns the number of minutes needed for the fixed duration task.
     * The fixed duration task has a duration in both hours and minutes.
     *
     * @param restOfInput User input string.
     * @return Number of minutes needed to complete the fixed duration task.
     * @throws Duke.DukeException If no input for minute is found.
     */
    public int getMinute(String restOfInput) throws Duke.DukeException {
        if (restOfInput.contains(TOKEN_MINUTE)) {
            int startPoint = restOfInput.indexOf(TOKEN_MINUTE);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                duke.ui.printg("MinInputError: Required minute input for FixedDuration command!");
                throw new Duke.DukeException("MinInputError: Required minute input for FixedDuration command!");
            }
            return scanner.nextInt();
        } else {
            duke.ui.printg("MinInputError: Required minute input for FixedDuration command!");
            throw new Duke.DukeException("MinInputError: Required minute input for FixedDuration command!");
        }
    }
}

