package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class BatchCommandParserTest {

    @Test
    public void execute_not_enough_parameter() {
        String[] inputs = {"batch"};
        try {
            new BatchCommandParser().parse(inputs);
            fail("Fail to detect not enough parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.NOT_ENOUGH_PARAMETER, e.getMessage());
        }
    }

    @Test
    public void execute_invalid_parameter() {
        String[] inputs = {"batch", "-x", "field"};
        try {
            new BatchCommandParser().parse(inputs);
            fail("Fail to detect invalid parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_PARAMETER, e.getMessage());
        }

    }

    @Test
    public void execute_repetitive_parameter() {
        String[] inputs = {"batch", "-i", "A", "-i", "A"};
        try {
            new BatchCommandParser().parse(inputs);
            fail("Fail to detect repetitive parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.REPETITIVE_PARAMETER, e.getMessage());
        }
    }
}