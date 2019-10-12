package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.Deadline;
import compal.model.tasks.TaskList;
import compal.model.tasks.Task;

import java.text.ParseException;
import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_INVALID_DATE_TIME_INPUT;
import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;

/**
 * Executes user command "deadline".
 */
public class DeadlineCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs DeadlineCommand object.
     *
     * @param d Compal.
     */
    public DeadlineCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Adds a Deadline into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Compal.DukeException If user input after "deadline" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException, ParseException {
        Scanner scanner = new Scanner(userIn);
        if (scanner.hasNext()) {
            String event = scanner.next();
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            Task.Priority priority = getPriority(restOfInput);
            String date = getDate(restOfInput);
            String endTime = getEndTime(restOfInput);
            if (!isValidDateAndTime(date, endTime)) {
                compal.ui.printg(MESSAGE_INVALID_DATE_TIME_INPUT);
                throw new Compal.DukeException(MESSAGE_INVALID_DATE_TIME_INPUT);
            }
            taskList.addTask(new Deadline(description, priority, date, endTime));
            int arrSize = taskList.arrlist.size() - 1;
            String descToPrint = taskList.arrlist.get(arrSize).toString();
            compal.ui.printg(descToPrint);
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }
}
