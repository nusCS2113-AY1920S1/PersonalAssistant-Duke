package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ReminderCommandParserTest {

    @Test
    public void execute_parse_correctly() {
        String[] inputs = {"reminder","-s","20"};
        try {
            new ReminderCommandParser().parse(inputs);
        } catch (ParserException e) {
            fail("Fail to parse reminder correctly");
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void execute_invalid_parameter() {
        String[] inputs = {"reminder", "-x", "field"};
        try {
            new ReminderCommandParser().parse(inputs);
            fail("Fail to detect invalid parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_PARAMETER, e.getMessage());
        }

    }

    @Test
    public void execute_invalid_integer() {
        String[] inputs = {"reminder", "-d", "1.1"};
        try {
            new ReminderCommandParser().parse(inputs);
            fail("Fail to detect invalid integer");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_NUMBER, e.getMessage());
        }
    }

    @Test
    public void execute_empty_field() {
        String[] inputs = {"reminder", "-s", "-d"};
        try {
            new ReminderCommandParser().parse(inputs);
            fail("Fail to detect empty field");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.EMPTY_FIELD, e.getMessage());
        }
    }

    @Test
    public void execute_repetitive_parameter() {
        String[] inputs = {"reminder", "-s", "1", "-s", "2"};
        try {
            new ReminderCommandParser().parse(inputs);
            fail("Fail to detect repetitive parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.REPETITIVE_PARAMETER, e.getMessage());
        }
    }
}