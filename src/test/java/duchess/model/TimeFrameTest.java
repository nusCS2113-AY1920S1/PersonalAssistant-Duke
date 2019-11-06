package duchess.model;

import duchess.exceptions.DuchessException;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeFrameTest {
    private final TimeFrame instantFrame;
    private final TimeFrame indefiniteFrame;
    private final TimeFrame normalFrame;
    private final TimeFrame normalFrame2;

    /**
     * Initializes few TimeFrames for testing.
     *
     * @throws DuchessException if unable to create a TimeFrame
     */
    public TimeFrameTest() throws DuchessException {
        LocalDateTime dateTime = Util.parseDateTime("12/12/2019 1200");
        LocalDateTime dateTime2 = Util.parseDateTime("12/12/2019 1400");
        normalFrame = new TimeFrame(dateTime, dateTime2);

        LocalDateTime dateTime3 = Util.parseDateTime("12/12/2019 1300");
        LocalDateTime dateTime4 = Util.parseDateTime("12/12/2019 1500");
        normalFrame2 = new TimeFrame(dateTime3, dateTime4);

        instantFrame = TimeFrame.ofInstantaneousTask(dateTime);
        indefiniteFrame = TimeFrame.ofTimelessTask();
    }

    @Test
    public void compareTo() {
        assertTrue(indefiniteFrame.compareTo(indefiniteFrame) == 0);
        assertTrue(indefiniteFrame.compareTo(instantFrame) < 0);
        assertTrue(instantFrame.compareTo(indefiniteFrame) > 0);
        assertTrue(normalFrame.compareTo(normalFrame) == 0);
        assertTrue(normalFrame.compareTo(normalFrame2) < 0);
    }

    @Test
    public void fallsWithin_indefiniteTimeFrame() {
        assertFalse(normalFrame.fallsWithin(indefiniteFrame));
    }

    @Test
    public void clashesWith_instantaneousTimeFrame() {
        assertFalse(normalFrame.clashesWith(instantFrame));
    }

    @Test
    public void clashesWith_normalTimeFrame() {
        assertTrue(normalFrame.clashesWith(normalFrame2));
        assertTrue(normalFrame2.clashesWith(normalFrame));
    }
}
