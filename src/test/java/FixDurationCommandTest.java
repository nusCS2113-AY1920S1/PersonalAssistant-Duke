import gazeeebo.Tasks.FixedDuration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixDurationCommandTest {

    FixedDuration testFD = new FixedDuration("playing basketball", "4 hours");
    @Test
    void testtoString() {
        assertEquals("FD|ND|playing basketball|4 hours", testFD.toString());
    }

    @Test
    void testlistformat() {
        assertEquals("[FD][ND] playing basketball(requires:4 hours)", testFD.listFormat());
    }
}