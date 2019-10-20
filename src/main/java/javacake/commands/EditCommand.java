
package javacake.commands;

import javacake.exceptions.DukeException;
import javacake.ProgressStack;
import javacake.storage.Profile;
import javacake.ui.Ui;
import javacake.storage.Storage;

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
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        input = input.substring(5);
        try {
            int num = Integer.parseInt(input.substring(0,1));
            --num;
            boolean isWithinData = false;
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
            if (!isWithinData) {
                throw new DukeException("Task number is out of bounds [Edit]");
            }
        } catch (NumberFormatException e) {
            throw new DukeException("Task number is invalid! [Edit]");
        }
        return "";
    }
}
