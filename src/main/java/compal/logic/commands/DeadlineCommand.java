package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.tasks.Deadline;
import compal.tasks.TaskList;
import compal.tasks.Task;

import java.util.Scanner;

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
    public void parseCommand(String userIn) throws Compal.DukeException {
        Scanner scanner = new Scanner(userIn);
        if (scanner.hasNext()) {
            String event = scanner.next();
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            Task.Priority priority = getPriority(restOfInput);
            String date = getDate(restOfInput);
            String endTime = getEndTime(restOfInput);
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
