package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.BankTracker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

public class CreateBankAccountTest {

    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private static String SAMPLE_INPUT1 = "bank-account OCBC /amt 30 /at 27/7/2017 /rate 0.005";
    private static String INVALID_INPUT1 = "bank-account OCBC /amt -100 /at 27/7/2017 /rate 0.005";
    private static String INVALID_INPUT2 = "bank-account OCBC /amt 100 /at haha /rate 0.005";
    private static String INVALID_INPUT3 = "bank-account OCBC /amt 100 /at 27/7/2017 /rate 0.005%%";
    private static String INVALID_INPUT4 = "bank-account OCBC /amt %^haha i m tired100 /at 27/7/2017 /rate 0.005%%";

    CreateBankAccountTest() throws IOException {
        ui = new Ui();
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account(moneyStorage.load());
    }

    @Test
    void constructor_wrongNumberFormat_exceptionThrown() {
        Assertions.assertThrows(DukeException.class, () -> new CreateBankAccountCommand(INVALID_INPUT3));
        Assertions.assertThrows(DukeException.class, () -> new CreateBankAccountCommand(INVALID_INPUT4));

    }

    @Test
    void constructor_wrongDateFormat_exceptionThrown() {
        Assertions.assertThrows(DukeException.class, () -> new CreateBankAccountCommand(INVALID_INPUT2));
    }

    @Test
    void constructor_negativeInitialAmt_exceptionThrown() {
        Assertions.assertThrows(DukeException.class, () -> new CreateBankAccountCommand(INVALID_INPUT1));
    }

    @Test
    void isExit_success() throws DukeException {
        Assertions.assertFalse(new CreateBankAccountCommand(SAMPLE_INPUT1).isExit());
    }

    @Test
    void execute_duplicateName_exceptionThrown() throws DukeException {
        account.getBankTrackerList().clear();
        CreateBankAccountCommand cmd1 = new CreateBankAccountCommand(SAMPLE_INPUT1);
        CreateBankAccountCommand cmd2 = new CreateBankAccountCommand(SAMPLE_INPUT1);
        cmd1.execute(account, ui, moneyStorage);
        Assertions.assertThrows(DukeException.class, () -> cmd2.execute(account, ui, moneyStorage));
    }

    @Test
    void execute_validNewTracker_success() throws ParseException, DukeException {
        account.getBankTrackerList().clear();
        CreateBankAccountCommand cmd1 = new CreateBankAccountCommand(SAMPLE_INPUT1);
        cmd1.execute(account, ui, moneyStorage);
        Assertions.assertEquals("New bank account tracker has been added to the list: \n"
                + account.getBankTrackerList().get(0).getBankAccountInfo() + "\n", ui.getOutputString());
    }

    @Test
    void undo_validInput_success() throws ParseException, DukeException {
        account.getBankTrackerList().clear();
        CreateBankAccountCommand cmd1 = new CreateBankAccountCommand(SAMPLE_INPUT1);
        cmd1.execute(account, ui, moneyStorage);
        BankTracker b = account.getBankTrackerList().get(0);

        ui.clearOutputString();
        cmd1.undo(account, ui, moneyStorage);
        Assertions.assertEquals(" Last command undone: \n" + b.toString() + "\n" + " Now you have "
                + account.getBankTrackerList().size() + " bank accounts listed\n", ui.getOutputString());
    }
}
