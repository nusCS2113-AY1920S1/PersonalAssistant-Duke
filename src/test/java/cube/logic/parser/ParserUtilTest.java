package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParserUtilTest {

    @Test
    public void parseDateToString_correctly() {
        String expected = "01/11/2019";
        Date test = new Date(2019 - 1900,11 - 01,1);
        assertEquals(ParserUtil.parseDateToString(test),expected);
    }

    @Test
    void parseStringToDate_correctly() throws ParserException {
        String test = "01/11/2019";
        Date expected = new Date(2019 - 1900,11 - 01,1);
        assertEquals(ParserUtil.parseStringToDate(test),expected);
    }

    @Test
    void parseStringToDate_invalid_format() {
        String test = "01-10-2019";
        try {
            ParserUtil.parseStringToDate(test);
            fail("Fail to detect invalid format");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_DATE_FORMAT, e.getMessage());
        }
    }

    @Test
    void parseStringToDate_negative_date() {
        String test = "-1/11/2019";
        try {
            ParserUtil.parseStringToDate(test);
            fail("Fail to detect invalid format");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_DATE_FORMAT, e.getMessage());
        }
    }

    @Test
    void parseStringToDate_invalid_date() {
        String test = "01/13/2019";
        try {
            ParserUtil.parseStringToDate(test);
            fail("Fail to detect invalid format");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_DATE_FORMAT, e.getMessage());
        }
    }

    @Test
    void findFullString_correctly() {
        String expected = "test this";
        String[] testInputs = {"test","this","-"};
        int testIndex = 0;
        assertEquals(expected,ParserUtil.findFullString(testInputs, testIndex));
    }

    @Test
    void hasInvalidParameters_correctly() {
        String[] testInputs = {"test","this","-"};
        String[] testParameters = {"-"};
        assertFalse(ParserUtil.hasInvalidParameters(testInputs,testParameters));
    }

    @Test
    void hasInvalidParameters_incorrectly() {
        String[] testInputs = {"test","this","-test"};
        String[] testParameters = {"-"};
        assertTrue(ParserUtil.hasInvalidParameters(testInputs,testParameters));
    }

    @Test
    void hasRepetitiveParameters_correctly() {
        String[] testInputs = {"test","this","-"};
        assertFalse(ParserUtil.hasRepetitiveParameters(testInputs));
    }

    @Test
    void hasRepetitiveParameters_incorrectly() {
        String[] testInputs = {"test","this","-","-"};
        assertTrue(ParserUtil.hasRepetitiveParameters(testInputs));
    }

    @Test
    void hasField_correctly() {
        String[] testInputs = {"test","-","this"};
        int testIndex = 2;
        assertTrue(ParserUtil.hasField(testInputs,testIndex));
    }

    @Test
    void hasField_out_of_bound() {
        String[] testInputs = {"test","-"};
        int testIndex = 2;
        assertFalse(ParserUtil.hasField(testInputs,testIndex));
    }

    @Test
    void hasField_no_field() {
        String[] testInputs = {"test","-tag1","-tag2"};
        int testIndex = 2;
        assertFalse(ParserUtil.hasField(testInputs,testIndex));
    }

    @Test
    void isValidNumber_correctly() {
        String test = "1.1";
        assertTrue(ParserUtil.isValidNumber(test));
    }

    @Test
    void isValidNumber_too_small() {
        String test = "-1.1";
        assertFalse(ParserUtil.isValidNumber(test));
    }

    @Test
    void isValidInteger_correctly() {
        String test = "1";
        assertTrue(ParserUtil.isValidInteger(test));
    }

    @Test
    void isValidInteger_too_large() {
        String test = "1000000";
        assertFalse(ParserUtil.isValidInteger(test));
    }

    @Test
    void isValidInteger_is_double() {
        String test = "1.1";
        assertFalse(ParserUtil.isValidInteger(test));
    }

    @Test
    void getTimeZone() {
        TimeZone expected = TimeZone.getTimeZone("GMT+8:00");
        assertEquals(ParserUtil.getTimeZone(),expected);
    }
}