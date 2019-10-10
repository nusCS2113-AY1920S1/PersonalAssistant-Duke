package duke.command;

import java.util.List;

import duke.exceptions.DukeException;
import duke.storage.FileHandling;
import duke.tasks.DoWithin;
import duke.tasks.Event;
import duke.tasks.TaskList;
import duke.ui.Ui;

public class AddDoWithinCommand extends Command {
    private List<String> splitInput;

    public AddDoWithinCommand(List<String> splitInput) {
        this.splitInput = splitInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, FileHandling storage)throws DukeException {
        int i;
        int k = 0;
        String split1 = "";
        String split2 = "";
        if (splitInput.size() == 1) {
            throw new DukeException(" OOPS! the description for a DoWithin cannot be empty");
        }
        for (i = 1; i < splitInput.size(); i++) {
            if (splitInput.get(i).equals("/between")) {
                k = 1;
            } else if (k == 0) {
                split1 += splitInput.get(i) + " ";
            } else {
                split2 += splitInput.get(i) + " ";
            }
        }
        if (k == 0) {
            throw new DukeException(" Please make sure you have used \"/between\" to separate"
                    + " task and time");
        } else if (split2.trim().length() == 0) {
            throw new DukeException(" Please enter the time frame");
        }
        tasks.addTask(new DoWithin(split1.trim(), split2.trim()));
        String taskA = tasks.getTask(tasks.numTasks() - 1).toString();
        ui.printAddTask(tasks.getAllTasks(),taskA);
        storage.saveData(tasks);
    }
}