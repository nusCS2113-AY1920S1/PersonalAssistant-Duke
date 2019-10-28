package duchess.parser;

import duchess.exceptions.DuchessException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilTest {
    @Test
    public void parseDateTime_outOfBounds_exceptionThrown() {
        assertThrows(
            DuchessException.class,
            () -> {
                Util.parseDateTime("12/12/2019");
            }
        );
    }

    @Test
    public void parameterize() {
        String input = "event something happening /at a b /to c d /hi";
        Map<String, String> parameters = Util.parameterize(input);

        assertEquals("event", parameters.get("command"));
        assertEquals("something happening", parameters.get("general"));
        assertEquals("a b", parameters.get("at"));
        assertEquals("c d", parameters.get("to"));
        assertEquals(null, parameters.get("hi"));
    }
}
