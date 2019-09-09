package Duke.Commands;

import Duke.DukeException;
import Duke.Storage;
import Duke.TaskList;
import Duke.Ui;

public class DoneCommand extends Command {
    public DoneCommand(String str) {
        type = CmdType.DONE;
        input = str;
    }

    /**
     * Execute checking of task in tasks
     * @param tasks TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @throws DukeException Shows error when task number is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        input = input.substring(5);
        try {
            int num = Integer.parseInt(input);
            --num;
            boolean isInsideData = false;
            for (int i = 0; i < tasks.size(); ++i) {
                if (i == num) {
                    if (tasks.get(i).isDone()) {
                        ui.showMessage(tasks.get(i).toString().substring(3) + " is already done!");
                        isInsideData = true;
                        continue;
                    }
                    tasks.get(i).markAsDone();
                    storage.write(tasks.getData());
                    ui.showMessage("Nice! I've marked this task as done: ");
                    ui.showMessage("    [✓] " + tasks.get(i).toString().substring(3));
                    isInsideData = true;
                    break;
                }
            }
            if (!isInsideData) {
                ui.showError("Task number is out of bounds! [Done]");
            }
        } catch (NumberFormatException e) {
            throw new DukeException("Not a valid Task Number!");
        }
    }
}
