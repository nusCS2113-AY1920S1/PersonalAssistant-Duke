package Duke.Commands;

import Duke.DukeException;
import Duke.Storage;
import Duke.TaskList;
import Duke.Ui;

public class FindCommand extends Command {
    public FindCommand (String str) {
        type = CmdType.FIND;
        input = str;
    }

    /**
     * Executes finding tasks corresponding to the keyword in 'input'
     * @param tasks TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @throws DukeException Shows error when unable to access list
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        input = input.substring(5);
        if (tasks.size() > 0) {
            boolean isInside = false;
            boolean isStarting = true;
            for (int i = 0; i < tasks.size(); ++i) {
                if (tasks.get(i).toString().contains(input)) {
                    if (isStarting) {
                        isStarting = false;
                        ui.showMessage("Here are the matching tasks in your list:");
                    }
                    int temp = i + 1;
                    String stringBuilder = temp + "." +
                            tasks.get(i).toString().substring(0, 3) +
                            "[" + tasks.get(i).getStatusIcon() + "] " +
                            tasks.get(i).toString().substring(3);
                    ui.showMessage(stringBuilder);
                    isInside = true;
                }
            }
            if (!isInside) {
                ui.showError("Keyword not in List");
            }
        } else {
            ui.showError("Empty List!");
        }
    }
}
