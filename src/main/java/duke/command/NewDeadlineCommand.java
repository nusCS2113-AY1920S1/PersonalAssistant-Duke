package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;
import duke.task.DeadlineTask;

/**
 * Class responsible for executing Command to create a new Deadline task.
 */
public class NewDeadlineCommand extends NewTimedTaskCommand {
    /**
     * Creates a new Command object that can be executed to create a new Deadline task.
     */
    public NewDeadlineCommand() {
        argc = 2;
        delim = "/by";
        invalidArgMsg = "Invalid deadline! I need to know the date and time that it is due /by.";
        emptyArgMsg = "You didn't tell me anything about the deadline!";
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
        String addStr = ctx.taskList.addTask(new DeadlineTask(argv[0], taskDateTime));
        ctx.storage.writeTaskFile(ctx.taskList.getFileStr());
        ctx.ui.print(ctx.taskList.getAddReport(System.lineSeparator() + "  " + addStr, 1));
    }

    @Override
    public String silentExecute(DukeContext ctx) {
        return ctx.taskList.addTask(new DeadlineTask(argv[0], taskDateTime));
    }
}
