package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.model.food.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SoldCommandParserTest {

    @Test
    public void execute_parse_correctly() {
        String[] inputs = {"sold","test","-q","20"};
        try {
            new SoldCommandParser().parse(inputs);
        } catch (ParserException e) {
            fail("Fail to parse sold correctly");
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void execute_not_enough_parameter() {
        String[] inputs = {"sold"};
        try {
            new SoldCommandParser().parse(inputs);
            fail("Fail to detect not enough parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.NOT_ENOUGH_PARAMETER, e.getMessage());
        }
    }

    @Test
    public void execute_invalid_parameter() {
        String[] inputs = {"sold", "test", "-x", "field"};
        try {
            new SoldCommandParser().parse(inputs);
            fail("Fail to detect invalid parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_PARAMETER, e.getMessage());
        }

    }

    @Test
    public void execute_invalid_name() {
        String[] inputs = {"sold", " ", "-q", "field"};
        try {
            new SoldCommandParser().parse(inputs);
            fail("Fail to detect empty name");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_NAME, e.getMessage());
        }
    }

    @Test
    public void execute_empty_field() {
        String[] inputs = {"sold", "test", "-q"};
        try {
            new SoldCommandParser().parse(inputs);
            fail("Fail to detect empty field");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.EMPTY_FIELD, e.getMessage());
        }
    }

    @Test
    public void execute_repetitive_parameter() {
        String[] inputs = {"sold", "test", "-q", "1", "-q", "2"};
        try {
            new SoldCommandParser().parse(inputs);
            fail("Fail to detect repetitive parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.REPETITIVE_PARAMETER, e.getMessage());
        }
    }
}