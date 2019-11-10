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

public class ListBankTrackerCommandTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private static String SAMPLE_INPUT1 = "bank-account OCBC /amt 30 /at 27/7/2017 /rate 0.05";
    private static String SAMPLE_INPUT2 = "bank-account DBS /amt 100 /at 20/7/2018 /rate 0.0";

    ListBankTrackerCommandTest() throws IOException {
        ui = new Ui();
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account(moneyStorage.load());
    }

    @Test
    void execute_validInput_success() throws ParseException, DukeException {
        account.getBankTrackerList().clear();
        MoneyCommand cmd1 = new CreateBankAccountCommand(SAMPLE_INPUT1);
        cmd1.execute(account, ui, moneyStorage);
        MoneyCommand cmd2 = new CreateBankAccountCommand(SAMPLE_INPUT2);
        cmd2.execute(account, ui, moneyStorage);
        MoneyCommand cmd3 = new ListBankTrackerCommand();
        cmd3.execute(account, ui, moneyStorage);
        Assertions.assertEquals("Here are the bank accounts and their info:\n"
                + "1. ----------------------------------------\n"
                + "  Name: OCBC\n  Balance: 30.00\n  Latest Update Date: 27/7/2017\n  Interest Rate: 0.05\n"
                + "-------------------------------------------\n"
                + "2. ----------------------------------------\n"
                + "  Name: DBS\n  Balance: 100.00\n  Latest Update Date: 20/7/2018\n  Interest Rate: 0.0\n"
                + "-------------------------------------------\n" ,ui.getGraphContainerString());
    }
}
