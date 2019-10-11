package Commands;

import Interface.Duke;
import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;

/**
 * Confirms Tentative Schedule
 */
public class ConfirmCommand extends Command {


    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) {
        String out = "Which one would you like to choose?";
        TaskList tentativeDates = Duke.getTentativeDates();
        for (int i = 0; i <tentativeDates.taskListSize();i++){
            out += i+1 + "." + tentativeDates.taskToString(i ) + "\n";
        }
        return out;
    }
}
