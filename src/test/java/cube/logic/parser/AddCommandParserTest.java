package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.model.food.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class AddCommandParserTest {

    @Test
    public void execute_parse_correctly() throws ParserException {
        final String[] inputs = {"add","test","-t","test","-p","1.1",
                "-s","100"};
        Food expected = new Food("test");
        expected.setType("test");
        expected.setPrice(1.1);
        expected.setStock(100);

        AddCommandParser test = new AddCommandParser();
        test.parse(inputs);
        assertEquals(test.getTempFood(), expected);
    }

    @Test
    public void execute_not_enough_parameter() {
        String[] inputs = {"add"};
        try {
            new AddCommandParser().parse(inputs);
            fail("Fail to detect not enough parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.NOT_ENOUGH_PARAMETER, e.getMessage());
        }
    }

    @Test
    public void execute_invalid_parameter() {
        String[] inputs = {"add", "test", "-x", "field"};
        try {
            new AddCommandParser().parse(inputs);
            fail("Fail to detect invalid parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_PARAMETER, e.getMessage());
        }

    }

    @Test
    public void execute_invalid_name() {
        String[] inputs = {"add", " ", "-t", "field"};
        try {
            new AddCommandParser().parse(inputs);
            fail("Fail to detect empty name");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_NAME, e.getMessage());
        }
    }

    @Test
    public void execute_empty_field() {
        String[] inputs = {"add", "test", "-p"};
        try {
            new AddCommandParser().parse(inputs);
            fail("Fail to detect empty field");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.EMPTY_FIELD, e.getMessage());
        }
    }

    @Test
    public void execute_repetitive_parameter() {
        String[] inputs = {"add", "test", "-t", "A", "-t", "B"};
        try {
            new AddCommandParser().parse(inputs);
            fail("Fail to detect repetitive parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.REPETITIVE_PARAMETER, e.getMessage());
        }
    }
}