package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

public class FindFreeTimeCommand extends Command {
    int reqFreeHours;

    public FindFreeTimeCommand(CommandType type, int reqFreeHours) {
        super(type);
        this.reqFreeHours = reqFreeHours;
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        list.findFreeHours(reqFreeHours);
    }
}
