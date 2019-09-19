package duke.commands;

import duke.TaskList;
import duke.Storage;
import duke.Ui;
import duke.items.DateTime;
import duke.items.Task;

import java.util.ArrayList;

public class ViewScheduleCommand extends Command {

    ArrayList<Task> scheduled_tasks;
    DateTime day;

    public ViewScheduleCommand(CommandType type, DateTime day) {
        super(type);
        this.day = day;
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        ArrayList<Task> tasks = list.getTaskList();
        for (int i = 0 ; i < list.getSize() ; i++) {
        }
    }
}
