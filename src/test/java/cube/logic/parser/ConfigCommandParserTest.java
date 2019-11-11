package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ConfigCommandParserTest {

    @Test
    public void parse_view_correctly() {
        String[] inputs = {"config"};
        try {
            new ConfigCommandParser().parse(inputs);
        } catch (ParserException e) {
            fail("Fail to parse view correctly");
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void parse_log_correctly() {
        String[] inputs = {"config","LOG", "-c", "1"};
        try {
            new ConfigCommandParser().parse(inputs);
        } catch (ParserException e) {
            fail("Fail to parse log correctly");
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void parse_ui_correctly() {
        String[] inputs = {"config","UI","-h","700"};
        try {
            new ConfigCommandParser().parse(inputs);
        } catch (ParserException e) {
            fail("Fail to parse ui correctly");
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void execute_height_out_of_range() {
        String[] inputs = {"config","UI","-h","300"};
        String testParameter = "-h";
        double maxHeight = 1000.0;
        double minHeight = 600.0;
        try {
            new ConfigCommandParser().parse(inputs);
            fail("Fail to catch height out of range.");
        } catch (ParserException e) {
            assertEquals(String.format(ParserErrorMessage.INVALID_PARAM_RANGE,
                    testParameter,minHeight,maxHeight), e.getMessage());
        }
    }

    @Test
    public void execute_invalid_parameter() {
        String[] inputs = {"config", "-x", "field"};
        try {
            new ConfigCommandParser().parse(inputs);
            fail("Fail to detect invalid parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_PARAMETER, e.getMessage());
        }

    }

    @Test
    public void execute_empty_field() {
        String[] inputs = {"config", "UI", "-h"};
        try {
            new ConfigCommandParser().parse(inputs);
            fail("Fail to detect empty field");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.EMPTY_FIELD, e.getMessage());
        }
    }

    @Test
    public void execute_repetitive_parameter() {
        String[] inputs = {"config", "UI", "-h", "1", "-h", "1"};
        try {
            new ConfigCommandParser().parse(inputs);
            fail("Fail to detect repetitive parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.REPETITIVE_PARAMETER, e.getMessage());
        }
    }

}