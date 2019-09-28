package duke.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import duke.commons.DukeException;
import duke.parser.Parser;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.List;

public class ExecuteShortcutCommand extends UndoableCommand {

    private List<String> lines = new ArrayList<>();

    public ExecuteShortcutCommand(@JsonProperty("lines") List<String> lines) {
        this.lines = lines;
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        for (String line : lines) {
            //System.out.println("[" + line + "]");
            Command command = Parser.getCommand(line.strip(), bakingList.getShortcuts());
            command.execute(bakingList, storage, ui);
            //System.out.println("Finished [" + line + "]");
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
