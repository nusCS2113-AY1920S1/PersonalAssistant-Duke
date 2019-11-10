package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

public class CheckFutureBalanceTest {

    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private static String SAMPLE_CREATE_TRACKER1 = "bank-account OCBC /amt 30 /at 27/7/2017 /rate 0.005";

    CheckFutureBalanceTest() throws IOException {
        ui = new Ui();
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account(moneyStorage.load());
    }

    private void createNewTrackerWithSample1() throws ParseException, DukeException {
        account.getBankTrackerList().clear();
        CreateBankAccountCommand cmd = new CreateBankAccountCommand(SAMPLE_CREATE_TRACKER1);
        cmd.execute(account, ui, moneyStorage);
    }

    private void executeCheckBalanceCmd(String inputString) throws ParseException, DukeException {
        ui.clearOutputString();
        MoneyCommand cmd = new CheckFutureBalanceCommand(inputString);
        cmd.execute(account, ui, moneyStorage);
    }

    private void undoCheckBalanceCmd(String anyInputString) throws ParseException, DukeException {
        ui.clearOutputString();
        CheckFutureBalanceCommand cmd = new CheckFutureBalanceCommand(anyInputString);
        cmd.undo(account, ui, moneyStorage);
    }

    @Test
    void execute_tooEarlyFutureDate_exceptionThrown() throws ParseException, DukeException {
        createNewTrackerWithSample1();
        Assertions.assertThrows(DukeException.class,
            () -> executeCheckBalanceCmd("check-balance OCBC /at 8/2/2016"));
    }

    @Test
    void execute_validInput_success() throws ParseException, DukeException {
        createNewTrackerWithSample1();
        executeCheckBalanceCmd("check-balance OCBC /at 8/2/2020");
        Assertions.assertEquals("  The future balance in OCBC" + " :\n    34.84 at 8/2/2020\n", ui.getOutputString());
    }

    @Test
    void undo_anyInput_exceptionThrown() throws DukeException, ParseException {
        createNewTrackerWithSample1();
        Assertions.assertThrows(DukeException.class, () -> undoCheckBalanceCmd("check-balance OCBC /at 8/2/2020"));
    }
}
