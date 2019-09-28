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

public class ExecuteShortcutCommand extends UndoableCommand {

    private List<String> lines = new ArrayList<>();
    private List<Command> commands = new ArrayList<>();

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

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
