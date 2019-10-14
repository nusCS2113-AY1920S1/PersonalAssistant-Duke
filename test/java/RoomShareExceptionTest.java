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
    public void testException() {
        new RoomShareException(ExceptionType.emptylist);
        assertEquals("List is empty", outContent.toString());
    }


}



