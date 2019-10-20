import CustomExceptions.RoomShareException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import Enums.ExceptionType;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RoomShareExceptionTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeAll
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testEmptylist() {
        new RoomShareException(ExceptionType.emptyList);
        assertEquals("List is empty", outContent.toString());
    }

    @Test
    public void testTimeclash() {
        new RoomShareException(ExceptionType.timeClash);
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



