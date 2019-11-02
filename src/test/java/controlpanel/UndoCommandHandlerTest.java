package controlpanel;

import moneycommands.AddIncomeCommand;
import moneycommands.MoneyCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UndoCommandHandlerTest {
    private UndoCommandHandler undoCommandHandler;
    UndoCommandHandlerTest() {
        undoCommandHandler = new UndoCommandHandler();
    }

    @Test
    void testFixedSizeStack() {
      for (int i = 0; i < 6; i++) {
          AddIncomeCommand dummy = new AddIncomeCommand(Integer.toString(i));
          undoCommandHandler.updateLastIssuedCommands(dummy);
      }
      assertEquals(5, undoCommandHandler.getLastIssuedCommandsSize());
    }

    @Test
    void testEmptyStack() {
        Assertions.assertThrows(DukeException.class, () -> {
           for (int i = 0; i < 6; i++) {
               MoneyCommand c = undoCommandHandler.getLastIssuedCommand();
           }
        });
    }
}
