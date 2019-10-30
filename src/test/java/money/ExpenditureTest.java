package money;

import controlpanel.MoneyStorage;
import money.Loan;
import moneycommands.*;
import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExpenditureTest {
    private Ui ui;
    private Account account;
    private MoneyStorage storage;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private LocalDate testDate = LocalDate.parse("9/10/1997", dateTimeFormatter);


    ExpenditureTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new MoneyStorage(filePath);
        account = new Account();
        ui = new Ui();
    }

    @Test
    void testAddExpenditure() throws ParseException, DukeException {
        String testAdd = "spent sick sirloin steak /amt 30 /cat food /on 9/10/1997";
        MoneyCommand addExpenditureCommand = new AddExpenditureCommand(testAdd);
        ui.clearOutputString();
        addExpenditureCommand.execute(account, ui, storage);
        assertEquals(" Got it. I've added this to your total spending: \n" +
                        "     [E]$30.0 sick sirloin steak (on: 9/10/1997)\n"
                , ui.getOutputString().split(" Now")[0]);
    }

    @Test
    void testListAllExpenditure() throws ParseException, DukeException {
        account.getExpListTotal().clear();
        Expenditure e1 = new Expenditure(70, "Flowers for the lady", "present", testDate);
        Expenditure e2 = new Expenditure(50, "Lego for the boy", "toy", testDate);
        account.getExpListTotal().add(e1);
        account.getExpListTotal().add(e2);
        MoneyCommand listAllExpenditureCommand = new ListTotalExpenditureCommand();
        ui.clearOutputString();
        ui.clearGraphContainerString();
        listAllExpenditureCommand.execute(account, ui, storage);
        assertEquals(" 1.[E]$70.0 Flowers for the lady(on: 9/10/1997)\n" +
                " 2.[E]$50.0 Lego for the boy(on: 9/10/1997)\n" +
                "Total expenditure so far: $120.0\n", ui.getGraphContainerString());
        assertEquals("Got it, list will be printed in the other pane!\n", ui.getOutputString());
    }

    @Test
    void testDeleteExpenditure() throws DukeException, ParseException {
        account.getExpListTotal().clear();
        Expenditure e2 = new Expenditure(50, "Doll for the girl", "toy", testDate);
        account.getExpListTotal().add(e2);
        String deleteInput = "delete expenditure 1";
        MoneyCommand deleteExpenditureCommand = new DeleteExpenditureCommand(deleteInput);
        ui.clearOutputString();
        deleteExpenditureCommand.execute(account, ui, storage);
        assertEquals(" Noted. I've removed this expenditure:\n" +
                "  [E]$50.0 Doll for the girl(on: 9/10/1997)\n", ui.getOutputString().split(" Now")[0]);
    }

    @Test
    void testInvalidCommandException() {
        String invalidInput = "spent useless stuff /amt rubbish /cat 3209 ";
        MoneyCommand invalidAddCommand = new AddExpenditureCommand(invalidInput);
        ui.clearOutputString();
        try {
            invalidAddCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException | ParseException e) {
            assertThat(e.getMessage(), is("Please enter in the format: " +
                    "spent <description> /amt <amount> /cat <category> /on <date>\n"));
        }
    }

    @Test
    void testInvalidDateException() {
        String invalidInput = "spent useless stuff /amt 2000000 /cat rubbish /on 222/43/123431 ";
        MoneyCommand invalidAddCommand = new AddExpenditureCommand(invalidInput);
        ui.clearOutputString();
        try {
            invalidAddCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException | ParseException e) {
            assertThat(e.getMessage(), is("Invalid date! Please enter date in the format: d/m/yyyy\n"));
        }
    }

    @Test
    void testInvalidIndexException() throws DukeException {
        String invalidInput = "delete expenditure yo yo ";
        MoneyCommand invalidAddCommand = new DeleteExpenditureCommand(invalidInput);
        ui.clearOutputString();
        try {
            invalidAddCommand.execute(account, ui, storage);
            fail();
        } catch (DukeException | ParseException | NumberFormatException e) {
            assertThat(e.getMessage(),
                    is("Please enter a numerical number as the index of the expenditure to be deleted\n"));
        }
    }
}
