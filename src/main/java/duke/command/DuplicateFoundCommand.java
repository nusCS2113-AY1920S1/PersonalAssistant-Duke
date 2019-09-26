package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;

public class DuplicateFoundCommand extends Command{
    @Override
    public void execute(TaskList items, Ui ui) {
        ui.showDuplicateMsg();
    }

    @Override
    public String executeGui(TaskList items, Ui ui) {
        return "";
    }

    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException {
    }
}