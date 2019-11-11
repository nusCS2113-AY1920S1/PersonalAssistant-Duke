package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.model.food.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class UpdateCommandParserTest {

    @Test
    public void execute_parse_correctly() throws ParserException {
        final String[] inputs = {"update","test","-t","test","-p","1.1",
                "-s","100"};
        Food expected = new Food("test");
        expected.setType("test");
        expected.setPrice(1.1);
        expected.setStock(100);

        UpdateCommandParser test = new UpdateCommandParser();
        test.parse(inputs);
        assertEquals(test.getTempFood(), expected);
    }

    @Test
    public void execute_not_enough_parameter() {
        String[] inputs = {"update"};
        try {
            new UpdateCommandParser().parse(inputs);
            fail("Fail to detect not enough parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.NOT_ENOUGH_PARAMETER, e.getMessage());
        }
    }

    @Test
    public void execute_invalid_parameter() {
        String[] inputs = {"update", "test", "-x", "field"};
        try {
            new UpdateCommandParser().parse(inputs);
            fail("Fail to detect invalid parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_PARAMETER, e.getMessage());
        }

    }

    @Test
    public void execute_invalid_name() {
        String[] inputs = {"update", " ", "-t", "field"};
        try {
            new UpdateCommandParser().parse(inputs);
            fail("Fail to detect empty name");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_NAME, e.getMessage());
        }
    }

    @Test
    public void execute_empty_field() {
        String[] inputs = {"update", "test", "-p"};
        try {
            new UpdateCommandParser().parse(inputs);
            fail("Fail to detect empty field");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.EMPTY_FIELD, e.getMessage());
        }
    }

    @Test
    public void execute_repetitive_parameter() {
        String[] inputs = {"update", "test", "-t", "A", "-t", "A"};
        try {
            new UpdateCommandParser().parse(inputs);
            fail("Fail to detect repetitive parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.REPETITIVE_PARAMETER, e.getMessage());
        }
    }
}