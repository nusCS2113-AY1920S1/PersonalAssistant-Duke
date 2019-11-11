package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


class PromotionCommandParserTest {

    @Test
    public void execute_parse_correctly() {
        String[] inputs = {"promotion","-delete","1"};
        try {
            new PromotionCommandParser().parse(inputs);
        } catch (ParserException e) {
            System.out.println(e.getMessage());
            fail("fail to execute correctly");
        }
    }

    @Test
    public void execute_not_enough_parameter() {
        String[] inputs = {"promotion","test"};
        try {
            new PromotionCommandParser().parse(inputs);
            fail("Fail to detect not enough parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.NOT_ENOUGH_PARAMETER, e.getMessage());
        }
    }

}