package dolla.command.modify;

import dolla.DollaDataStubs.DollaDataDebtStub1;
import dolla.ModeStringList;
import dolla.model.Debt;
import dolla.model.DollaData;
import dolla.model.Record;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartialModifyDebtCommandTest implements ModeStringList {

    @Test
    public void execute() {
        DollaData dollaData = new DollaDataDebtStub1();
        int indexToModify = 1;
        int recordNum = indexToModify + 1;

        String newType = null;
        double newAmount = 999;
        String newName = null;
        String newDesc = "Owe Money Pay Money";
        LocalDate newDate = LocalDate.parse("2000-11-11");

        Record ogDebt = dollaData.getRecordFromList(MODE_DEBT, indexToModify);
        String ogType = ogDebt.getType();
        String ogName = ogDebt.getName();
        Debt modifiedDebt = new Debt(ogType, ogName, newAmount, newDesc, newDate);

        PartialModifyDebtCommand modifyCommand =
                new PartialModifyDebtCommand(recordNum, newType, newName, newAmount, newDesc, newDate);
        modifyCommand.execute(dollaData);

        Record cmpDebt = dollaData.getRecordFromList(MODE_DEBT, indexToModify);
        assertEquals(modifiedDebt.getRecordDetail(), cmpDebt.getRecordDetail());
    }
}
