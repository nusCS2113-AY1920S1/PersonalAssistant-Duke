package duke.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Timer;

import org.junit.jupiter.api.Test;

class PomodoroTimerTaskTest {
    Timer t = new Timer();

    PomodoroTimerTask timerTask = new PomodoroTimerTask(t, 25);

    /**
     * Tests for getMinutesRemaining of timer task
     */
    @Test
    public void testGetMinutesRemaining() {
        int actualMinutesRemaining = timerTask.getMinutesRemaining();
        int expectedMinutesRemaining = 25;
        assertEquals(expectedMinutesRemaining, actualMinutesRemaining);
    }

    /**
     * Tests for setMintuesRemaining for timer task. Assert equals that timer properly set.
     */
    @Test
    public void testSetMinutesRemaining() {
        int expectedMinutesRemaining = 10;
        timerTask.setMinutesRemaining(10);
        int actualMinutesRemaining = timerTask.getMinutesRemaining();
        assertEquals(expectedMinutesRemaining, actualMinutesRemaining);
    }

}
