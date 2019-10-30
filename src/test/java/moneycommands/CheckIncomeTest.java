package moneycommands;

import controlpanel.MoneyStorage;
import money.Income;
import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;
import money.Account;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CheckIncomeTest {
    private Ui ui;
    private Account account;
    private MoneyStorage storage;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private LocalDate testDate1 = LocalDate.parse("9/10/1997", dateTimeFormatter);
    private LocalDate testDate2 = LocalDate.parse("5/6/2018", dateTimeFormatter);

    CheckIncomeTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new MoneyStorage(filePath);
        account = new Account();
        ui = new Ui();
    }

    @Test
    void testCheckIncome() throws DukeException, ParseException {
        account.getIncomeListTotal().clear();
        Income i1 = new Income(2000, "TA Pay", testDate1);
        Income i2 = new Income (1000, "Slave Job", testDate2);
        account.getIncomeListTotal().add(i1);
        account.getIncomeListTotal().add(i2);
        ui.clearOutputString();
        ui.clearGraphContainerString();
        String checkInput1 = "check income 10 1997";
        MoneyCommand checkIncome1 = new ViewPastIncomeCommand(checkInput1);
        checkIncome1.execute(account, ui, storage);
        assertEquals(" 1.[I] TA Pay(salary: $2000.0) (Paid On: 9/10/1997)\n" +
                "Total income for October of 1997 : $2000.0\n", ui.getGraphContainerString());
        assertEquals("Got it, list will be printed in the other pane!\n", ui.getOutputString());
        ui.clearOutputString();
        ui.clearGraphContainerString();
        String checkInput2 = "check income 6 2018";
        MoneyCommand checkIncome2 = new ViewPastIncomeCommand(checkInput2);
        checkIncome2.execute(account, ui, storage);
        assertEquals(" 1.[I] Slave Job(salary: $1000.0) (Paid On: 5/6/2018)\n" +
                "Total income for June of 2018 : $1000.0\n", ui.getGraphContainerString());
        assertEquals("Got it, list will be printed in the other pane!\n", ui.getOutputString());
    }

    @Test
    void testInvalidInput() {
        String emptyYearInput = "check income 10";
        ui.clearOutputString();
        try {
            MoneyCommand invalidYearCommand = new ViewPastIncomeCommand(emptyYearInput);
            invalidYearCommand.execute(account, ui, storage);
            fail();
        } catch (ParseException | DukeException | IndexOutOfBoundsException e) {
            assertThat(e.getMessage(), is("Please include the year!"));
        }
        String invalidInput = "check income lorem ipsumNonsense";
        ui.clearOutputString();
        try {
            MoneyCommand invalidInputCommand = new ViewPastIncomeCommand(invalidInput);
            invalidInputCommand.execute(account, ui, storage);
            fail();
        } catch (ParseException | DukeException | NumberFormatException  e) {
            assertThat(e.getMessage(), is("Please input in the format: check income <month> <year>\n"));
        }
    }
}
