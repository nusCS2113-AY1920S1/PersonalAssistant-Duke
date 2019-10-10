package duke.command;

import java.util.List;

import duke.tasks.TaskList;
import duke.ui.Ui;
import duke.storage.FileHandling;
import duke.exceptions.DukeException;
import duke.tasks.Deadline;

public class AddDeadlineCommand extends Command {
    private List<String> splitInput;

    public AddDeadlineCommand(List<String> splitInput) {
        this.splitInput = splitInput;
    }

    @Override
    public void execute(TaskList tasks,Ui ui,FileHandling storage)throws DukeException {
        int i;
        int k = 0;
        String split1 = "";
        String split2 = "";
        if (splitInput.size() == 1) {
            throw new DukeException(" OOPS! The description of deadline cannot be empty");
        }
        for (i = 1; i < splitInput.size(); i++) {
            if (splitInput.get(i).equals("/by")) {
                k = 1;
            } else if (k == 0) {
                split1 += splitInput.get(i) + " ";
            } else {
                split2 += splitInput.get(i) + " ";
            }
        }
        if (k == 0) {
            throw new DukeException(" Please make sure that you have entered \"/by\" "
                    + "to separate task and time");
        } else if (split2.trim().length() == 0) {
            throw new DukeException(" Please enter the time frame");
        }
        tasks.addTask(new Deadline(split1.trim(), split2.trim()));
        String taskA = tasks.getTask(tasks.numTasks() - 1).toString();
        ui.printAddTask(tasks.getAllTasks(),taskA);
        storage.saveData(tasks);
    }
}