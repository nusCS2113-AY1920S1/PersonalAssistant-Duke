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

public class DeleteBankAccountTest {

    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private static String SAMPLE_CREATE_TRACKER1 = "bank-account OCBC /amt 30 /at 27/7/2017 /rate 0.005";
    private static String SAMPLE_INPUT_OUT_OF_BOUND1 = "delete bank-account 100";
    private static String SAMPLE_VALID_INPUT1 = "delete bank-account 1";

    DeleteBankAccountTest() throws IOException {
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

    private void executeDeleteCmd(String inputString) throws ParseException, DukeException {
        ui.clearOutputString();
        MoneyCommand cmd = new DeleteBankAccountCommand(inputString);
        cmd.execute(account, ui, moneyStorage);
    }

//    private void undoDeleteCmd(String deleteCommand) throws ParseException, DukeException {
//        MoneyCommand cmd = new DeleteBankAccountCommand(deleteCommand);
//        cmd.execute(account, ui, moneyStorage);
//        ui.clearOutputString();
//        cmd.undo(account, ui, moneyStorage);
//    }


    @Test
    void execute_outOfBound_exceptionThrown() throws ParseException, DukeException {
        createNewTrackerWithSample1();
        Assertions.assertThrows(DukeException.class, () -> executeDeleteCmd(SAMPLE_INPUT_OUT_OF_BOUND1));
    }

    @Test
    void execute_validInput_success() throws ParseException, DukeException {
        createNewTrackerWithSample1();
        executeDeleteCmd(SAMPLE_VALID_INPUT1);
        Assertions.assertEquals("The bank account tracker below has been removed: \n  Name: OCBC\n"
                + "  Balance: 30.00\n  Latest Update Date: 27/7/2017\n  Interest Rate: 0.005\n", ui.getOutputString());
    }

//    @Test
//    void undo_validInput_success() throws ParseException, DukeException {
//        createNewTrackerWithSample1();
//        undoDeleteCmd(SAMPLE_VALID_INPUT1);
//        Assertions.assertEquals(" Last command undone: \n"
//                + "  Name: OCBC\n  Balance: 30.00\n  Latest Update Date: 27/7/2017\n  Interest Rate: 0.005\n"
//                + " Now you have 1 bank listed\n", ui.getOutputString());
//    }
}
