package Duke.Commands;

import Duke.DukeException;

import Duke.Storage;
import Duke.TaskList;
import Duke.Ui;
import Duke.Parser;

public class AddCommand extends Command {
    public AddCommand(CmdType cmdType, String str) throws DukeException {
        input = str;
        if (cmdType == CmdType.TODO) {
            if (input.length() == 4)
                throw new DukeException("     ☹ OOPS!!! The description of a todo cannot be empty.");
            type = cmdType;
        } else if (cmdType == CmdType.EVENT) {
            if (input.length() == 5)
                throw new DukeException("     ☹ OOPS!!! The description of a event cannot be empty.");
            type = cmdType;
        } else if (cmdType == CmdType.DEADLINE) {
            if (input.length() == 8)
                throw new DukeException("     ☹ OOPS!!! The description of a deadline cannot be empty.");
            type = cmdType;
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        switch (type) {
            case TODO:
                ui.showMessage(Parser.runTodo(tasks.getData(), input, 0));
                storage.write(tasks.getData());
                break;
            case DEADLINE:
                ui.showMessage(Parser.runDeadline(tasks.getData(), input, 0));
                storage.write(tasks.getData());
                break;
            case EVENT:
                ui.showMessage(Parser.runEvent(tasks.getData(), input, 0));
                storage.write(tasks.getData());
                break;
            default:
                throw new DukeException("     [Unknown COMMAND TYPE]");
        }
    }
}
