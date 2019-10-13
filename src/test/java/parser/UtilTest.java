package parser;

import duchess.exceptions.DuchessException;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilTest {
    @Test
    public void parseDateTime_outOfBounds_exceptionThrown() {
        assertThrows(
            DuchessException.class,
            () -> {
                Util.parseDateTime(List.of("12/12/2019"), 0);
            }
        );
    }
}
