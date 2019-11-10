package ducats.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GroupCommandTest {

    //@@author rohan-av
    @Test
    void testStartMetronome() {
        GroupCommand groupCommand = new GroupCommand("placeholder");
        assertArrayEquals(new int[]{-1, -1, -1, -1}, groupCommand.startMetronome());
    }

    @Test
    void testIsExit() {
        GroupCommand groupCommand = new GroupCommand("placeholder");
        assertFalse(groupCommand.isExit());
    }
}
