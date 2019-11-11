package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class FindCommandParserTest {

    @Test
    public void execute_not_enough_parameter_only_command() {
        String[] inputs = {"find"};
        try {
            new FindCommandParser().parse(inputs);
            fail("Fail to detect not enough parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.NOT_ENOUGH_PARAMETER, e.getMessage());
        }
    }

    @Test
    public void execute_invalid_number() {
        String[] inputs = {"find","-i", "test"};
        try {
            new FindCommandParser().parse(inputs);
            fail("Fail to detect invalid number");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_INTEGER, e.getMessage());
        }
    }

    @Test
    public void execute_invalid_parameter() {
        String[] inputs = {"find", "-x", "field"};
        try {
            new FindCommandParser().parse(inputs);
            fail("Fail to detect invalid parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_PARAMETER, e.getMessage());
        }

    }

    @Test
    public void execute_repetitive_parameter() {
        String[] inputs = {"find", "-i", "1", "-i", "2"};
        try {
            new FindCommandParser().parse(inputs);
            fail("Fail to detect repetitive parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.REPETITIVE_PARAMETER, e.getMessage());
        }
    }
}