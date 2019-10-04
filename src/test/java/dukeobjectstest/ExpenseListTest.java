package dukeobjectstest;

import dukeobjects.Expense;
import dukeobjects.ExpenseList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.LocalDateTimeParser;

import java.time.LocalDateTime;

public class ExpenseListTest {

    @Test
    public void expenseListAddTest() {
        Expense expense = new Expense("45", "train concession");
        String timeNow = new LocalDateTimeParser().toString(LocalDateTime.now());
        ExpenseList expenseList = new ExpenseList();
        expenseList.add(expense);
        Assertions.assertEquals(expenseList.getSize(), 1);
        Assertions.assertEquals(expenseList.getExpense(0).toString(), "$45.00 train concession " + timeNow);
        Expense anotherExpense = new Expense("52.50", "bus concession");
        expenseList.add(anotherExpense);
        Assertions.assertEquals(expenseList.getSize(), 2);
        Assertions.assertEquals(expenseList.getExpense(1).toString(), "$52.50 bus concession " + timeNow);
    }
}
