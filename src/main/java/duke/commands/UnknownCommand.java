package duke.commands;

import duke.exceptions.DukeException;
import duke.exceptions.InputException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;

public class UnknownCommand extends Command {

    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws DukeException {
        throw new InputException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}
