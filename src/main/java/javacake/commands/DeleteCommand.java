package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

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
     * @throws CakeException Shows error when deletion is not possible
     * @return
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        if (input.length() <= 7) {
            throw new CakeException("[!] No deadline mentioned!");
        }
        input = input.substring(7);
        try {
            int num = Integer.parseInt(input);
            --num;
            boolean isInsideData = false;
            /*
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
             */
            for (int i = 0; i < storageManager.storage.getData().size(); ++i) {
                if (i == num) {
                    storageManager.storage.getData().remove(i);
                    storageManager.storage.write(storageManager.storage.getData());
                    isInsideData = true;
                    break;
                }
            }

            if (!isInsideData) {
                ui.showError("Task number is out of bounds! [Done]");
                throw new CakeException("[!] Task number is out of bounds!\n"
                        + "Task number must be <= '"
                        + storageManager.storage.getData().size()
                        + "'!");
            }
        } catch (NumberFormatException e) {
            throw new CakeException("[!] Task number is invalid! [Delete]");
        }
        return "";
    }
}
