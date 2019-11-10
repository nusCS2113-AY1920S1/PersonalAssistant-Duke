package dolla.command.modify;

import dolla.dolladatastub.DollaDataLimitStub1;
import dolla.ModeStringList;
import dolla.model.DollaData;
import dolla.model.Limit;
import dolla.model.Record;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FullModifyLimitCommandTest implements ModeStringList {

    @Test
    public void execute_uniqueLimit_success() {
        DollaData dollaData = new DollaDataLimitStub1();

        String newType = "budget";
        double newAmount = 999;
        String newDuration = "weekly";
        final Limit newLimit = new Limit(newType, newAmount, newDuration);

        int indexToModify = 0;
        String currMode = MODE_LIMIT;
        dollaData.updateMode("modify " + currMode);
        dollaData.prepForModify(currMode, indexToModify);

        FullModifyLimitCommand modifyCommand = new FullModifyLimitCommand(newType, newAmount, newDuration);
        modifyCommand.execute(dollaData);

        Record cmpLimit = dollaData.getRecordFromList(MODE_LIMIT, indexToModify);
        assertEquals(newLimit.getRecordDetail(), cmpLimit.getRecordDetail());
    }

    @Test
    public void execute_existingDuration_failure() {
        DollaData dollaData = new DollaDataLimitStub1();

        String newType = "budget";
        double newAmount = 999;
        String newDuration = "monthly";

        int indexToModify = 0;
        String currMode = MODE_LIMIT;
        dollaData.updateMode("modify " + currMode);
        dollaData.prepForModify(currMode, indexToModify);

        Record ogLimit = dollaData.getRecordFromList(MODE_LIMIT, indexToModify);
        FullModifyLimitCommand modifyCommand = new FullModifyLimitCommand(newType, newAmount, newDuration);
        modifyCommand.execute(dollaData);

        Record cmpLimit = dollaData.getRecordFromList(MODE_LIMIT, indexToModify);
        assertEquals(ogLimit.getRecordDetail(), cmpLimit.getRecordDetail());
    }
}
