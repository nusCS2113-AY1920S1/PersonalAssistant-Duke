package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import duke.task.FixedDurationTask;

import java.time.Duration;
import java.time.format.DateTimeParseException;

/**
 * Class responsible for executing Command to create a new FixedDuration task.
 */
public class NewFixedDurationCommand extends ArgCommand {

    private Duration period;
    /**
     * Creates a new Command object that can be executed to create a new FixedDuration task.
     * argc will throw an error if the number of arguments is less than 2.
     */

    public NewFixedDurationCommand() {
        argc = 2;
        delim = "(/for)";
        invalidArgMsg = "Invalid event! I need to know the duration it is /for.";
        emptyArgMsg = "You didn't tell me anything about the event!";
    }

    /**
     * Split the input string into the elements of the argv array using MultiArgCommand's parse, then load the task
     * with argv[0] as the description and argv[1] as the period of time in the Duration data format.
     *
     * @throws DukeException If task description is empty, or if duration is invalid.
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
            this.period = Duration.parse(argv[1]);
        } catch (DateTimeParseException excp) {
            throw new DukeException("Date and time must be given as e.g. P3DT3H4M5S parses as"
                                    + "3 Days, 3 Hours, 4 Minutes, 5 Seconds.");
        }
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String addStr = core.taskList.addTask(new FixedDurationTask(argv[0], this.period));
        core.storage.writeTaskFile(core.taskList.getFileStr());
        core.ui.print(core.taskList.getAddReport(System.lineSeparator() + "  " + addStr, 1));
    }
}
