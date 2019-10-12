package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.TaskList;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_INVALID_TASK_NUMBER;
import static compal.commons.Messages.MESSAGE_INVALID_TASK;
import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;

/**
 * Executes user command "remind".
 */
public class SetReminderCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs SetReminderCommand object.
     *
     * @param d Compal
     */
    public SetReminderCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Marks task as has reminder based on user task number input and
     * prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Compal.DukeException If user task number input is invalid or missing.
     * @throws ParseException       If date is in invalid format.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException, ParseException {
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        int maxSize = taskList.arrlist.size();
        Date currentDate = java.util.Calendar.getInstance().getTime();
        if (!scanner.hasNextLine()) {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
        String restOfInput = scanner.nextLine();
        int toMark = Integer.parseInt(restOfInput.trim()) - 1;
        if (toMark < 0 || toMark >= maxSize) {
            compal.ui.printg(MESSAGE_INVALID_TASK_NUMBER);
            throw new Compal.DukeException(MESSAGE_INVALID_TASK_NUMBER);
        }
        if (taskList.arrlist.get(toMark).getDate().compareTo(currentDate) < 0) {
            compal.ui.printg(MESSAGE_INVALID_TASK);
            compal.ui.printg(currentDate);
            compal.ui.printg(taskList.arrlist.get(toMark).getDate());
            throw new Compal.DukeException(MESSAGE_INVALID_TASK);
        }
        taskList.arrlist.get(toMark).setHasReminder();
        String desc = taskList.arrlist.get(toMark).toString();
        compal.ui.printg("Okay! I've set a reminder for this task: \n"
                + desc);
        compal.storage.saveCompal(taskList.arrlist);
    }
}
