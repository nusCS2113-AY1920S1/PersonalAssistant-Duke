package javacake.unused;

import javacake.commands.Command;
import javacake.exceptions.CakeException;
import javacake.Logic;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class FindCommand extends Command {
    public FindCommand(String str) {
        type = CmdType.FIND;
        input = str;
    }

    /**
     * Executes finding tasks corresponding to the keyword in 'input'.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @throws CakeException Shows error when unable to access list
     * @return
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        input = input.substring(5);
        /*if (progressStack.size() > 0) {
            boolean isInside = false;
            boolean isStarting = true;
            for (int i = 0; i < progressStack.size(); ++i) {
                if (progressStack.get(i).toString().contains(input)) {
                    if (isStarting) {
                        isStarting = false;
                        ui.showMessage("Here are the matching tasks in your list:");
                    }
                    int temp = i + 1;
                    ui.showMessage(temp + ". " + progressStack.get(i).getFullString());
                    isInside = true;
                }
            }
            if (!isInside) {
                ui.showError("Keyword not in List");
            }
        } else {
            ui.showError("Empty List!");
        }*/
        return "";
    }
}
