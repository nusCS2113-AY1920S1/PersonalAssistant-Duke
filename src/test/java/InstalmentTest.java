import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Instalment;
import moneycommands.*;
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
        assertEquals("[INS]" + "$" + instalment.getPrice() + " "
                + instalment.getDescription() + "(on: " + instalment.getBoughtDate() + ")", instalment.toString());
    }

    @Test
    public void testAddInstalmentCommand() throws ParseException, DukeException {
        String testInput = "add instalment mortgage /amt 100000 /within 200 months /from 12/12/2010 @6%";
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
                , ui.getOutputString());
    }

    @Test
    public void testDeleteInstalment() throws ParseException, DukeException {
        account.getInstalments().clear();
        Instalment instalment = new Instalment(5000, "car", "instalments", dateTestDate, 120, 3);
        Instalment instalment1 = new Instalment(100000, "mortgage", "instalments", dateTestDate, 180, 4);
        account.getInstalments().add(instalment);
        account.getInstalments().add(instalment1);

        String deleteFirstInput = "delete instalments 2";
        MoneyCommand deleteInstalmentCommand = new DeleteInstalmentCommand(deleteFirstInput);
        assertEquals(false, deleteInstalmentCommand.isExit());
        ui.clearOutputString();
        deleteInstalmentCommand.execute(account, ui, moneyStorage);
        assertEquals(" Noted. I've removed this Instalment:\n"
                + "  " + instalment1.toString() + "\n"
                + " Now you have " + (account.getInstalments().size()) + " instalments in the list.\n"
                , ui.getOutputString());

        String deleteSecondInput = "delete instalments 1";
        MoneyCommand deleteSecondInstalmentCommand = new DeleteInstalmentCommand(deleteSecondInput);
        ui.clearOutputString();
        deleteSecondInstalmentCommand.execute(account, ui, moneyStorage);
        assertEquals(" Noted. I've removed this Instalment:\n"
                        + "  " + instalment.toString() + "\n"
                        + " Now you have " + (account.getInstalments().size()) + " instalments in the list.\n"
                , ui.getOutputString());
    }

    @Test
    public void testDeleteInstalmentException() throws ParseException, DukeException {
        account.getInstalments().clear();

        String deleteEmptyInstalmentList = "delete instalments 1";
        MoneyCommand deleteInstalmentCommand = new DeleteInstalmentCommand(deleteEmptyInstalmentList);
        ui.clearOutputString();
        try {
            deleteInstalmentCommand.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("The serial number of the Instalments is Out Of Bounds!"));
        }

        Instalment instalment = new Instalment(5000, "car", "instalments", dateTestDate, 120, 3);
        Instalment instalment1 = new Instalment(100000, "mortgage", "instalments", dateTestDate, 180, 4);
        account.getInstalments().add(instalment);
        account.getInstalments().add(instalment1);

        String deleteFirstInput = "delete instalments 3";
        MoneyCommand deleteInstalmentCommand1 = new DeleteInstalmentCommand(deleteFirstInput);
        ui.clearOutputString();
        try {
            deleteInstalmentCommand1.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("The serial number of the Instalments is Out Of Bounds!"));
        }
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
                        + df.format(instalment.equalMonthlyInstalment()) + " per month until " + instalment.getDateEndDate() + ")\n"
                        + " 2.[" + df.format(instalment1.getPercentage()) + "%] " + instalment1.getDescription() + " ($"
                        + df.format(instalment1.equalMonthlyInstalment()) + " per month until " + instalment1.getDateEndDate() + ")\n",
                ui.getGraphContainerString());
    }

    @Test
    public void testAutoUpdateInstalment() throws ParseException, DukeException{
        account.getInstalments().clear();
        account.getExpListTotal().clear();
        account.getExpListCurrMonth().clear();

        LocalDate currDate = LocalDate.now();

        Instalment instalment = new Instalment(50000, "car", "instalments", currDate, 100, 3);
        account.getInstalments().add(instalment);

        AutoUpdateInstalmentCommand autoUpdateInstalmentCommand = new AutoUpdateInstalmentCommand();
        autoUpdateInstalmentCommand.execute(account, ui, moneyStorage);

        LocalDate testDate1 = autoUpdateInstalmentCommand.getCurrDate();
        assertEquals(testDate1, currDate);
        LocalDate testDate2 = testDate1.plusMonths(1);
        assertEquals(testDate2, currDate.plusMonths(1));

        autoUpdateInstalmentCommand.setCurrDate(testDate2);
        autoUpdateInstalmentCommand.execute(account, ui, moneyStorage);
        assertEquals("You have paid " + instalment.equalMonthlyInstalment() + " for "
                + instalment.getDescription() + ". It is currently " + instalment.getPercentage() + "% paid.", ui.getOutputString());
        assertEquals(1, account.getExpListCurrMonth().size());
        assertEquals(1, account.getExpListTotal().size());
        assertEquals(true, instalment.getPayForTheMonth());

        ui.clearOutputString();
        testDate2 = testDate2.plusDays(2);
        autoUpdateInstalmentCommand.setCurrDate(testDate2);
        autoUpdateInstalmentCommand.execute(account, ui, moneyStorage);
        assertEquals(false, instalment.getPayForTheMonth());
    }
}