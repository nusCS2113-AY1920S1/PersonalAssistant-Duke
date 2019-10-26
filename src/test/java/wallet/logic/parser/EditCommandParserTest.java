package wallet.logic.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import wallet.model.contact.Contact;
import wallet.model.record.Category;
import wallet.model.record.Expense;

import static org.junit.jupiter.api.Assertions.*;

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

    //@@author Xdecosee
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "1", "garbage"})
    public void parseContact_invalidInput_true(String input) {
        EditCommandParser parser = new EditCommandParser();
        Contact contact = parser.parseContact(input);
        assertNull(contact, "Return Contact should be null:");
    }

    @ParameterizedTest
    @ValueSource(strings = { "6 /n /d /p", "6 /n   /d   /p  ",
        "6 /n John /p 7183 /d brother 123@abc.com", "8 /n Test /d /d /doctor"})
    public void parseContact_validInput_success(String input) {
        EditCommandParser parser = new EditCommandParser();
        Contact contact = parser.parseContact(input);
        Contact match = null;
        if(input.equals("6 /n /d /p")){
            match = new Contact(null, "", "");
            match.setId(6);
        } else if (input.equals("6 /n   /d   /p  ")){
            match = new Contact(null, "", "");
            match.setId(6);
        } else if (input.equals( "6 /n John /p 7183 /d brother 123@abc.com")){
            match = new Contact("John", "brother 123@abc.com", "7183");
            match.setId(6);
        } else if (input.equals("8 /n Test /d /d /doctor")){
            match = new Contact("Test", "/d /doctor", null);
            match.setId(8);
        }
        Contact finalMatch = match;
        assertAll("Returned Contact should contain correct input values",
                () -> assertEquals(finalMatch.getId(), contact.getId()),
                () -> assertEquals(finalMatch.getName(), contact.getName()),
                () -> assertEquals(finalMatch.getDetail(), contact.getDetail()),
                () -> assertEquals(finalMatch.getPhoneNum(), contact.getPhoneNum())
        );
    }
}
