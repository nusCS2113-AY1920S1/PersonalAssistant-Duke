
package javacake.commands;

import javacake.DukeException;
import javacake.Storage;
import javacake.TaskList;
import javacake.Ui;

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
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        input = input.substring(5);
        try {
            int num = Integer.parseInt(input.substring(0,1));
            --num;
            boolean isWithinData = false;
            for (int i = 0; i < tasks.size(); ++i) {
                if (i == num) {
                    tasks.get(i).changeDate(input.substring(2));
                    String stringBuilder = "Noted. I've rescheduled this task: " + "\n"
                            + tasks.get(i).getFullString();
                    storage.write(tasks.getData());
                    ui.showMessage(stringBuilder);
                    isWithinData = true;
                    break;
                }
            }
            if (!isWithinData) {
                throw new DukeException("Task number is out of bounds [Edit]");
            }
        } catch (NumberFormatException e) {
            throw new DukeException("Task number is invalid! [Edit]");
        }
    }
}
