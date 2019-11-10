package eggventory.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Raghav-B
public class CommandHistoryTest {

    @Test
    public void testGetLastCommand() {
        String command = "test command";

        for (int i = 0; i < 10; i++) {
            CommandHistory.addToHistory(command + " " + i);
        }

        for (int i = 9; i >= 0; i--) {
            assertEquals(command + " " + i, CommandHistory.getCommand(-1));
        }
    }

    @Test
    public void testGetNextCommand() {
        testGetLastCommand();
        String command = "test command";

        for (int i = 1; i < 10; i++) {
            assertEquals(command + " " + i, CommandHistory.getCommand(1));
        }
    }
}
//@@author
