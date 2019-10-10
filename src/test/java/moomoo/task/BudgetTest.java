package moomoo.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetTest {
    @Test
    public void testToString() {
        Budget newBudget = new Budget(5000.50);
        assertEquals("Your budget is: $5000.50", newBudget.toString());
        assertEquals(5000.5, newBudget.getBudget());
        newBudget.setBudget(700.45);
        assertEquals(700.45, newBudget.getBudget());


    }
}