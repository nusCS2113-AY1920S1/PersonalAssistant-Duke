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

public class InternalTransferTest {

    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private static String SAMPLE_CREATE_TRACKER1 = "bank-account OCBC /amt 30 /at 27/7/2017 /rate 0";
    private static String SAMPLE_DEPOSIT1 = "deposit 200 OCBC /at 3/3/2018";
    private static String SAMPLE_WITHDRAW1 = "withdraw 200 OCBC /at 3/3/2019";

    InternalTransferTest() throws IOException {
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

    private void executeInternalTransferCmd(String inputString) throws ParseException, DukeException {
        ui.clearOutputString();
        MoneyCommand cmd = new InternalTransferCommand(inputString);
        cmd.execute(account, ui, moneyStorage);
    }

    @Test
    void execute_withdrawTooMuch_exceptionThrown() throws ParseException, DukeException {
        createNewTrackerWithSample1();
        Assertions.assertThrows(DukeException.class, () -> executeInternalTransferCmd(SAMPLE_WITHDRAW1));
    }

    @Test
    void execute_validDepositAndWithdraw_success() throws ParseException, DukeException {
        createNewTrackerWithSample1();
        executeInternalTransferCmd(SAMPLE_DEPOSIT1);
        Assertions.assertEquals("  Got it. Here is the current inf ormation about this account:\n"
                + "  Name: OCBC\n  Balance: 230.00\n  Latest Update Date: 3/3/2018\n  Interest Rate: 0.0\n",
                ui.getOutputString());
        executeInternalTransferCmd(SAMPLE_WITHDRAW1);
        Assertions.assertEquals("  Got it. Here is the current information about this account:\n"
                + "  Name: OCBC\n  Balance: 30.00\n  Latest Update Date: 3/3/2019\n  Interest Rate: 0.0\n",
                ui.getOutputString());
    }
}
