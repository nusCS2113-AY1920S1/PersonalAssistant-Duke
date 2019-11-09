package dolla.command.modify;

import dolla.model.DollaData;
import dolla.command.Command;
import dolla.model.Entry;
import dolla.model.Record;
import dolla.ui.ModifyUi;

import java.time.LocalDate;

<<<<<<< HEAD
public class PartialModifyEntryCommand extends ModifyEntryCommand {
=======
public class PartialModifyEntryCommand extends Command {

    private String type;
    private double amount;
    private String description;
    private LocalDate date;

    private static final String MODE_ENTRY = "entry";
>>>>>>> 02347bc6ca157d87a54276f756ae2eecdcc1679b

    /**
     * Instantiates a new PartialModifyEntryCommand.
     * @param recordNum number of entry in list to modify.
     * @param type type of entry.
     * @param amount of money.
     * @param description description.
     * @param date date.
     */
    public PartialModifyEntryCommand(int recordNum, String type, double amount, String description, LocalDate date) {
        this.index = recordNum - 1;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public void execute(DollaData dollaData) {
        dollaData.prepForModify(MODE_ENTRY, index);
        if (isIndexInList(dollaData.getRecordListObj(MODE_ENTRY), MODE_ENTRY)) {
            Record originalEntry = dollaData.getRecordFromList(MODE_ENTRY, index);
            overwriteComponents(originalEntry);
<<<<<<< HEAD
            updateUndoState(dollaData);
            Entry newEntry = new Entry(type, amount, description, date, tagName);
=======
            Entry newEntry = new Entry(type, amount, description, date);
>>>>>>> 02347bc6ca157d87a54276f756ae2eecdcc1679b
            dollaData.modifyRecordList(newEntry);
            ModifyUi.echoModifyRecord(newEntry);
            dollaData.updateMode(MODE_ENTRY);
        } else {
            return;
        }
    }

    private void overwriteComponents(Record ogEntry) {
        if (type == null) {
            type = ogEntry.getType();
        }
        if (amount == -1) {
            amount = ogEntry.getAmount();
        }
        if (description == null) {
            description = ogEntry.getDescription();
        }
        if (date == null) {
            date = ogEntry.getDate();
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
