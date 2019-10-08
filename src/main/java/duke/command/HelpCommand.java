package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import javafx.event.Event;

import java.io.IOException;
/**
 * Displaying full help instructions that list down all functions and commands available
 */
public class HelpCommand extends Command{
    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Showing all help instructions.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public void execute(TaskList items, Ui ui) {

    }

    @Override
    public String executeGui(TaskList items, Ui ui) {
        String str = Ui.helpRequest(items); //replace with events later on
        return str;
    }

    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException {

    }
}