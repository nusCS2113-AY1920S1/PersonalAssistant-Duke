package duke.command;

import duke.commons.DukeException;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddShortcutCommand extends UndoableCommand {
    private String name;
    private List<String> lines = new ArrayList<>();

    public AddShortcutCommand(String line) throws DukeException {
        parse(line);
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        ExecuteShortcutCommand executeShortcutCommand = new ExecuteShortcutCommand(lines);
        bakingList.getShortcuts().put(name, executeShortcutCommand);
        storage.serialize(bakingList);
        ui.showMessage("Shortcut added");
    }

    private void parse(String line) throws DukeException {
        Pattern pattern = Pattern.compile("^\\w+\\s+(\\w+)\\s+\\\"(.*?)\\\"");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new DukeException("Please enter valid parameters");
        }

        this.name = matcher.group(1);
        this.lines = Arrays.asList(matcher.group(2).split(";"));
    }
}
