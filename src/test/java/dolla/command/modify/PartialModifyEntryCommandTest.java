package dolla.command.modify;

import dolla.dolladatastub.DollaDataEntryStub1;
import dolla.ModeStringList;
import dolla.model.DollaData;
import dolla.model.Entry;
import dolla.model.Record;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartialModifyEntryCommandTest implements ModeStringList {

    @Test
    public void execute() {
        DollaData dollaData = new DollaDataEntryStub1();
        int indexToModify = 1;
        int recordNum = indexToModify + 1;

        String newType = "expense";
        double newAmount = 999;
        String newDesc = null;
        LocalDate newDate = null;

        Record ogEntry = dollaData.getRecordFromList(MODE_ENTRY, indexToModify);
        String ogDesc = ogEntry.getDescription();
        LocalDate ogDate = ogEntry.getDate();

        Entry modifiedEntry = new Entry(newType, newAmount, ogDesc, ogDate);

        PartialModifyEntryCommand modifyCommand =
                new PartialModifyEntryCommand(recordNum, newType, newAmount, newDesc, newDate);
        modifyCommand.execute(dollaData);

        Record cmpEntry = dollaData.getRecordFromList(MODE_ENTRY, indexToModify);
        assertEquals(modifiedEntry.getRecordDetail(), cmpEntry.getRecordDetail());
    }
}
