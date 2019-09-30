package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.tasks.FixedDurationTask;
import compal.tasks.Task;
import compal.tasks.TaskList;

import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_INVALID_MINUTE;
import static compal.commons.Messages.MESSAGE_INVALID_TIME_RANGE;
import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;
import static compal.commons.Messages.MESSAGE_MISSING_HOUR;
import static compal.commons.Messages.MESSAGE_MISSING_HOUR_ARG;
import static compal.commons.Messages.MESSAGE_MISSING_MIN;
import static compal.commons.Messages.MESSAGE_MISSING_MIN_ARG;

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
     * @param d Compal.
     */
    public FixedDurationCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Adds a FixedDurationTask into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Compal.DukeException If user input after "fixeddurationtask" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException {
        Scanner scanner = new Scanner(userIn);
        String event = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            Task.Priority priority = getPriority(restOfInput);
            String date = getDate(restOfInput);
            String startTime = getStartTime(restOfInput);
            String endTime = getEndTime(restOfInput);

            if (Integer.parseInt(startTime) > Integer.parseInt(endTime)) {
                compal.ui.printg(MESSAGE_INVALID_TIME_RANGE);
                throw new Compal.DukeException(MESSAGE_INVALID_TIME_RANGE);
            }

            taskList.addTask(new FixedDurationTask(description, priority, date, startTime, endTime));
            int arrSize = taskList.arrlist.size() - 1;
            String descToPrint = taskList.arrlist.get(arrSize).toString();
            compal.ui.printg(descToPrint);
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }

    /**
     * Returns the number of hours needed for the fixed duration task.
     * The fixed duration task has a duration in both hours and minutes.
     *
     * @param restOfInput User input string.
     * @return Number of hours needed to complete the fixed duration task.
     * @throws Compal.DukeException If no input for hour is found.
     */
    public int getHour(String restOfInput) throws Compal.DukeException {
        if (restOfInput.contains(TOKEN_HOUR)) {
            int startPoint = restOfInput.indexOf(TOKEN_HOUR);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                compal.ui.printg(MESSAGE_MISSING_HOUR);
                throw new Compal.DukeException(MESSAGE_MISSING_HOUR);
            }
            return scanner.nextInt();
        } else {
            compal.ui.printg(MESSAGE_MISSING_HOUR_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_HOUR_ARG);
        }
    }

    /**
     * Returns the number of minutes needed for the fixed duration task.
     * The fixed duration task has a duration in both hours and minutes.
     *
     * @param restOfInput User input string.
     * @return Number of minutes needed to complete the fixed duration task.
     * @throws Compal.DukeException If no input for minute is found.
     */
    public int getMinute(String restOfInput) throws Compal.DukeException {
        if (restOfInput.contains(TOKEN_MINUTE)) {
            int startPoint = restOfInput.indexOf(TOKEN_MINUTE);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                compal.ui.printg(MESSAGE_MISSING_MIN);
                throw new Compal.DukeException(MESSAGE_MISSING_MIN);
            }
            int minutes = scanner.nextInt();
            if (minutes >= 0 && minutes < 60) {
                return minutes;
            } else {
                compal.ui.printg(MESSAGE_INVALID_MINUTE);
                throw new Compal.DukeException(MESSAGE_INVALID_MINUTE);
            }
        } else {
            compal.ui.printg(MESSAGE_MISSING_MIN_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_MIN_ARG);
        }
    }
}

