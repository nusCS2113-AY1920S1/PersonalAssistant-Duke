package dolla.command.modify;

import dolla.model.DollaData;
import dolla.model.Entry;
import dolla.ui.ModifyUi;

import java.time.LocalDate;

//@@author omupenguin
public class FullModifyEntryCommand extends ModifyEntryCommand {

    /**
     * Instantiates a new FullModifyEntryCommand.
     * @param type type of entry
     * @param amount of money
     * @param description description
     * @param date date
     */
    public FullModifyEntryCommand(String type, double amount, String description, LocalDate date) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public void execute(DollaData dollaData) {
        updateUndoState(dollaData);
        Entry newEntry = new Entry(type, amount, description, date);
        dollaData.modifyRecordList(newEntry);
        ModifyUi.echoModifyRecord(newEntry);
        dollaData.updateMode(mode);
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
