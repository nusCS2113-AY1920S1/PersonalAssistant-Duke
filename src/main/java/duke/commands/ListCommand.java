package duke.commands;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.enums.CommandType;

public class ListCommand extends Command {
    public ListCommand(CommandType type) {
        super(type);
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        int max = list.getSize();
        String listString = "";
        if (max == 0) {
            ui.print("The list is currently empty.");
            return;
        }

        for (int i = 0; i < max; i++) { //Index starts from 0.
            // TODO: Change to StringBuilder - Raghav
            listString += (i + 1 + ". " + list.getTask(i).toString() + "\n");
        }
        ui.print(listString);
    }
}
