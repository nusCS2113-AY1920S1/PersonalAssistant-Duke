package ducats.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CopyCommandTest {


    //@@author rohan-av
    @Test
    void testStartMetronome() {
        CopyCommand copyCommand = new CopyCommand("placeholder");
        assertArrayEquals(new int[]{-1, -1, -1, -1}, copyCommand.startMetronome());
    }

    @Test
    void testIsExit() {
        CopyCommand copyCommand = new CopyCommand("placeholder");
        assertFalse(copyCommand.isExit());
    }
}
