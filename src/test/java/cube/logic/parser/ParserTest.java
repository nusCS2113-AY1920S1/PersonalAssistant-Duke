package cube.logic.parser;


import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParserTest {

    @Test
    public void test_invalid_command() {
        String test = "test";
        try {
            new Parser().parse(test);
            fail("Fail to detect invalid command");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_COMMAND, e.getMessage());
        }
    }

}