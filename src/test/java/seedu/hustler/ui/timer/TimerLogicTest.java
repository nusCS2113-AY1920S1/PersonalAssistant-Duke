package seedu.hustler.ui.timer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.hustler.ui.timer.TimerLogic.decrement;

class TimerLogicTest {

    int[] timeInput1 = new int[] {55, 30, 2};
    int[] timeOutput1 = new int[] {54, 30, 2};

    int[] timeInput2 = new int[] {0, 10, 2};
    int[] timeOutput2 = new int[] {59, 9, 2};

    int[] timeInput3 = new int[] {0, 0, 1};
    int[] timeOutput3 = new int[] {59, 59, 0};

    int[] timeInput4 = new int[] {1, 0, 0};
    int[] timeOutput4 = new int[] {0, 0, 0};

    @Test
    public void basicDecrement_input023055_output023054() {
        timeInput1 = decrement(timeInput1);

        assertEquals(timeOutput1[0], timeInput1[0]);
        assertEquals(timeOutput1[1], timeInput1[1]);
        assertEquals(timeOutput1[2], timeInput1[2]);
    }

    @Test
    public void secondsModulo60_input021000_output020959() {
        timeInput2 = decrement(timeInput2);

        assertEquals(timeOutput2[0], timeInput2[0]);
        assertEquals(timeOutput2[1], timeInput2[1]);
        assertEquals(timeOutput2[2], timeInput2[2]);
    }

    @Test
    public void minutesModulo60_input010000_output005959() {
        timeInput3 = decrement(timeInput3);

        assertEquals(timeOutput3[0], timeInput3[0]);
        assertEquals(timeOutput3[1], timeInput3[1]);
        assertEquals(timeOutput3[2], timeInput3[2]);
    }

    @Test
    public void basicDecrement_input000001_output000000() {
        timeInput4 = decrement(timeInput4);

        assertEquals(timeOutput4[0], timeInput4[0]);
        assertEquals(timeOutput4[1], timeInput4[1]);
        assertEquals(timeOutput4[2], timeInput4[2]);
    }
}
