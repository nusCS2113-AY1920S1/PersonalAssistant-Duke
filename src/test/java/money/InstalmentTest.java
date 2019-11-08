package money;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import moneycommands.*;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstalmentTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private String testDate = "9/10/1997";
    private LocalDate dateTestDate = LocalDate.parse(testDate, dateTimeFormatter);

    public InstalmentTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account();
        ui = new Ui();
    }

    @Test
    public void testInstalment() throws ParseException, DukeException {
        account.getInstalments().clear();
        Instalment instalment = new Instalment(5000, "car", "instalments", dateTestDate, 120, 3);
        account.getInstalments().add(instalment);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        instalment.isPayTheMonth();
        assertEquals(true, instalment.getPayForTheMonth());

        instalment.isNotPayTheMonth();
        assertEquals(false, instalment.getPayForTheMonth());

        assertEquals("car", instalment.getDescription());
        assertEquals(5000.0, instalment.getPrice());
        assertEquals("instalments", instalment.getCategory());
        assertEquals(120, instalment.getNumOfPayments());
        assertEquals("9/10/1997", instalment.getBoughtDate());
        assertEquals("9/10/2007", instalment.getDateEndDate());
        assertEquals("[INS]" + "$" + instalment.getPriceStr() + " "
                + instalment.getDescription() + "(on: " + instalment.getBoughtDate() + ")", instalment.toString());

        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }
}