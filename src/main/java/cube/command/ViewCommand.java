package cube.command;

import java.util.Date;

import cube.exception.DukeException;
import cube.task.Task;
import cube.task.TaskList;
import cube.ui.Message;
import cube.ui.Ui;
import cube.util.Storage;

public class ViewCommand implements Command {
    private Date date;
    public ViewCommand(Date date) {
        this.date = date;
    }

    /**
     * Returns true if no error in the arguments of view command, otherwise throws DukeException.
     *
     * @return true if view is valid.
     * @throws DukeException printout warning when date of the view command is empty.
     */
    private boolean isValid() throws DukeException{
        if (date == null) {
            throw new DukeException(Message.EMPTY_DATE);
        }
        return true;
    }

    /**
     * Always returns false since this is not an exit command.
     *
     * @return false.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Shows the list of tasks that falls within a specific date.
     *
     * @param tasks the list of tasks.
     * @param ui the user interface to output message.
     * @param storage storage of Duke.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (isValid()) {
            TaskList tasksFoundAt = new TaskList();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                Date task_date = task.getDate();
                if (date.equals(task_date)) {
                    tasksFoundAt.add(task);
                }
            }
        }
    }
}
