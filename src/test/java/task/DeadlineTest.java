package task;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {

    Deadline testDeadline = new Deadline("Sleep /by 01-01-1970 2200");
    Deadline testDeadlineOverload = new Deadline("0", "Sleep", "01-01-1970 2200");

    DeadlineTest() throws DukeException {
    }

    @Test
    void testToList() {
        assertEquals("[D][N] Sleep (By: 01-01-1970 2200)", testDeadline.toList());
        assertEquals("[D][N] Sleep (By: 01-01-1970 2200)", testDeadlineOverload.toList());
    }

    @Test
    void testSnooze() {
    }

    @Test
    void testGetStatusIcon() throws DukeException {
        assertEquals("N", testDeadline.getStatusIcon());
        assertEquals("N", testDeadlineOverload.getStatusIcon());
        testDeadline.markDone();
        testDeadlineOverload.markDone();
        assertEquals("Y", testDeadline.getStatusIcon());
        assertEquals("Y", testDeadlineOverload.getStatusIcon());
    }


    @Test
    void testGetType() {
        assertEquals("D", testDeadline.getType());
        assertEquals("D", testDeadlineOverload.getType());
    }
}