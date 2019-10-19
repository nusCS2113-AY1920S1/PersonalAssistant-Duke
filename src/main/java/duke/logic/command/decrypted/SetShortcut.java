//package duke.logic.command.decrypted;
//
//import duke.logic.command.Command;
//import duke.logic.command.Undoable;
//import duke.commons.DukeException;
//import duke.storage.decrpted.BakingList;
//import duke.storage.decrpted.Storage;
//import duke.ui.Ui;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * A command to set a new shortcut. A shortcut is a set of user-defined commands that can be executed by
// * entering the shortcut keyword.
// */
//public class SetShortcut extends Command implements Undoable {
//    private String name;
//    private ExecuteShortcut unmodifiedExecuteShortCutCommand;
//    private List<String> lines = new ArrayList<>();
//    private final Set<String> reservedWords = Set.of(
//            "undo", "redo", "order", "recipe", "add", "remove", "edit", "done"
//    );
//
//
//    /**
//     * Class constructor.
//     *
//     * @param line A line of user input.
//     * @throws DukeException if shortcut name contains the application's reserved words.
//     */
//    public SetShortcut(String line) throws DukeException {
//        splitIntoLines(line);
//        if (containsReservedWords(name)) {
//            throw new DukeException("Cannot contain reserved words");
//        }
//    }
//
//    @Override
//    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        if (unmodifiedExecuteShortCutCommand == null) {
//            System.out.println("here");
//            bakingList.getShortcuts().remove(name);
//        } else {
//            bakingList.getShortcuts().put(name, unmodifiedExecuteShortCutCommand);
//        }
//        storage.serialize(bakingList);
//        ui.showMessage("Undo: Set shortcut");
//    }
//
//    @Override
//    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        execute(bakingList, storage, ui);
//        ui.showMessage("Redo: Set shortcut");
//    }
//
//    @Override
//    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        ExecuteShortcut executeShortcutCommand = new ExecuteShortcut(lines);
//        if (bakingList.getShortcuts().containsKey(name)) {
//            unmodifiedExecuteShortCutCommand = bakingList.getShortcuts().get(name);
//        }
//        bakingList.getShortcuts().put(name, executeShortcutCommand);
//        ui.showMessage("Shortcut added");
//        storage.serialize(bakingList);
//    }
//
//    private void splitIntoLines(String line) throws DukeException {
//        Pattern pattern = Pattern.compile("^\\w+\\s+(\\w+)\\s+\\\"(.*?)\\\"");
//        Matcher matcher = pattern.matcher(line);
//        if (!matcher.find()) {
//            throw new DukeException("Please enter valid parameters");
//        }
//
//        this.name = matcher.group(1);
//        this.lines = Arrays.asList(matcher.group(2).split(";"));
//    }
//
//    private boolean containsReservedWords(String str) {
//        return reservedWords.contains(str.strip());
//    }
//}
