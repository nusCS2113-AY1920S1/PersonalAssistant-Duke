package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import duke.task.Reminder;
import duke.task.TimedTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Class responsible for executing Command to attach a new Reminder to a Task.
 *
 * @author Pang Jia Jun Vernon
 */
public class NewReminderCommand extends ArgCommand {
    private LocalDateTime reminderDateTime;

    /**
     * Creates a new Command object that can be executed to set a Reminder for a Task, updating its inherited
     * parameters.
     */
    public NewReminderCommand() {
        argc = 2;
        delim = "/after";
        invalidArgMsg = "Invalid reminder! I need to know the date and time to remind you /after.";
        emptyArgMsg = "You didn't tell me anything about the reminder!";
    }

    @Override
    public void parse(String inputStr) throws DukeException {
        super.parse(inputStr);
        if (argv[0].length() == 0) {
            argv = null;
            throw new DukeException("Description of reminder cannot be empty!");
        }

        try {
            reminderDateTime = LocalDateTime.parse(argv[1], TimedTask.getPatDatetime());
        } catch (DateTimeParseException excp) {
            throw new DukeException("Date and time must be given as e.g. "
                    + LocalDateTime.now().format(TimedTask.getPatDatetime()) + ".");
        }
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String remindStr = "Roger! I've set a reminder for this task." + System.lineSeparator()
                + "  " + core.taskList.setReminder(argv[0], new Reminder(reminderDateTime));
        core.storage.writeTaskFile(core.taskList.getFileStr());
        core.ui.print(remindStr);
    }
}
