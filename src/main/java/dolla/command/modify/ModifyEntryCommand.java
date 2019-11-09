package dolla.command.modify;

import dolla.command.Command;
import dolla.command.action.Redo;
import dolla.command.action.state.EntryState;
import dolla.command.action.state.UndoStateList;
import dolla.model.DollaData;
import dolla.model.EntryList;

import java.time.LocalDate;

public abstract class ModifyEntryCommand extends Command {
    protected String type;
    protected double amount;
    protected String description;
    protected LocalDate date;
    protected String tagName = "";
    protected String mode = MODE_ENTRY;

    protected void updateUndoState(DollaData dollaData) {
        EntryList entryList = (EntryList) dollaData.getRecordListObj(mode);
        UndoStateList.addState(new EntryState(entryList.get()), mode);///////////////////////////////////////
        Redo.clearRedoState(mode);
    }
}
