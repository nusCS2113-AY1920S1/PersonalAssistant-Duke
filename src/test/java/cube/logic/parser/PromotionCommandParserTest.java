package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


class PromotionCommandParserTest {

    @Test
    public void execute_parse_correctly() {
        String[] inputs = {"profit","-delete","1"};
        try {
            new ProfitCommandParser().parse(inputs);
        } catch (ParserException e) {
            fail("Fail to detect not enough parameter");
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void execute_not_enough_parameter() {
        String[] inputs = {"profit","test"};
        try {
            new ProfitCommandParser().parse(inputs);
            fail("Fail to detect not enough parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.NOT_ENOUGH_PARAMETER, e.getMessage());
        }
    }

}