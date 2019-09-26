package task;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AfterTest {
    After testAfter = new After("Sleep /after Work");

    AfterTest() throws DukeException {
    }

    @Test
    void testToList() {
        assertEquals("[A][N] Sleep (After: Work)", testAfter.toList());
    }

    @Test
    void testGetType() {
        assertEquals("A", testAfter.getType());
    }

    @Test
    void testGetAfter() {
        assertEquals("Work", testAfter.getAfter());
    }

    @Test
    void testGetStatusIcon() throws DukeException {
        assertEquals("N", testAfter.getStatusIcon());
        testAfter.markDone();
        assertEquals("Y", testAfter.getStatusIcon());
    }
}