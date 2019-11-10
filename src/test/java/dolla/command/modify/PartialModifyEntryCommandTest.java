package dolla.command.modify;

import dolla.DollaDataStubs.DollaDataStub2;
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
        DollaData dollaData = new DollaDataStub2();

        String newType = "expense";
        double newAmount = 999;
        String newDesc = "WOW";
        LocalDate newDate = LocalDate.parse("2000-11-11");
        Entry newEntry = new Entry(newType, newAmount, newDesc, newDate);

        int indexToModify = 1;
        int recordNum = indexToModify + 1;

        PartialModifyEntryCommand modifyCommand =
                new PartialModifyEntryCommand(recordNum, newType, newAmount, newDesc, newDate);
        modifyCommand.execute(dollaData);

        Record cmpEntry = dollaData.getRecordFromList(MODE_ENTRY, indexToModify);
        assertEquals(newEntry.getRecordDetail(), cmpEntry.getRecordDetail());
    }
}
