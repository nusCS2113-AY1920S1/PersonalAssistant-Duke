
package javacake.commands;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

import java.util.Date;
import java.util.List;

/**
 * EDIT commands should be of the following format:
 * edit TASKINDEX  NEWDATE.
 */

public class EditCommand extends Command {

    public EditCommand(String str) {
        type = CmdType.EDIT;
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
            int num = Integer.parseInt(buffer[1]);
            --num;
            if (!buffer[2].equals("/by")) {
                throw new CakeException("[!] Wrong format\nPlease input:\n'deadline TASK /by TASK_DATE'");
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
            Date newDate;
            boolean isInsideData = false;
            try {
                Parser parser = new Parser();
                List<DateGroup> groups = parser.parse(newDateString);
                newDate = groups.get(0).getDates().get(0);
                storageManager.storage.getData().get(num).changeDate(newDateString);
                System.out.println("Snoozed to: " + newDateString);
                storageManager.storage.write(storageManager.storage.getData());
                isInsideData = true;
            } catch (Exception e) {
                throw new CakeException("[!] Date cannot be parsed: " + newDateString);
            }
            /*for (int i = 0; i < progressStack.size(); ++i) {
                if (i == num) {
                    progressStack.get(i).changeDate(input.substring(2));
                    String stringBuilder = "Noted. I've rescheduled this task: " + "\n"
                            + progressStack.get(i).getFullString();
                    storage.write(progressStack.getData());
                    ui.showMessage(stringBuilder);
                    isWithinData = true;
                    break;
                }
            }*/
            if (!isInsideData) {
                throw new CakeException("[!] Task number is out of bounds [Edit]");
            }
        } catch (NumberFormatException e) {
            throw new CakeException("[!] Task number is invalid! [Edit]");
        }
        return "";
    }
}
