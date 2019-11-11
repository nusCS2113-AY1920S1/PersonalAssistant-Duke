package ducats.commands;

import ducats.DucatsException;
import ducats.components.Bar;
import ducats.components.Group;
import ducats.components.Song;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AsciiCommandTest {

    //@@author rohan-av
    @Test
    void testStartMetronome() {
        AsciiCommand asciiCommand = new AsciiCommand("ascii song blank");
        assertArrayEquals(new int[]{-1, -1, -1, -1}, asciiCommand.startMetronome());
    }

    @Test
    void testIsExit() {
        AsciiCommand asciiCommand = new AsciiCommand("ascii song blank");
        assertFalse(asciiCommand.isExit());
    }
}
