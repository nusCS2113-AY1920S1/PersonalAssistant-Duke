package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LastTest {
    @Test
    public void checkToString() {
        String description = "This is a test Last";
        String duration = "1 hr";
        String expectedResult = "[L][\u2718] This is a test Last (last: 1 hr)";

        Last temp = new Last(description, duration);
        assertEquals(expectedResult, temp.toString());
    }

    @Test
    public void checkDataString() {
        String description = "This is a test Last";
        String duration = "1 hr";
        String expectedResult = "L | 0 | This is a test Last | 1 hr";
        Last temp = new Last(description, duration);
        assertEquals(expectedResult, temp.dataString());
    }
}
