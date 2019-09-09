package Duke.Commands;

import Duke.DukeException;
import Duke.Storage;
import Duke.TaskList;
import Duke.Ui;

public class DeleteCommand extends Command {
    public DeleteCommand (String str) {
        type = CmdType.DELETE;
        input = str;
    }

    /**
     * Execute deletion of task in tasks
     * @param tasks TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @throws DukeException Shows error when deletion is not possible
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        input = input.substring(7);
        try {
            int num = Integer.parseInt(input);
            --num;
            boolean isWithinData = false;
            for (int i = 0; i < tasks.size(); ++i) {
                if (i == num) {
                    String stringBuilder = "Noted. I've removed this task: " + "\n" +
                            "  " + tasks.get(i).toString().substring(0, 3) +
                            "[" + tasks.get(i).getStatusIcon() + "] " +
                            tasks.get(i).toString().substring(3);
                    tasks.remove(i);
                    storage.write(tasks.getData());
                    ui.showMessage(stringBuilder);
                    ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
                    isWithinData = true;
                    break;
                }
            }
            if (!isWithinData) {
                throw new DukeException("Task number is out of bounds [Delete]");
            }
        } catch (NumberFormatException e) {
            throw new DukeException("Task number is invalid! [Delete]");
        }
    }
}
