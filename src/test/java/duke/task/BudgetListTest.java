package duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

//@@author maxxyx96
public class BudgetListTest {
    @Test
    void newBudgetListTest() {
        BudgetList budgetList = new BudgetList();
        assertEquals(0, budgetList.getBudget());
    }

    @Test
    void addBudgetTest() {
        BudgetList budgetList = new BudgetList();
        budgetList.addToBudget(2113);
        assertEquals(2113, budgetList.getBudget());
    }

    @Test
    void subtractBudgetTest() {
        BudgetList budgetList = new BudgetList();
        budgetList.addToBudget(-2113);
        assertEquals(-2113, budgetList.getBudget());
    }

    @Test
    void resetBudgetTest() {
        BudgetList budgetList = new BudgetList();
        budgetList.addToBudget(2113);
        budgetList.resetBudget(0);
        assertEquals(0, budgetList.getBudget());
    }
}
//@@author