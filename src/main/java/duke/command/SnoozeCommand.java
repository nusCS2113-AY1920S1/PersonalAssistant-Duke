package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;
import duke.task.TimedTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class SnoozeCommand extends MultiArgCommand {
    public SnoozeCommand(){ argc = 2;
        delim = " ";
        invalidArgMsg = "Snooze statement most specify the number of the task to be snoozed\nfollowed by the new date and time given as e.g. snooze 1 01/02/2019 1800";
        emptyArgMsg = "Empty snooze statement";
    }

    @Override
    public void parse(String inputStr) throws DukeException {
        super.parse(inputStr);
        if (argv[0].length() == 0) {
            throw new DukeException("The snooze statement cannot be empty");
        }
    }

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        int idx = ctx.taskList.getTaskIdx(argv[0]);
        String newDateTime = arg.substring(argv[0].length() + 1 ); // +1 to exclude the task number from DateTime
        LocalDateTime datetime;
        try {
            datetime = LocalDateTime.parse(newDateTime, TimedTask.getPatDatetime());
        } catch (DateTimeParseException excp) {
            throw new DukeException(invalidArgMsg);
        }
        ctx.ui.print(ctx.taskList.snooze(idx, datetime));
    }
}
