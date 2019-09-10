import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    void checkParsing() throws DukeException {
        Parser obj = new Parser();
        assertThrows(DukeException.class,()-> obj.parse("gfgregt4e"));
    }
}
