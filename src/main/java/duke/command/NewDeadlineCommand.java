package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import duke.task.DeadlineTask;

/**
 * Class responsible for executing Command to create a new Deadline task.
 */
public class NewDeadlineCommand extends NewTimedTaskCommand {
    /**
     * Creates a new NewDeadlineCommand, setting the parameters for its inherited methods.
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
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String addStr = core.taskList.addTask(new DeadlineTask(argv[0], taskDateTime));
        core.storage.writeTaskFile(core.taskList.getFileStr());
        core.ui.print(core.taskList.getAddReport(System.lineSeparator() + "  " + addStr, 1));
    }

    @Override
    public String silentExecute(DukeCore core) {
        return core.taskList.addTask(new DeadlineTask(argv[0], taskDateTime));
    }
}
