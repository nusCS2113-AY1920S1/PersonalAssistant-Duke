package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;
import duke.task.EventTask;

/**
 * Class responsible for executing Command to create a new Event task.
 */
public class NewEventCommand extends NewTimedTaskCommand {
    /**
     * Creates a new NewEventCommand, setting the parameters for its inherited methods.
     */
    public NewEventCommand() {
        argc = 2;
        delim = "/at";
        invalidArgMsg = "Invalid event! I need to know the date and time that it is /at.";
        emptyArgMsg = "You didn't tell me anything about the event!";
    }

    @Override
    public void parse(String inputStr) throws DukeException {
        super.parse(inputStr);
        if (argv[0].length() == 0) {
            throw new DukeException("Task description cannot be empty!");
        }
    }

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        super.execute(ctx);
        String addStr = ctx.taskList.addTask(new EventTask(argv[0], taskDateTime));
        ctx.storage.writeTaskFile(ctx.taskList.getFileStr());
        ctx.ui.print(ctx.taskList.getAddReport(System.lineSeparator() + "  " + addStr, 1));
    }

    @Override
    public String silentExecute(DukeContext ctx) {
        return ctx.taskList.addTask(new EventTask(argv[0], taskDateTime));
    }
}
