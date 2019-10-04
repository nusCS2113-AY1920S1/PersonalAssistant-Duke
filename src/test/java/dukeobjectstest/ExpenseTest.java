package dukeobjectstest;

import dukeobjects.Expense;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.LocalDateTimeParser;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ExpenseTest {
    @Test
    public void toStringTest() {
        Expense expense = new Expense("45", "train concession");
        String timeNow = new LocalDateTimeParser().toString(LocalDateTime.now());
        Assertions.assertEquals(expense.toString(), "$45.00 train concession " + timeNow);

    }
}
