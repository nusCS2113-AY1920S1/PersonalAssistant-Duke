package javacake.commands;

import javacake.Parser;
import javacake.exceptions.DukeException;
import javacake.ProgressStack;
import javacake.storage.Profile;
import javacake.storage.TaskList;
import javacake.ui.Ui;
import javacake.storage.Storage;

import java.io.File;

public class AddCommand extends Command {
    /**
     * Constructor for Adding of commands.
     * @param str Input string
     * @throws DukeException Throws exception when empty task
     */
    public AddCommand(String str) throws DukeException {
        input = str;
        type = CmdType.DEADLINE;
        if (input.length() == 8) {
            throw new DukeException("The description of a deadline cannot be empty!");
        }

    }

    /**
     * Execute addition of tasks.
     * @param progressStack TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @throws DukeException Shows error when deletion is not possible
     * @return
     */
    @Override
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        String output = TaskList.runDeadline(storage.tasks.getData(), input, TaskList.TaskState.NOT_DONE);
        Storage.generateFolder(new File("data/tasks/"));
        storage.write(storage.tasks.getData());
        return output;


        /*switch (type) {
        case TODO:
            ui.showMessage(Parser.runTodo(progressStack.getData(), input, Parser.TaskState.NOT_DONE));
            storage.write(progressStack.getData());
            break;
        case DEADLINE:
            ui.showMessage(Parser.runDeadline(progressStack.getData(), input, Parser.TaskState.NOT_DONE));
            storage.write(progressStack.getData());
            break;
        case EVENT:
            ui.showMessage(Parser.runEvent(progressStack.getData(), input, Parser.TaskState.NOT_DONE));
            storage.write(progressStack.getData());
            break;
        case DAILY:
            ui.showMessage(Parser.runRecurring(progressStack.getData(), input, Parser.TaskState.NOT_DONE, "daily"));
            storage.write(progressStack.getData());
            break;
        case WEEKLY:
            ui.showMessage(Parser.runRecurring(progressStack.getData(), input, Parser.TaskState.NOT_DONE, "weekly"));
            storage.write(progressStack.getData());
            break;
        case MONTHLY:
            ui.showMessage(Parser.runRecurring(progressStack.getData(), input, Parser.TaskState.NOT_DONE, "monthly"));
            storage.write(progressStack.getData());
            break;
        default:
            throw new DukeException("     [Unknown COMMAND TYPE]");
        }*/
    }
}
