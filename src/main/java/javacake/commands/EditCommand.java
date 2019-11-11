
package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

/**
 * EDIT commands should be of the following format:
 * snooze TASK_INDEX /by NEW_DATE.
 */

public class EditCommand extends Command {

    public EditCommand(String str) {
        type = CmdType.SNOOZE;
        input = str;
    }

    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        if (input.length() <= 6) {
            throw new CakeException("[!] No deadline mentioned!");
        }
        input = input.substring(6);
        try {
            System.out.println("Task: " + input);
            String[] buffer = input.split("\\s+");
            System.out.println("Task num: " + buffer[1]);
            int taskIndex = Integer.parseInt(buffer[1]);
            --taskIndex;
            if (!buffer[2].equals("/by")) {
                throw new CakeException("[!] Wrong format\nPlease input:\n'deadline TASK /by TASK_DATE'");
            }
            if (taskIndex >= storageManager.storage.getData().size() || taskIndex < 0) {
                throw new CakeException("[!] Task number is out of bounds!\n"
                        + "Task number must be <= '"
                        + storageManager.storage.getData().size()
                        + "'!");
            }
            String newDateString = "";
            for (int i = 0; i < buffer.length; ++i) {
                if (i <= 2) {
                    continue;
                }
                newDateString += buffer[i];
                if (i != buffer.length - 1) {
                    newDateString += " ";
                }
            }

            boolean isInsideData = false;
            try {
                storageManager.storage.getData().get(taskIndex).changeDate(newDateString);
                System.out.println("Snoozed to: " + newDateString);
                storageManager.storage.write(storageManager.storage.getData());
                isInsideData = true;
            } catch (Exception e) {
                throw new CakeException("[!] Date cannot be parsed: " + newDateString);
            }
            if (!isInsideData) {
                throw new CakeException("[!] Task number is out of bounds [Edit]");
            }
        } catch (NumberFormatException e) {
            throw new CakeException("[!] Task number is invalid! [Edit]");
        }
        return "";
    }
}
