package duchess.model;

import duchess.exceptions.DuchessException;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeFrameTest {
    private final LocalDateTime dateTime = Util.parseDateTime("12/12/2019 1200");
    private final LocalDateTime dateTime2 = Util.parseDateTime("12/12/2019 1400");

    private final TimeFrame instantFrame = TimeFrame.ofInstantaneousTask(dateTime);
    private final TimeFrame indefiniteFrame = TimeFrame.ofTimelessTask();
    private final TimeFrame normalFrame = new TimeFrame(dateTime, dateTime2);

    public TimeFrameTest() throws DuchessException {
    }

    @Test
    public void compareTo() {
        assertTrue(indefiniteFrame.compareTo(indefiniteFrame) == 0);
        assertTrue(indefiniteFrame.compareTo(instantFrame) < 0);
        assertTrue(instantFrame.compareTo(indefiniteFrame) > 0);
        assertTrue(normalFrame.compareTo(normalFrame) == 0);
    }

    @Test
    public void fallsWithin_indefiniteTasks() {
        assertFalse(normalFrame.fallsWithin(indefiniteFrame));
    }

    @Test
    public void clashesWith_instantaneousTask() {
        assertFalse(normalFrame.clashesWith(instantFrame));
    }
}
