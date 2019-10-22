package wallet.logic.parser;

import org.junit.jupiter.api.Test;
import wallet.model.record.Category;
import wallet.model.record.Expense;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCommandParserTest {
    @Test
    public void parseExpense_validInput_success() {
        AddCommandParser parser = new AddCommandParser();
        String input = "Lunch $10 Food /on 11/11/2019";
        Expense expense = parser.parseExpense(input);
        assertAll("Expense should contain correct input values",
            () -> assertEquals("Lunch", expense.getDescription()),
            () -> assertEquals(LocalDate.parse("2019-11-11"), expense.getDate()),
            () -> assertEquals(10.0, expense.getAmount()),
            () -> assertEquals(Category.FOOD, expense.getCategory()),
            () -> assertEquals(false, expense.isRecurring()),
            () -> assertEquals(null, expense.getRecFrequency())
        );
    }

    @Test
    public void parseExpense_validRecurringInput_success() {
        AddCommandParser parser = new AddCommandParser();
        String input = "Phone Bill $49 Bills /on 05/10/2019 /r monthly";
        Expense expense = parser.parseExpense(input);
        assertAll("Expense should contain correct input values",
            () -> assertEquals("Phone Bill", expense.getDescription()),
            () -> assertEquals(LocalDate.parse("2019-10-05"), expense.getDate()),
            () -> assertEquals(49.0, expense.getAmount()),
            () -> assertEquals(Category.BILLS, expense.getCategory()),
            () -> assertEquals(true, expense.isRecurring()),
            () -> assertEquals("MONTHLY", expense.getRecFrequency())
        );
    }
}
