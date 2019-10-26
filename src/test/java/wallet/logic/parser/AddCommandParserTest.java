package wallet.logic.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import wallet.model.contact.Contact;
import wallet.model.record.Category;
import wallet.model.record.Expense;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AddCommandParserTest {
    //@@author kyang96
    @Test
    public void parseExpenseValidInputSuccess() {
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
    public void parseExpenseValidRecurringInputSuccess() {
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

    //@@author Xdecosee
    /**
     * This method test a series of wrong contact command inputs.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "/d /p"})
    public void parseContactInvalidInputTrue(String input) {
        AddCommandParser parser = new AddCommandParser();
        Contact contact = parser.parseContact(input);
        assertNull(contact, "Return Contact should be null:");
    }

    /**
     * This method test a series of correct contact command inputs.
     */
    @ParameterizedTest
    @ValueSource(strings = {"Mary /d /p  ", "Mary Tan", "Mary /p 9728 1831 /d sister", "Test /p /p"})
    public void parseContactValidInputSuccess(String input) {
        AddCommandParser parser = new AddCommandParser();
        Contact contact = parser.parseContact(input);
        Contact match = null;
        if ("Mary /d /p  ".equals(input)) {
            match = new Contact("Mary", null, null);
        } else if ("Mary Tan".equals(input)) {
            match = new Contact("Mary Tan", null, null);
        } else if ("Mary /p 9728 1831 /d sister".equals(input)) {
            match = new Contact("Mary", "sister", "9728 1831");
        } else if ("Test /p /p".equals(input)) {
            match = new Contact("Test", null, "/p");
        }
        Contact finalMatch = match;
        assertAll("Returned Contact should contain correct input values",
            () -> assertEquals(finalMatch.getName(), contact.getName()),
            () -> assertEquals(finalMatch.getDetail(), contact.getDetail()),
            () -> assertEquals(finalMatch.getPhoneNum(), contact.getPhoneNum())
        );
    }

}
