package duke.extensions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import duke.exception.DukeException;

class PomodoroTest {
    private Pomodoro p = new Pomodoro();

    /**
     * Tests for correction exception thrown once there is a timer already started
     *
     * @throws DukeException
     */
    @Test
    public void testStartTimerTimerAlreadyStarted_failure() throws DukeException {
        p.startTimer();
        Exception exception = assertThrows(DukeException.class, () ->
                p.startTimer());
        assertEquals("A timer has already started, please end that one first before starting a new timer!",
                exception.getMessage());
    }

    /**
     * Tests for no exception thrown if timer started and then stop successfully
     *
     * @throws DukeException
     */
    @Test
    public void testStopTimerThenStartTimer_success() throws DukeException {
        p.startTimer();
        p.stopTimer();
        assertDoesNotThrow(() -> p.startTimer());
    }
}
