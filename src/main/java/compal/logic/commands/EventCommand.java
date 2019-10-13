package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.text.ParseException;
import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_INVALID_TIME_RANGE;
import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;
import static compal.commons.Messages.MESSAGE_INVALID_DATE_TIME_INPUT;

/**
 * Executes user command "event".
 */
public class EventCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs EventCommand object.
     *
     * @param d Compal.
     */
    public EventCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     *
     * Adds a single ToDo to the tasklist and print out confirmation for the user.
     *
     * @param userIn Entire String input by the user.
     */

    /**
     * Adds an Event into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Compal.DukeException If user input after "event" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException, ParseException {
        Scanner scanner = new Scanner(userIn);
        String event = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            Task.Priority priority = getPriority(restOfInput);
            String date = getDate(restOfInput);
            String startTime = getStartTime(restOfInput);
            String endTime = getEndTime(restOfInput);

            if (!isValidDateAndTime(date, startTime)) {
                compal.ui.printg(MESSAGE_INVALID_DATE_TIME_INPUT);
                throw new Compal.DukeException(MESSAGE_INVALID_DATE_TIME_INPUT);
            }

            if (Integer.parseInt(startTime) > Integer.parseInt(endTime)) {
                compal.ui.printg(MESSAGE_INVALID_TIME_RANGE);
                throw new Compal.DukeException(MESSAGE_INVALID_TIME_RANGE);
            }

            taskList.addTask(new Event(description, priority, date, startTime, endTime));
            int arrSize = taskList.arrlist.size() - 1;
            String descToPrint = taskList.arrlist.get(arrSize).toString();
            compal.ui.printg(descToPrint);
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }
}
