package controlpanel;

import money.Account;
import moneycommands.AddIncomeCommand;
import moneycommands.ListTotalIncomeCommand;
import moneycommands.MoneyCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UndoCommandHandlerTest {
    private UndoCommandHandler undoCommandHandler;
    private Account account;
    private Ui ui;
    private MoneyStorage storage;
    UndoCommandHandlerTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        undoCommandHandler = new UndoCommandHandler();
        account = new Account();
        ui = new Ui();
        storage = new MoneyStorage(filePath);
    }

    @Test
    void testFixedSizeStack() {
      for (int i = 0; i < 6; i++) {
          ListTotalIncomeCommand dummy = new ListTotalIncomeCommand();
          undoCommandHandler.updateLastIssuedCommands(dummy);
      }
      assertEquals(5, undoCommandHandler.getLastIssuedCommandsSize());
    }

    @Test
    void testEmptyStackException() {
        for (int i = 0; i < 5; i++) {
            ListTotalIncomeCommand dummy = new ListTotalIncomeCommand();
            undoCommandHandler.updateLastIssuedCommands(dummy);
        }

        Assertions.assertThrows(DukeException.class, () -> {
           for (int i = 0; i < 6; i++) {
               MoneyCommand c = undoCommandHandler.getLastIssuedCommand();
               c.execute(account, ui, storage);
           }
        });
    }

    @Test
    void testUndoableCommandException() {
        ListTotalIncomeCommand dummy = new ListTotalIncomeCommand();
        undoCommandHandler.updateLastIssuedCommands(dummy);

        Assertions.assertThrows(DukeException.class, () -> {
           MoneyCommand c = undoCommandHandler.getLastIssuedCommand();
           c.undo(account, ui, storage);
        });
    }
}
