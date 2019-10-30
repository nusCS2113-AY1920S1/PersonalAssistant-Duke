import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * JUnit test class for Duke class
 */
public class DukeTest {
    private final Duke dukeTest = new Duke("data/duketest.json");

    @Test
    public void dummyTest() {
        assertEquals(2, 2);
    }
}
