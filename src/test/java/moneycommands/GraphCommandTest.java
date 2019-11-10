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

public class GraphCommandTest {

    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private static String INVALID_INPUT1 = "graph financial status /until 11/11/2019";
    private static String INVALID_INPUT2 = "graph monthly histogram";
    private static String VALID_COMMAND = "graph monthly report";

    //@@author cctt1014
    GraphCommandTest() throws IOException {
        ui = new Ui();
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account(moneyStorage.load());
    }

    @Test
    void execute_invalidInput_exceptionThrown() {
        MoneyCommand cmd1 = new GraphCommand(INVALID_INPUT1);
        Assertions.assertThrows(DukeException.class, () -> cmd1.execute(account, ui, moneyStorage));
        MoneyCommand cmd2 = new GraphCommand(INVALID_INPUT2);
        Assertions.assertThrows(DukeException.class, () -> cmd2.execute(account, ui, moneyStorage));
    }

    @Test
    void execute_validInput_success() throws ParseException, DukeException {
        MoneyCommand cmd1 = new GraphCommand(VALID_COMMAND);
        cmd1.execute(account, ui, moneyStorage);
        Assertions.assertEquals("Got it, graph will be printed in the other pane!\n", ui.getOutputString());
    }
}
