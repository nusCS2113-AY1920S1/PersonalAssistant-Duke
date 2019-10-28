package javacake.commands;

import javacake.Logic;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.StorageManager;
import javacake.ui.Ui;
import javacake.storage.Storage;

public class DeleteCommand extends Command {
    public DeleteCommand(String str) {
        type = CmdType.DELETE;
        input = str;
    }

    /**
     * Execute deletion of task in tasks.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @throws DukeException Shows error when deletion is not possible
     * @return
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws DukeException {
        input = input.substring(7);
        /*try {
            int num = Integer.parseInt(input);
            --num;
            boolean isWithinData = false;
            for (int i = 0; i < progressStack.size(); ++i) {
                if (i == num) {
                    String stringBuilder = "Noted. I've removed this task: " + "\n      "
                            + progressStack.get(i).getFullString() + "\nNow you have " + (progressStack.size() - 1)
                            + " tasks in the list.";
                    progressStack.remove(i);
                    storage.write(progressStack.getData());
                    ui.showMessage(stringBuilder);
                    isWithinData = true;
                    break;
                }
            }
            if (!isWithinData) {
                throw new DukeException("Task number is out of bounds [Delete]");
            }
        } catch (NumberFormatException e) {
            throw new DukeException("Task number is invalid! [Delete]");
        }*/
        return "";
    }
}
