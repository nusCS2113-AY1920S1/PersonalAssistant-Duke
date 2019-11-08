package duke.logic.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AutoCompleterTest {

    private static final String NULL_OBJECT = null;
    private static final String NOT_NULL_STRING_INPUT = "dummy";

    private static final String INPUT_WITH_ADD_PREFIX = "add";
    private static final String PREFIX_ADD_FIRST_OPTION = "addExpense";
    private static final String PREFIX_ADD_SECOND_OPTION = "addPayment";
    private static final String PREFIX_ADD_THIRD_OPTION = "addBudget";

    private static final String PARAMETER_TO_COMPLETE = "addExpense /ti";
    private static final String PARAMETER_COMPLETED = "addExpense /time";

    private static final String PARAMETER_TO_PRODUCE = "addExpense 10 ";
    private static final String PARAMETER_PRODUCED_FIRST_OPTION = "addExpense 10 /recurring";
    private static final String PARAMETER_PRODUCED_SECOND_OPTION = "addExpense 10 /description";

    private static final String NOT_IDENTIFIABLE_FRAGMENT = "arbitrary";
    private static final String DUMMY_BEFORE_PARAMETER_NAME = "addPayment 10 dummy/d";
    private static final String INCORRECT_COMMAND_NAME = "addDummy 10 ";
    private static final String NO_SPACE_AT_END = "changePayment 60";
    private static final String EMPTY_STRING = "";

    private static final String SPACES_AT_FIRST = "    addE";
    private static final String SPACES_AT_FIRST_NO_PROBLEM = "    addExpense";

    @Test
    public void receiveTest_nullStringInput_throwsNullPointerException() {
        AutoCompleter autoCompleter = new AutoCompleter();
        assertThrows(NullPointerException.class, () -> autoCompleter.receiveText(NULL_OBJECT));
    }

    @Test
    public void receiveTest_acceptableStringInput_success() {
        AutoCompleter autoCompleter = new AutoCompleter();
        assertDoesNotThrow(() -> autoCompleter.receiveText(NOT_NULL_STRING_INPUT));
    }

    @Test
    public void getFullComplement() {
        AutoCompleter autoCompleter = new AutoCompleter();

        // Completes "add" to "addExpense".
        autoCompleter.receiveText(INPUT_WITH_ADD_PREFIX);
        assertEquals(PREFIX_ADD_FIRST_OPTION, autoCompleter.getFullComplement());

        // Iterates from "addExpense" to "addPayment".
        autoCompleter.receiveText(PREFIX_ADD_FIRST_OPTION);
        assertEquals(PREFIX_ADD_SECOND_OPTION, autoCompleter.getFullComplement());

        // Iterates from "addPayment" to "addBudget".
        autoCompleter.receiveText(PREFIX_ADD_SECOND_OPTION);
        assertEquals(PREFIX_ADD_THIRD_OPTION, autoCompleter.getFullComplement());

        // Iterates from "addBudget" to "addExpense".
        autoCompleter.receiveText(PREFIX_ADD_THIRD_OPTION);
        assertEquals(PREFIX_ADD_FIRST_OPTION, autoCompleter.getFullComplement());

        // Completes the parameter name "/ti" to "/time".
        autoCompleter.receiveText(PARAMETER_TO_COMPLETE);
        assertEquals(PARAMETER_COMPLETED, autoCompleter.getFullComplement());

        // Produces the first option "/recurring".
        autoCompleter.receiveText(PARAMETER_TO_PRODUCE);
        assertEquals(PARAMETER_PRODUCED_FIRST_OPTION, autoCompleter.getFullComplement());

        // Produces the second option "/description".
        autoCompleter.receiveText(PARAMETER_PRODUCED_FIRST_OPTION);
        assertEquals(PARAMETER_PRODUCED_SECOND_OPTION, autoCompleter.getFullComplement());

        // No complement is applied to not identifiable input.
        autoCompleter.receiveText(NOT_IDENTIFIABLE_FRAGMENT);
        assertEquals(NOT_IDENTIFIABLE_FRAGMENT, autoCompleter.getFullComplement());

        // No complement is applied to parameter name if dummy exists before it.
        autoCompleter.receiveText(DUMMY_BEFORE_PARAMETER_NAME);
        assertEquals(DUMMY_BEFORE_PARAMETER_NAME, autoCompleter.getFullComplement());

        // No parameter name is produced if the command name is incorrect.
        autoCompleter.receiveText(INCORRECT_COMMAND_NAME);
        assertEquals(INCORRECT_COMMAND_NAME, autoCompleter.getFullComplement());

        // No parameter is produced if the input does not end with space.
        autoCompleter.receiveText(NO_SPACE_AT_END);
        assertEquals(NO_SPACE_AT_END, autoCompleter.getFullComplement());

        // No change is applied if the input is empty.
        autoCompleter.receiveText(EMPTY_STRING);
        assertEquals(EMPTY_STRING, autoCompleter.getFullComplement());

        // Spaces at the start of the input do not affect auto-complete.
        autoCompleter.receiveText(SPACES_AT_FIRST);
        assertEquals(SPACES_AT_FIRST_NO_PROBLEM, autoCompleter.getFullComplement());
    }

}
