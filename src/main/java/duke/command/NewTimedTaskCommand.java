package duke.command;

import duke.exception.DukeException;
import duke.task.TimedTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Abstract class for Tasks that need to store a LocalDateTime object as part of their description.
 */
public abstract class NewTimedTaskCommand extends ArgCommand {

    protected LocalDateTime taskDateTime;

    /**
     * Split the input string into the elements of the argv array using MultiArgCommand's parse, then load the task
     * with argv[0] as the description and argv[1] as the date and time in the TimedTask data format.
     *
     * @throws DukeException If task description is empty, or if date and time are invalid.
     * @see ArgCommand
     */
    @Override
    public void parse(String inputStr) throws DukeException {
        super.parse(inputStr);
        if (argv[0].length() == 0) {
            argv = null;
            throw new DukeException("The task description cannot be empty!");
        }

        try {
            taskDateTime = LocalDateTime.parse(argv[1], TimedTask.getPatDatetime());
        } catch (DateTimeParseException excp) {
            throw new DukeException("Date and time must be given as e.g. "
                    + LocalDateTime.now().format(TimedTask.getPatDatetime()) + ".");
        }
    }
}
