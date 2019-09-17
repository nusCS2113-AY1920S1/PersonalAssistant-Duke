package duke.commands;

import duke.DukeException;
import duke.Parser;
import duke.Ui;
import duke.Storage;
import duke.TaskList;

public class AddCommand extends Command {
    /**
     * Constructor for Adding of commands.
     * @param cmdType Type of command
     * @param str Input string
     * @throws DukeException Throws exception when empty task
     */
    public AddCommand(CmdType cmdType, String str) throws DukeException {
        input = str;
        if (cmdType == CmdType.TODO) {
            if (input.length() == 4) {
                throw new DukeException("     ☹ OOPS!!! The description of a todo cannot be empty.");
            }
        } else if (cmdType == CmdType.EVENT) {
            if (input.length() == 5) {
                throw new DukeException("     ☹ OOPS!!! The description of a event cannot be empty.");
            }
        } else if (cmdType == CmdType.DEADLINE) {
            if (input.length() == 8) {
                throw new DukeException("     ☹ OOPS!!! The description of a deadline cannot be empty.");
            }
        }
        type = cmdType;
    }

    /**
     * Execute addition of tasks.
     * @param tasks TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @throws DukeException Shows error when deletion is not possible
     */
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
        case DAILY:
            ui.showMessage(Parser.runRecurring(tasks.getData(), input, 0, "daily"));
            storage.write(tasks.getData());
            break;
        case WEEKLY:
            ui.showMessage(Parser.runRecurring(tasks.getData(), input, 0, "weekly"));
            storage.write(tasks.getData());
            break;
        case MONTHLY:
            ui.showMessage(Parser.runRecurring(tasks.getData(), input, 0, "monthly"));
            storage.write(tasks.getData());
            break;
        default:
            throw new DukeException("     [Unknown COMMAND TYPE]");
        }
    }
}
