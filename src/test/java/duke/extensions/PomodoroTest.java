package duke.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

class PomodoroTest {
    Pomodoro p = new Pomodoro();

    @Test
    public void testStartTimerTimerAlreadyStarted_failure() throws DukeException {
        p.startTimer();
        Exception exception = assertThrows(DukeException.class, () ->
                p.startTimer());
        assertEquals("A timer has already started, please end that one first before starting a new timer!",
                exception.getMessage());
    }

    @Test
    public void testStopTimerThenStartTimer_Success() throws DukeException {
        p.startTimer();
        p.stopTimer();
        assertDoesNotThrow(()->p.startTimer());
    }
}
