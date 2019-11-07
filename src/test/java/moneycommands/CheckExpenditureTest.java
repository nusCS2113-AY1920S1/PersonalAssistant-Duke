package moneycommands;

import controlpanel.MoneyStorage;
import money.Expenditure;
import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;
import money.Account;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CheckExpenditureTest {
    private Ui ui;
    private Account account;
    private MoneyStorage storage;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private LocalDate testDate1 = LocalDate.parse("9/10/1997", dateTimeFormatter);
    private LocalDate testDate2 = LocalDate.parse("4/9/2015", dateTimeFormatter);

    CheckExpenditureTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new MoneyStorage(filePath);
        account = new Account();
        ui = new Ui();
    }

    @Test
    void testCheckExpenditure() throws ParseException, DukeException {
        account.getExpListTotal().clear();
        Expenditure e1 = new Expenditure(120, "A Jays 5", "present", testDate1);
        Expenditure e2 = new Expenditure(94, "HHN VIP Tickets", "gift", testDate2);
        account.getExpListTotal().add(e1);
        account.getExpListTotal().add(e2);
        ui.clearOutputString();
        ui.clearGraphContainerString();
        String checkInput1 = "check expenditure 10 1997";
        MoneyCommand checkExpenditure1 = new ViewPastExpenditureCommand(checkInput1);
        checkExpenditure1.execute(account, ui, storage);
        assertEquals(" 1.[E]$120.00 A Jays 5(on: 9/10/1997)\n" +
                "Total expenditure for October of 1997 : $120.00\n", ui.getGraphContainerString());
        assertEquals("Got it, list will be printed in the other pane!\n", ui.getOutputString());
        ui.clearOutputString();
        ui.clearGraphContainerString();
        String checkInput2 = "check expenditure 9 2015";
        MoneyCommand checkExpenditure2 = new ViewPastExpenditureCommand(checkInput2);
        checkExpenditure2.execute(account, ui, storage);
        assertEquals(" 1.[E]$94.00 HHN VIP Tickets(on: 4/9/2015)\n" +
                "Total expenditure for September of 2015 : $94.00\n", ui.getGraphContainerString());
        assertEquals("Got it, list will be printed in the other pane!\n", ui.getOutputString());
    }

     @Test
     void testCheckMonthExpenditure() throws DukeException, ParseException {
        account.getExpListTotal().clear();
        int currMonth = LocalDate.now().getMonthValue();
        int currYear = LocalDate.now().getYear();
        LocalDate currMonthDate = LocalDate.parse("4/" + currMonth + "/" + currYear, dateTimeFormatter);
        Expenditure e1 = new Expenditure(40, "blue shirt", "gift", currMonthDate);
        Expenditure e2 = new Expenditure(94, "HHN VIP Tickets", "gift", testDate2);
        account.getExpListTotal().add(e1);
        account.getExpListTotal().add(e2);
        ui.clearOutputString();
        ui.clearGraphContainerString();
        String checkInput = "list month";
        MoneyCommand checkMonthExpenditure = new ViewPastExpenditureCommand(checkInput);
        checkMonthExpenditure.execute(account, ui, storage);
        String currMontStr = new DateFormatSymbols().getMonths()[currMonth-1];
         assertEquals(" 1.[E]$40.00 blue shirt(on: 4/" + currMonth + "/" + currYear + ")\n" +
                 "Total expenditure for " + currMontStr + " of " + currYear + " : $40.00\n", ui.getGraphContainerString());
         assertEquals("Got it, list will be printed in the other pane!\n", ui.getOutputString());
     }

    @Test
    void testInvalidInput() {
        String emptyYearInput = "check expenditure 5";
        ui.clearGraphContainerString();
        try {
            MoneyCommand invalidYearCommand = new ViewPastExpenditureCommand(emptyYearInput);
            invalidYearCommand.execute(account, ui, storage);
            fail();
        } catch (ParseException | DukeException | IndexOutOfBoundsException e) {
            assertThat(e.getMessage(), is("Please include the year!"));
        }
        String invalidInput = "check expenditure yo yo bro";
        ui.clearOutputString();
        try {
            MoneyCommand invalidInputCommand = new ViewPastExpenditureCommand(invalidInput);
            invalidInputCommand.execute(account, ui, storage);
            fail();
        } catch (ParseException | DukeException | NumberFormatException  e) {
            assertThat(e.getMessage(), is("Please input in the format: check expenditure <month> <year>\n"));
        }
        String invalidMonthInput = "check expenditure 45 1994";
        ui.clearOutputString();
        try {
            MoneyCommand invalidMonthCommand = new ViewPastExpenditureCommand(invalidMonthInput);
            invalidMonthCommand.execute(account, ui, storage);
            fail();
        } catch (ParseException | DukeException | NumberFormatException  e) {
            assertThat(e.getMessage(), is("Month is invalid! Please pick a month from 1-12"));
        }
        String invalidYearInput = "check expenditure 10 839";
        ui.clearOutputString();
        try {
            MoneyCommand invalidYearCommand = new ViewPastExpenditureCommand(invalidYearInput);
            invalidYearCommand.execute(account, ui, storage);
            fail();
        } catch (ParseException | DukeException e) {
            assertThat(e.getMessage(), is("Only years dated from 1000-9999 are accepted by Financial Ghost :)"));
        }
    }
}
