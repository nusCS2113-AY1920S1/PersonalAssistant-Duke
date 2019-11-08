package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddInstalmentTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private String testDate = "9/10/1997";
    private LocalDate dateTestDate = LocalDate.parse(testDate, dateTimeFormatter);

    public AddInstalmentTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account();
        ui = new Ui();
    }

    @Test
    public void testAddInstalmentCommand() throws ParseException, DukeException {
        String testInput = "add instalment mortgage /amt 100000 /within 200 months /from 12/12/2010 /percentage 6";
        MoneyCommand addInstalmentCommand =  new AddInstalmentCommand(testInput);

        assertEquals(false, addInstalmentCommand.isExit());

        ui.clearOutputString();
        addInstalmentCommand.execute(account, ui, moneyStorage);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        int last = account.getInstalments().size() - 1;

        assertEquals(" Got it. I've added this to your instalments: \n"
                        + account.getInstalments().get(last).toString() + "\n"
                        + " Now you have " + account.getInstalments().size() + " instalments listed\n"
                        + " You are paying $" + df.format(account.getInstalments().get(last).equalMonthlyInstalment())
                        + " per month\n" + " For " + account.getInstalments().get(last).getNumOfPayments() + " months\n"
                        + " Until " + account.getInstalments().get(last).getDateEndDate() + "\n"
                        + " The total amount you will pay is $" + account.getInstalments().get(last).totalAmount() + "\n"
                        + "Got it, list will be printed in the other pane!\n"
                , ui.getOutputString());

        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testAddInvalidNumbers()throws ParseException, DukeException {
        String testInvalidAmount = "add instalment mortgage /amt 100gse2 /within 200 months /from 12/12/2010 /percentage 6";
        String testInvalidNoOfMonths = "add instalment mortgage /amt 100000 /within 2e0 months /from 12/12/2010 /percentage 6";
        String testInvalidInterestRate = "add instalment mortgage /amt 100000 /within 200 months /from 12/12/2010 /percentage f";
        MoneyCommand addInstalmentCommand1 =  new AddInstalmentCommand(testInvalidAmount);
        MoneyCommand addInstalmentCommand2 =  new AddInstalmentCommand(testInvalidNoOfMonths);
        MoneyCommand addInstalmentCommand3 =  new AddInstalmentCommand(testInvalidInterestRate);
        ui.clearOutputString();

        try {
            addInstalmentCommand1.execute(account, ui, moneyStorage);
            addInstalmentCommand2.execute(account, ui, moneyStorage);
            addInstalmentCommand3.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("Please enter in the format: "
                    + "add instalment <desc> /amt <amount> /within <number of months of payment> months "
                    + "/from <date> @<annual interest rate>%\n"));
        }

        account.getInstalments().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testInvalidDate()throws ParseException, DukeException {
        String testInput = "add instalment mortgage /amt 100000 /within 200 months /from 12/12201 /percentage 6\n";
        MoneyCommand addInstalmentCommand =  new AddInstalmentCommand(testInput);
        ui.clearOutputString();

        try {
            addInstalmentCommand.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("Invalid date! Please enter date in the format: d/m/yyyy\n"));
        }
        account.getInstalments().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }
}