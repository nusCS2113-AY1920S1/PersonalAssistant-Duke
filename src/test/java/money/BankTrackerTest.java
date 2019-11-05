package money;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Parser;
import controlpanel.Ui;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;

public class BankTrackerTest {

    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private BankTracker sample1;
    private LocalDate sampleDate;
    private LocalDate sampleDate2;

    BankTrackerTest() throws IOException, ParseException {
        ui = new Ui();
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account(moneyStorage.load());
        sampleDate = Parser.shortcutTime("10/10/2018");
        sample1 = new BankTracker("OCBC", 100, sampleDate, 0.00);
    }

    @Test
    void getBankAccountInfo_validInput_success() {
        Assertions.assertEquals("  Name: OCBC\n  Balance: 100.00\n  Latest Update Date: 10/10/2018\n" +
                "  Interest Rate: 0.0", sample1.getBankAccountInfo());
    }

    @Test
    void getDescription_validInput_success() {
        Assertions.assertEquals("OCBC", sample1.getDescription());
    }

    @Test
    void getLatestDate_validInput_success() {
        Assertions.assertEquals(sampleDate, sample1.getLatestDate());
    }

    @Test
    void getAmt_validInput_success() {
        Assertions.assertEquals(100.00, sample1.getAmt());
    }

    @Test
    void getRate_validInput_success() {
        Assertions.assertEquals(0.00, sample1.getRate());
    }

    @Test
    void addAmt_validInput_success() {
        sample1.addAmt(100);
        Assertions.assertEquals(200.00, sample1.getAmt());
    }

    @Test
    void updateDate_validInput_success() throws ParseException, DukeException {
        sampleDate2 = Parser.shortcutTime("11/11/2019");
        sample1.updateDate(sampleDate2);
        Assertions.assertEquals(sampleDate2, sample1.getLatestDate());
    }

    @Test
    void updateDate_dateIsTooEarly_exceptionThrown() throws ParseException {
        sampleDate2 = Parser.shortcutTime("1/1/2000");
        Assertions.assertThrows(DukeException.class, ()->sample1.updateDate(sampleDate2));
    }

    @Test
    void predictAmt_validInput_success() throws ParseException, DukeException {
        sampleDate2 = Parser.shortcutTime("11/11/2020");
        Assertions.assertEquals(100, sample1.predictAmt(sampleDate2));
    }

    @Test
    void predictAmt_dateIsTooEarly_exceptionThrown() throws ParseException {
        sampleDate2 = Parser.shortcutTime("1/1/2000");
        Assertions.assertThrows(DukeException.class, ()->sample1.predictAmt(sampleDate2));
    }

}
