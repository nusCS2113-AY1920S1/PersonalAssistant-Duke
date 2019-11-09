package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class InputValidatorTest {

    private final String emptyInput = "   ";
    private final String emptyInput2 = "";
    private final String nonEmptyInput1 = "abc";
    private final String nonEmptyInput2 = "calorie";
    private final String nonNumericInput = "@#$%^";
    private final String nonNumercInput2 = "1bcjeu";
    private final String numericInput = "1.23";
    private final String numericInput2 = "12345678";

    //test whether exception is thrown if user input is empty
    @Test
    public void emptyInputTest() {
        try {
            InputValidator.validate(emptyInput);
            InputValidator.validate(emptyInput2);
            fail("No emptyInput exception is thrown");
        } catch (ProgramException e) {
            assertTrue(true);
        }
    }

    //test that exception is not thrown if user input is not empty
    @Test
    public void nonEmptyInputTest() {
        try {
            InputValidator.validate(nonEmptyInput1);
            InputValidator.validate(nonEmptyInput2);
        } catch (ProgramException e) {
            fail("Exception is thrown although user input is not empty");
        }
    }

    //test whether exception is thrown if the input is not numeric
    @Test
    public void nonNumericInputTest() {
        try {
            InputValidator.validateAmount(nonNumericInput);
            InputValidator.validateAmount(nonNumercInput2);
            fail("No nonNumeric exception is thrown");
        } catch (ProgramException e) {
            assertTrue(true);
        }
    }

    //test that exception is not thrown if the input is numeric
    @Test
    public void numericInputTest() {
        try {
            InputValidator.validateAmount(numericInput);
            InputValidator.validateAmount(numericInput2);
        } catch (ProgramException e) {
            fail("Exception is thrown although user input is numeric");
        }
    }
}
