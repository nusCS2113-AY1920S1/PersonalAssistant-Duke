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

        String newType = "owe";
        double newAmount = 999;
        String newName = "Alexander";
        String newDesc = "Owe Money Pay Money";
        LocalDate newDate = LocalDate.parse("2000-11-11");
        Debt newDebt = new Debt(newType, newName, newAmount, newDesc, newDate);

        int indexToModify = 1;
        int recordNum = indexToModify + 1;

        PartialModifyDebtCommand modifyCommand =
                new PartialModifyDebtCommand(recordNum, newType, newName, newAmount, newDesc, newDate);
        modifyCommand.execute(dollaData);

        Record cmpDebt = dollaData.getRecordFromList(MODE_DEBT, indexToModify);
        assertEquals(newDebt.getRecordDetail(), cmpDebt.getRecordDetail());
    }
}
