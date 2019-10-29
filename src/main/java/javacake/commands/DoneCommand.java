package javacake.commands;

import javacake.Logic;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.StorageManager;
import javacake.ui.Ui;
import javacake.storage.Storage;

public class DoneCommand extends Command {

    public DoneCommand(String str) {
        type = CmdType.DONE;
        input = str;
    }

    /**
     * Execute checking of task in tasks.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @throws DukeException Shows error when task number is invalid
     * @return
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws DukeException {
        input = input.substring(5);
        try {
            int num = Integer.parseInt(input);
            --num;
            boolean isInsideData = false;
            /*for (int i = 0; i < progressStack.size(); ++i) {
                if (i == num) {
                    if (progressStack.get(i).isDone()) {
                        ui.showMessage(progressStack.get(i).toString().substring(3) + " is already done!");
                        isInsideData = true;
                        continue;
                    }
                    progressStack.get(i).markAsDone();
                    storage.write(progressStack.getData());
                    ui.showMessage("Nice! I've marked this task as done: ");
                    ui.showMessage("    [✗] " + progressStack.get(i).toString().substring(3));
                    isInsideData = true;
                    break;
                }
            }*/
            if (!isInsideData) {
                ui.showError("Task number is out of bounds! [Done]");
            }
        } catch (NumberFormatException e) {
            throw new DukeException("Not a valid Task Number!");
        }
        return "";
    }
}
