package dolla.command.view;

import java.time.LocalDate;

import dolla.DollaDataStubs.DollaDataStub0;
import dolla.DollaDataStubs.DollaDataEntryStub1;
import dolla.DollaDataStubs.DollaDataEntryStub2;
import dolla.model.DollaData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewCommandTest {

    @Test
    public void execute_emptyList_zero() {
        DollaData dollaData = new DollaDataStub0();
        ViewTodayCommand viewCommand = new ViewTodayCommand();
        viewCommand.execute(dollaData);
        double expectedSum = 0;
        assertEquals(expectedSum, viewCommand.getSum());
    }

    @Test
    public void execute_noRelevantDates_zero() {
        DollaData dollaData = new DollaDataEntryStub1();
        LocalDate inputDate = LocalDate.parse("2099-12-03");
        ViewDateCommand viewCommand = new ViewDateCommand(inputDate);
        viewCommand.execute(dollaData);
        double expectedSum = 0;
        assertEquals(expectedSum, viewCommand.getSum());
    }

    @Test
    public void execute_positiveOverall_positive() {
        DollaData dollaData = new DollaDataEntryStub1();
        LocalDate inputDate = LocalDate.parse("1111-11-11");
        ViewDateCommand viewCommand = new ViewDateCommand(inputDate);
        viewCommand.execute(dollaData);
        double expectedSum = 200 - 100;
        assertEquals(expectedSum, viewCommand.getSum());
    }

    @Test
    public void execute_positiveOverall_negative() {
        DollaData dollaData = new DollaDataEntryStub2();
        LocalDate inputDate = LocalDate.parse("1111-11-11");
        ViewDateCommand viewCommand = new ViewDateCommand(inputDate);
        viewCommand.execute(dollaData);
        double expectedSum = 100 - 200;
        assertEquals(expectedSum, viewCommand.getSum());
    }

}
