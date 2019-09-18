package duketest.parser;

import duke.exceptions.DukeException;
import duke.parser.Parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void checkParsing() throws DukeException {
        Parser obj = new Parser();
        assertThrows(DukeException.class,() -> obj.parse("gfgregt4e"));
    }
}
