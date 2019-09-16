package duke.commands;

import duke.parsers.Parser;
import duke.storage.Storage;
import duke.ui.Ui;
import javafx.application.Platform;

/**
 * Class representing a command to exit duke.Duke.
 */
public class ExitCommand extends Command {
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Parser parser, Ui ui, Storage storage) {
        parser.setParserResponse(ui.getByeMessage());
    }
}
