import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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