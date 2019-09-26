package task;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AfterTest {
    After testAfter = new After("Sleep /after Work");

    AfterTest() throws DukeException {
    }

    @Test
    void testExceptionThrown() {
        try {
            assertEquals("/ast", new After("Sleep /after /after Work"));
            fail();
        } catch (Exception e) {
            assertEquals("Too many /after in String", e.getMessage());
        }

        try {
            assertEquals("/ast", new After("Sleep Work"));
            fail();
        } catch (Exception e) {
            assertEquals("Please use /after to indicate what needs to be done before doing "
                    + "this task", e.getMessage());
        }
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

    @Test
    void testToList() {
        assertEquals("[A][N] Sleep (After: Work)", testAfter.toList());
    }

    @Test
    void testGetDescription() {
        assertEquals("Sleep", testAfter.getDescription());
    }
}