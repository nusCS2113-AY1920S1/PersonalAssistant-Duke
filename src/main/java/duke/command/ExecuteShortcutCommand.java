package duke.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import duke.commons.DukeException;
import duke.parser.Parser;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

/**
 * A command to execute a user-defined set of commands.
 */
public class ExecuteShortcutCommand extends UndoableCommand {

    private List<String> lines = new ArrayList<>();
    private List<Command> commands = new ArrayList<>();

    /**
     * Class constructor.
     *
     * @param lines A list containing user inputs.
     *              Each entry represents a line of user input is to be converted to a <code>Command</code> object.
     * @throws DukeException if fails to convert any line into a <code>Command</code> object.
     */
    public ExecuteShortcutCommand(@JsonProperty("lines") List<String> lines) throws DukeException {
        this.lines = lines;
        for (String line : lines) {
            try {
                Command command = Parser.getCommand(line.strip(), new HashMap<String, ExecuteShortcutCommand>());
                commands.add(command);
            } catch (DukeException e) {
                //TODO: Improve catching
                throw new DukeException("Error when adding shortcut: " + e.getMessage());
            }
        }

    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        ListIterator<Command> itr = commands.listIterator(commands.size());
        while (itr.hasPrevious()) {
            Command command = itr.previous();
            if (command instanceof UndoableCommand) {
                ((UndoableCommand) command).undo(bakingList, storage, ui);
            }
        }
        ui.showMessage("Undo: Execute shortcut");
    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        execute(bakingList, storage, ui);
        ui.showMessage("Redo: Execute shortcut");
    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        for (Command command : commands) {
            command.execute(bakingList, storage, ui);
        }
        ui.showMessage("Shortcut executed successfully");
    }

    /**
     * Returns a list containing user inputs.
     * @return A list containing user inputs.
     *         Each entry represents a line of user input is to be converted to a <code>Command</code> object.
     */
    public List<String> getLines() {
        return lines;
    }

    /**
     * Sets a set of inputs to be converted to <code>Command</code> objects.
     * @param lines A list containing user inputs.
     *        Each entry represents a line of user input is to be converted to a <code>Command</code> object.
     */
    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
