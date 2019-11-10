package dolla.command.modify;

import dolla.DollaDataStubs.DollaDataLimitStub1;
import dolla.ModeStringList;
import dolla.model.DollaData;
import dolla.model.Limit;
import dolla.model.Record;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartialModifyLimitCommandTest implements ModeStringList {

    @Test
    public void execute_uniqueLimit_success() {
        DollaData dollaData = new DollaDataLimitStub1();
        int indexToModify = 0;
        int recordNum = indexToModify + 1;

        String newType = "budget";
        double newAmount = -1;
        String newDuration = "weekly";

        Record ogLimit = dollaData.getRecordFromList(MODE_LIMIT, indexToModify);
        double ogAmount = ogLimit.getAmount();
        Limit modifiedLimit = new Limit(newType, ogAmount, newDuration);

        PartialModifyLimitCommand modifyCommand =
                new PartialModifyLimitCommand(recordNum, newType, newAmount, newDuration);
        modifyCommand.execute(dollaData);

        Record cmpLimit = dollaData.getRecordFromList(MODE_LIMIT, indexToModify);
        assertEquals(modifiedLimit.getRecordDetail(), cmpLimit.getRecordDetail());
    }

    @Test
    public void execute_existingDuration_failure() {
        DollaData dollaData = new DollaDataLimitStub1();

        String newType = "budget";
        double newAmount = -1;
        String newDuration = "monthly";

        int indexToModify = 0;
        int recordNum = indexToModify + 1;

        Record ogLimit = dollaData.getRecordFromList(MODE_LIMIT, indexToModify);
        PartialModifyLimitCommand modifyCommand =
                new PartialModifyLimitCommand(recordNum, newType, newAmount, newDuration);
        modifyCommand.execute(dollaData);

        Record cmpLimit = dollaData.getRecordFromList(MODE_LIMIT, indexToModify);
        assertEquals(ogLimit.getRecordDetail(), cmpLimit.getRecordDetail());
    }
}
