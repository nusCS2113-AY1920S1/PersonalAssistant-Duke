package javacake.commands;

import javacake.TaskList;
import javacake.Ui;
import javacake.Storage;
import javacake.DukeException;
import javacake.Parser;
import javacake.tasks.TentativeEvent;

public class ConfirmCommand extends Command {
    public ConfirmCommand(String str) {
        input = str;
        type = CmdType.CONFIRM;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        ui.showMessage(Parser.runConfirm(tasks.getData(), input, Parser.TaskState.NOT_DONE));
        storage.write(tasks.getData());
        // null the tentative event object
        TentativeEvent.clearTentativeEvent();
    }
}
