package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import duke.task.ToDoTask;

public class NewToDoCommand extends ArgCommand {
    /**
     * Creates a new NewToDoCommand, setting the parameters for its inherited methods.
     */
    public NewToDoCommand() {
        emptyArgMsg = "Task description cannot be empty!";
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String addStr = core.taskList.addTask(new ToDoTask(arg));
        core.storage.writeTaskFile(core.taskList.getFileStr());
        core.ui.print(core.taskList.getAddReport(System.lineSeparator() + "  " + addStr, 1));
    }
}
