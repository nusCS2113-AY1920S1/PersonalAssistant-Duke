package ducats;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MetronomeTest {

    @Test
    void testInitializeParameters() {
        Metronome metronome = new Metronome();
        metronome.start(new int[]{1, 500, 4, 4});
        assertEquals(1, metronome.getDuration());
        assertEquals(500, metronome.getTempo());
        assertArrayEquals(new int[]{4, 4}, metronome.getTimeSig());
    }

    @Test
    void testStart() {
        Metronome metronome = new Metronome();
        metronome.start(new int[]{-1, -1, -1, -1});
        assertEquals(0, metronome.getDuration()); // 0 as default int value
    }


}
