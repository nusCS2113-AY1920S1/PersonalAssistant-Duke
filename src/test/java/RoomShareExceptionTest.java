package java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import Enums.ExceptionType;

public class RoomShareExceptionTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private static final RoomShareExceptionTest exeptionTest = new RoomShareExceptionTest();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testEmptylist() {
        new RoomShareException(ExceptionType.emptylist);
        assertEquals("List is empty", outContent.toString());
    }

    @Test
    public void testTimeclash() {
        new RoomShareException(ExceptionType.timeclash);
        assertEquals("Time Clash Detected", outContent.toString());
    }

    @Test
    public void testWrongFormat() {
        new RoomShareException(ExceptionType.wrongFormat);
        assertEquals("Wrong Format Detected", outContent.toString());
    }

    @Test
    public void testWrongPrioirty() {
        new RoomShareException(ExceptionType.wrongPriority);
        assertEquals("Wrong Priority Detected", outContent.toString());
    }

    @Test
    public void testOutofBounds() {
        new RoomShareException(ExceptionType.outOfBounds);
        assertEquals("Index is out of Bounds!", outContent.toString());
    }

    @Test
    public void testDefault() {
        new RoomShareException(ExceptionType.test);
        assertEquals("Anomaly Detected", outContent.toString());
    }
}



