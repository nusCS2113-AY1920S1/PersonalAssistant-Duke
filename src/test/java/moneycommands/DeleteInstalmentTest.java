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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteInstalmentTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private String testDate = "9/10/1997";
    private LocalDate dateTestDate = LocalDate.parse(testDate, dateTimeFormatter);

    public DeleteInstalmentTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account();
        ui = new Ui();
    }

    @Test
    public void testDeleteInstalment() throws ParseException, DukeException {
        account.getInstalments().clear();
        Instalment instalment = new Instalment(5000, "car", "instalments", dateTestDate, 120, 3);
        Instalment instalment1 = new Instalment(100000, "mortgage", "instalments", dateTestDate, 180, 4);
        account.getInstalments().add(instalment);
        account.getInstalments().add(instalment1);

        String deleteFirstInput = "delete instalment 2";
        MoneyCommand deleteInstalmentCommand = new DeleteInstalmentCommand(deleteFirstInput);
        assertEquals(false, deleteInstalmentCommand.isExit());
        ui.clearOutputString();
        deleteInstalmentCommand.execute(account, ui, moneyStorage);
        assertEquals(" Noted. I've removed this Instalment:\n"
                        + "  " + instalment1.toString() + "\n"
                        + " Now you have " + (account.getInstalments().size()) + " instalments in the list.\n"
                        + "Got it, list will be printed in the other pane!\n"
                , ui.getOutputString());

        String deleteSecondInput = "delete instalment 1";
        MoneyCommand deleteSecondInstalmentCommand = new DeleteInstalmentCommand(deleteSecondInput);
        ui.clearOutputString();
        deleteSecondInstalmentCommand.execute(account, ui, moneyStorage);
        assertEquals(" Noted. I've removed this Instalment:\n"
                        + "  " + instalment.toString() + "\n"
                        + " Now you have " + (account.getInstalments().size()) + " instalments in the list.\n"
                        + "Got it, list will be printed in the other pane!\n"
                , ui.getOutputString());

        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    public void testDeleteInstalmentException() throws ParseException, DukeException {
        account.getInstalments().clear();

        String deleteEmptyInstalmentList = "delete instalment 1";
        MoneyCommand deleteInstalmentCommand = new DeleteInstalmentCommand(deleteEmptyInstalmentList);
        ui.clearOutputString();
        try {
            deleteInstalmentCommand.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("The serial number of the Instalments is Out Of Bounds!"));
        }

        Instalment instalment = new Instalment(5000, "car", "instalments",
                dateTestDate, 120, 3);
        Instalment instalment1 = new Instalment(100000, "mortgage", "instalments",
                dateTestDate, 180, 4);
        account.getInstalments().add(instalment);
        account.getInstalments().add(instalment1);

        String deleteFirstInput = "delete instalment 3";
        MoneyCommand deleteInstalmentCommand1 = new DeleteInstalmentCommand(deleteFirstInput);
        ui.clearOutputString();
        try {
            deleteInstalmentCommand1.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("The serial number of the Instalments is Out Of Bounds!"));
        }

        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    void testUndoDeleteInstalment() throws DukeException, ParseException {
        account.getInstalments().clear();
        Instalment instalment = new Instalment(5000, "car", "instalments",
                dateTestDate, 120, 3);
        Instalment instalment1 = new Instalment(100000, "mortgage", "instalments",
                dateTestDate, 180, 4);
        account.getInstalments().add(instalment);
        account.getInstalments().add(instalment1);

        String deleteFirstInput = "delete instalment 2";
        MoneyCommand deleteInstalmentCommand = new DeleteInstalmentCommand(deleteFirstInput);
        deleteInstalmentCommand.execute(account, ui, moneyStorage);
        ui.clearOutputString();
        deleteInstalmentCommand.undo(account, ui, moneyStorage);
        assertEquals(" Last command undone: \n" + instalment1.toString() + "\n Now you have "
                + account.getInstalments().size() + " instalments listed\n", ui.getOutputString());
        account.getInstalments().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }
}
