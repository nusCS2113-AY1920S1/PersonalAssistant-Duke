package javacake.commands;

import javacake.exceptions.DukeException;
import javacake.Logic;
import javacake.storage.Profile;
import javacake.storage.StorageManager;
import javacake.storage.TaskList;
import javacake.ui.Ui;
import javacake.storage.Storage;

import java.io.File;

public class AddCommand extends Command {
    /**
     * Constructor for Adding of commands.
     * @param str Input string
     */
    public AddCommand(String str) {
        input = str;
        type = CmdType.DEADLINE;
    }

    /**
     * Execute addition of tasks.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @throws DukeException Shows error when deletion is not possible
     * @return
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws DukeException {
        String output = TaskList.runDeadline(storageManager.storage.currentTaskData.getData(),
                input, TaskList.TaskState.NOT_DONE);
        Storage.generateFolder(new File("data/tasks/"));
        storageManager.storage.write(storageManager.storage.currentTaskData.getData());
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
