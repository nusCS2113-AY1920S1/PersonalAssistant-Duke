package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class responsible for executing Command to view the scheduled Tasks on a specified date.
 *
 * @author Pang Jia Jun Vernon
 */
public class ViewCommand extends ArgCommand {
    private static final DateTimeFormatter PAT_DATE = DateTimeFormatter.ofPattern("d/M/yyyy");

    private LocalDate date;

    /**
     * Creates a new Command object that can be executed to view the scheduled Tasks on a specified date.
     */
    public ViewCommand() {
        emptyArgMsg = "Please give me a date to work with!";
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        String scheduleStr = "Here are your tasks for " + arg + ":";
        core.ui.print(scheduleStr + core.taskList.listSchedule(date));
    }
}
