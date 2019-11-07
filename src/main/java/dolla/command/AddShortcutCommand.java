package dolla.command;

import dolla.model.DollaData;
import dolla.model.Record;
import dolla.model.Shortcut;
import dolla.exception.DollaException;
import dolla.ui.Ui;

import java.util.ArrayList;

//@@author yetong1895
public class AddShortcutCommand extends Command {
    private int index;

    public AddShortcutCommand(String index) {
        this.index = Integer.parseInt(index);
    }

    @Override
    public void execute(DollaData dollaData) throws DollaException {
        ArrayList<Record> recordList = dollaData.getRecordList(MODE_ENTRY);
        ArrayList<Record> shortcutList = dollaData.getRecordList(MODE_SHORTCUT);
        try {
            Record record = recordList.get(index);
            Record shortcut = new Shortcut(record.getType(),record.getAmount(),record.getDescription());
            dollaData.addToRecordList(MODE_SHORTCUT, shortcut);
            Ui.echoAddRecord(shortcut);
        } catch (IndexOutOfBoundsException e) {
            Ui.printNumberOfRecords(shortcutList.size());
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
