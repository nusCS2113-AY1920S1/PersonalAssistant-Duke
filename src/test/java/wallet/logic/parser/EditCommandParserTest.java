package wallet.logic.parser;

import org.junit.jupiter.api.Test;
import wallet.model.record.Category;
import wallet.model.record.Expense;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditCommandParserTest {
    //@@author kyang96
    @Test
    public void parseExpense_validInput_success() {
        EditCommandParser parser = new EditCommandParser();
        String input = "2 /d Supper /a 10 /c Others";
        Expense expense = parser.parseExpense(input);
        assertAll("Expense should contain edited values",
            () -> assertEquals(2, expense.getId()),
            () -> assertEquals("Supper", expense.getDescription()),
            () -> assertEquals(null, expense.getDate()),
            () -> assertEquals(10.0, expense.getAmount()),
            () -> assertEquals(Category.OTHERS, expense.getCategory()),
            () -> assertEquals(false, expense.isRecurring()),
            () -> assertEquals("", expense.getRecFrequency())
        );
    }

    @Test
    public void parseExpense_validRecurringInput_success() {
        EditCommandParser parser = new EditCommandParser();
        String input = "2 /d Supper /a 10 /c Others /r Daily";
        Expense expense = parser.parseExpense(input);
        assertAll("Expense should contain edited values",
            () -> assertEquals(2, expense.getId()),
            () -> assertEquals("Supper", expense.getDescription()),
            () -> assertEquals(null, expense.getDate()),
            () -> assertEquals(10.0, expense.getAmount()),
            () -> assertEquals(Category.OTHERS, expense.getCategory()),
            () -> assertEquals(true, expense.isRecurring()),
            () -> assertEquals("DAILY", expense.getRecFrequency())
        );
    }
}
