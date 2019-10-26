package wallet.logic.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import wallet.model.contact.Contact;
import wallet.model.record.Category;
import wallet.model.record.Expense;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AddCommandParserTest {
    //@@author kyang96
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

    //@@author Xdecosee
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "/d /p"})
    public void parseContact_invalidInput_true(String input) {
        AddCommandParser parser = new AddCommandParser();
        Contact contact = parser.parseContact(input);
        assertNull(contact, "Return Contact should be null:");
    }

    @ParameterizedTest
    @ValueSource(strings = { "Mary /d /p  ", "Mary Tan", "Mary /p 9728 1831 /d sister", "Test /p /p"})
    public void parseContact_validInput_success(String input) {
        AddCommandParser parser = new AddCommandParser();
        Contact contact = parser.parseContact(input);
        Contact match = null;
        if(input.equals("Mary /d /p  ")){
            match = new Contact("Mary", null, null);
        } else if (input.equals("Mary Tan")){
            match = new Contact("Mary Tan", null, null);
        } else if (input.equals( "Mary /p 9728 1831 /d sister")){
            match = new Contact("Mary", "sister", "9728 1831");
        } else if (input.equals("Test /p /p")){
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
