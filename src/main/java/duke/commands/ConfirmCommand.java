package duke.commands;

import duke.*;
import duke.tasks.TentativeEvent;

public class ConfirmCommand extends Command {
    public ConfirmCommand(String str) {
        input = str;
        type = CmdType.CONFIRM;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        ui.showMessage(Parser.runConfirm(tasks.getData(), input, 0));
        storage.write(tasks.getData());
        // null the tentative event object
        TentativeEvent.clearTentativeEvent();
    }
}
