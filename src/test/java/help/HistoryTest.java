package help;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryTest {

    @Test
    public void testHistory() {
        History history = new History();
        history.addingCommandsEntered("This is command 1");
        assertEquals("This is command 1", history.getCommandsEntered().get(0));
        history.addingCommandsEntered("This is command 2");
        history.addingCommandsEntered("This is command 3");

        history.setFlagFalse();
        assertEquals(false, history.getFlag());

        history.setFlagTrue();
        assertEquals(true, history.getFlag());
        assertEquals(3, history.getMaxIndex());

        history.setCurrIndex();
        assertEquals(3, history.getCurrIndex());

        history.setFlagForFirstPress();
        assertEquals(true, history.getFlagForFirstPress());

        history.getPreviousCommand();
        assertEquals("This is command 3", history.getCommandsEntered().get(history.getCurrIndex()));
        history.getPreviousCommand();
        assertEquals("This is command 2", history.getCommandsEntered().get(history.getCurrIndex()));
        history.getPreviousCommand();
        assertEquals("This is command 1", history.getCommandsEntered().get(history.getCurrIndex()));
        history.getPreviousCommand();
        assertEquals("This is command 1", history.getCommandsEntered().get(history.getCurrIndex()));
    }
}
