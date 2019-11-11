package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

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
     * @throws CakeException Shows error when task number is invalid
     * @return
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        if (input.length() <= 5) {
            throw new CakeException("[!] No deadline mentioned!");
        }
        input = input.substring(5);
        try {
            int num = Integer.parseInt(input);
            --num;
            System.out.println(num);
            boolean isInsideData = false;
            for (int i = 0; i < storageManager.storage.getData().size(); ++i) {
                if (i == num) {
                    storageManager.storage.getData().get(i).markAsDone();
                    storageManager.storage.write(storageManager.storage.getData());
                    isInsideData = true;
                    break;
                }
            }

            if (!isInsideData) {
                ui.showError("Task number is out of bounds! [Done]");
                throw new CakeException("[!] Not a valid Task Number!");
            }
        } catch (NumberFormatException e) {
            throw new CakeException("[!] Not a valid Task Number!");
        }
        return "";
    }
}
