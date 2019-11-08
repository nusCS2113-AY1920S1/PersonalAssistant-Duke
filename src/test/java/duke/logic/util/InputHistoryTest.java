package duke.logic.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class InputHistoryTest {

    private static final String NULL_OBJECT = null;
    private static final String NULL_STRING = null;
    private static final String EMPTY_STRING = "";
    private static final String SPACE_STRING = "  ";

    private static final String FIRST_HISTORY_COMMAND = "addExpense /description phone bill";
    private static final String SECOND_HISTORY_COMMAND = "deleteExpense 1";
    private static final String THIRD_HISTORY_COMMAND = "sortExpense amount";

    @Test
    public void add_nullStringInput_throwsNullPointerException() {
        InputHistory inputHistory = new InputHistory();
        assertThrows(NullPointerException.class, () -> inputHistory.add(NULL_STRING));
    }

    @Test
    public void add_blankStringInput_failure() {
        InputHistory inputHistory = new InputHistory();
        inputHistory.add(EMPTY_STRING);
        inputHistory.add(SPACE_STRING);

        // No history command has been added into inputHistory yet.
        assertEquals(EMPTY_STRING, inputHistory.getLastInput());
    }

    @Test
    public void add_acceptableCommandString_success() {
        InputHistory inputHistory = new InputHistory();
        assertDoesNotThrow(() -> inputHistory.add(FIRST_HISTORY_COMMAND));
    }

    @Test
    public void getLastInput() {
        InputHistory inputHistory = new InputHistory();
        inputHistory.add(FIRST_HISTORY_COMMAND);
        inputHistory.add(SECOND_HISTORY_COMMAND);
        inputHistory.add(THIRD_HISTORY_COMMAND);

        // Navigates to three previous commands.
        assertEquals(THIRD_HISTORY_COMMAND, inputHistory.getLastInput());
        assertEquals(SECOND_HISTORY_COMMAND, inputHistory.getLastInput());
        assertEquals(FIRST_HISTORY_COMMAND, inputHistory.getLastInput());

        // Remains at the earliest history command if the head is reached.
        assertEquals(FIRST_HISTORY_COMMAND, inputHistory.getLastInput());
    }

    @Test
    public void getNextInput() {
        InputHistory inputHistory = new InputHistory();
        inputHistory.add(FIRST_HISTORY_COMMAND);
        inputHistory.add(SECOND_HISTORY_COMMAND);
        inputHistory.add(THIRD_HISTORY_COMMAND);

        // Navigates to three previous commands.
        inputHistory.getLastInput();
        inputHistory.getLastInput();
        inputHistory.getLastInput();

        // Navigates back to two recent commands.
        assertEquals(SECOND_HISTORY_COMMAND, inputHistory.getNextInput());
        assertEquals(THIRD_HISTORY_COMMAND, inputHistory.getNextInput());

        // Clears the output if the tail is exceeded.
        assertEquals(EMPTY_STRING, inputHistory.getNextInput());
    }
}
