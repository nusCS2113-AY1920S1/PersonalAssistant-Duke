package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.model.food.Food;
import cube.model.promotion.Promotion;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class AddPromotionCommandParserTest {

    @Test
    public void execute_parse_correctly() throws ParserException {
        final String[] inputs = {"promotion","test","-%","20", "-s", "01/12/2019",
                "-e","02/12/2019"};
        Promotion expected = new Promotion("test");
        expected.setDiscount(20);
        expected.setStartDate(new Date(2019 - 1900,12 - 01,01));
        expected.setEndDate(new Date(2019 - 1900,12 - 01,02));

        AddPromotionCommandParser test = new AddPromotionCommandParser();
        test.parse(inputs);
        expected = test.getTempPromotion();
        assertEquals(test.getTempPromotion(), expected);
    }

    @Test
    public void execute_not_enough_parameter() {
        String[] inputs = {"promotion"};
        try {
            new AddPromotionCommandParser().parse(inputs);
            fail("Fail to detect not enough parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.NOT_ENOUGH_PARAMETER, e.getMessage());
        }
    }

    @Test
    public void execute_invalid_parameter() {
        String[] inputs = {"promotion", "test", "-x", "field"};
        try {
            new AddPromotionCommandParser().parse(inputs);
            fail("Fail to detect invalid parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_PARAMETER, e.getMessage());
        }

    }

    @Test
    public void execute_invalid_name() {
        String[] inputs = {"promotion", " ", "-%", "field", "-e", "field"};
        try {
            new AddPromotionCommandParser().parse(inputs);
            fail("Fail to detect empty name");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_NAME, e.getMessage());
        }
    }

    @Test
    public void execute_empty_field() {
        String[] inputs = {"promotion", "test", "-%", "-e"};
        try {
            new AddPromotionCommandParser().parse(inputs);
            fail("Fail to detect empty field");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.EMPTY_FIELD, e.getMessage());
        }
    }

    @Test
    public void execute_repetitive_parameter() {
        String[] inputs = {"promotion", "test", "-%", "50", "-%", "20"};
        try {
            new AddPromotionCommandParser().parse(inputs);
            fail("Fail to detect repetitive parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.REPETITIVE_PARAMETER, e.getMessage());
        }
    }
}