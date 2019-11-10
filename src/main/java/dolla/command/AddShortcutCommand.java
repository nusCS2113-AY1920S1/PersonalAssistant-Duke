package dolla.command;


import dolla.command.action.Redo;
import dolla.command.action.Undo;
import dolla.model.DollaData;
import dolla.model.Record;
import dolla.model.Shortcut;
import dolla.model.ShortcutList;
import dolla.ui.Ui;

import java.util.ArrayList;

//@@author yetong1895
public class AddShortcutCommand extends Command {
    private int index;
    private String mode;

    public AddShortcutCommand(String index, String mode) {
        this.index = Integer.parseInt(index);
        this.mode = mode;
    }

    @Override
    public void execute(DollaData dollaData) {
        ArrayList<Record> entryList = dollaData.getRecordList(MODE_ENTRY);
        ArrayList<Record> shortcutList = dollaData.getRecordList(MODE_SHORTCUT);
        if (mode.equals(MODE_SHORTCUT)) {
            ShortcutList shortcutListObj = (ShortcutList) dollaData.getRecordListObj(MODE_SHORTCUT);
            Undo.addToStateList(mode,shortcutListObj.get());
            Redo.clearRedoState(mode);
        }
        try {
            Record record = entryList.get(index);
            Record shortcut = new Shortcut(record.getType(),record.getAmount(),record.getDescription());
            dollaData.addToRecordList(MODE_SHORTCUT, shortcut);
            Ui.echoAddRecord(shortcut);
        } catch (IndexOutOfBoundsException e) {
            if (mode.equals(MODE_SHORTCUT)) {
                Ui.printNumberOfRecords(shortcutList.size());
            } else {
                Ui.printNumberOfRecords(entryList.size());
            }
        }
    }

    @Override
    public String getCommandInfo() {
        String command = "create shortcut ";
        return command + index + " in mode " + mode;
    }
}
