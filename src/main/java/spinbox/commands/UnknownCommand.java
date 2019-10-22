package spinbox.commands;

import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Storage;
import spinbox.containers.lists.TaskList;
import spinbox.Ui;

public class UnknownCommand extends Command {

    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui, boolean gui) throws SpinBoxException {
        throw new InputException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}
