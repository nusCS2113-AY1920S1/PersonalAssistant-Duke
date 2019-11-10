package ducats;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MetronomeTest {

    @Test
    void testInitializeParameters() {
        Metronome metronome = new Metronome();
        metronome.start(new int[]{1, 500, 4, 4});
        Assertions.assertEquals(1, metronome.getDuration());
        Assertions.assertEquals(500, metronome.getTempo());
        Assertions.assertArrayEquals(new int[]{4, 4}, metronome.getTimeSig());
    }

    @Test
    void testStart() {
        Metronome metronome = new Metronome();
        metronome.start(new int[]{-1, -1, -1, -1});
        Assertions.assertEquals(0, metronome.getDuration()); // 0 as default int value
    }


}
