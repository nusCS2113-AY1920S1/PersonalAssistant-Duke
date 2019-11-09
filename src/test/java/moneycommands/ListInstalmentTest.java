package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Instalment;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListInstalmentTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private String testDate = "9/10/1997";
    private LocalDate dateTestDate = LocalDate.parse(testDate, dateTimeFormatter);

    public ListInstalmentTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account();
        ui = new Ui();
    }

    @Test
    public void testListInstalments() throws ParseException, DukeException {
        account.getInstalments().clear();
        Instalment instalment = new Instalment(5000, "car", "instalments", dateTestDate, 120, 3);
        Instalment instalment1 = new Instalment(100000, "mortgage", "instalments", dateTestDate, 180, 4);
        account.getInstalments().add(instalment);
        account.getInstalments().add(instalment1);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        MoneyCommand listInstalmentCommand = new ListInstalmentCommand();
        assertEquals(false, listInstalmentCommand.isExit());
        ui.clearOutputString();
        listInstalmentCommand.execute(account, ui, moneyStorage);
        assertEquals("Got it, list will be printed in the other pane!\n", ui.getOutputString());
        assertEquals(" 1.[" + df.format(instalment.getPercentage()) + "%] " + instalment.getDescription() + " ($"
                        + df.format(instalment.equalMonthlyInstalment()) + " per month until "
                        + instalment.getDateEndDate() + ")\n"
                        + " 2.[" + df.format(instalment1.getPercentage()) + "%] " + instalment1.getDescription() + " ($"
                        + df.format(instalment1.equalMonthlyInstalment()) + " per month until "
                        + instalment1.getDateEndDate() + ")\n",
                ui.getGraphContainerString());

        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }
}
