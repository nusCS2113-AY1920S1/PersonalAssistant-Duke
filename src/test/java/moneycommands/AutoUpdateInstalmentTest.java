package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Instalment;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutoUpdateInstalmentTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private String testDate = "9/10/1997";
    private LocalDate dateTestDate = LocalDate.parse(testDate, dateTimeFormatter);

    public AutoUpdateInstalmentTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account();
        ui = new Ui();
    }

    @Test
    public void testAutoUpdateInstalment() throws ParseException, DukeException {
        account.getInstalments().clear();
        account.getExpListTotal().clear();

        LocalDate currDate = LocalDate.now();

        Instalment instalment = new Instalment(50000, "car", "instalments", currDate, 100, 3);
        account.getInstalments().add(instalment);

        AutoUpdateInstalmentCommand autoUpdateInstalmentCommand = new AutoUpdateInstalmentCommand();
        assertEquals(false, autoUpdateInstalmentCommand.isExit());
        assertEquals(LocalDate.now(), autoUpdateInstalmentCommand.getCurrDate());
        autoUpdateInstalmentCommand.execute(account, ui, moneyStorage);

        LocalDate testDate1 = autoUpdateInstalmentCommand.getCurrDate();
        assertEquals(testDate1, currDate);
        LocalDate testDate2 = testDate1.plusMonths(1);
        assertEquals(testDate2, currDate.plusMonths(1));

        autoUpdateInstalmentCommand.setCurrDate(testDate2);
        autoUpdateInstalmentCommand.execute(account, ui, moneyStorage);
        assertEquals(1, account.getExpListTotal().size());
        assertEquals(true, instalment.getPayForTheMonth());

        ui.clearOutputString();
        testDate2 = testDate2.plusDays(2);
        autoUpdateInstalmentCommand.setCurrDate(testDate2);
        autoUpdateInstalmentCommand.execute(account, ui, moneyStorage);
        assertEquals(false, instalment.getPayForTheMonth());
        instalment.setFullyPaid();
        assertEquals(true, instalment.getFullyPaid());

        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }
}
