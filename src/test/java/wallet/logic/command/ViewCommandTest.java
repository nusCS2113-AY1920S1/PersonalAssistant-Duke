//@@author matthewng1996

package wallet.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.model.Wallet;
import wallet.model.record.Budget;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.RecurrenceRate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewCommandTest {
    private static Wallet testWallet = new Wallet();

    /**
     * Set up test wallet by adding budget.
     */
    @BeforeAll
    public static void setUp() {
        testWallet.getBudgetList().addBudget(new Budget(1000, 10, 2019));
    }

    /**
     * Test for viewing existing budget via view command.
     */
    @Test
    public void executeViewBudget() {
        ViewCommand viewCommand = new ViewCommand("budget", "10/2019");
        viewCommand.execute(testWallet);
        assertEquals(1000, testWallet.getBudgetList().getBudgetList().get(0).getAmount());
    }

    //@@author kyang96
    @Test
    public void getCategoryMap_populatedExpenseList() {
        ArrayList<Expense> expenseList = new ArrayList<>();
        Expense e1 = new Expense("Lunch", LocalDate.parse("2019-10-01"), 3,
                Category.FOOD, false, RecurrenceRate.NO);
        Expense e2 = new Expense("Dinner", LocalDate.parse("2019-10-15"), 5,
                Category.FOOD, false, RecurrenceRate.NO);
        Expense e3 = new Expense("Lunch", LocalDate.parse("2019-10-31"), 10,
                Category.FOOD, false, RecurrenceRate.NO);
        Expense e4 = new Expense("Shirt", LocalDate.parse("2019-10-10"), 20,
                Category.SHOPPING, false, RecurrenceRate.NO);
        Expense e5 = new Expense("Train Concession", LocalDate.parse("2019-10-05"), 50,
                Category.TRANSPORT, false, RecurrenceRate.NO);
        Expense e6 = new Expense("Phone Bill", LocalDate.parse("2019-10-25"), 40,
                Category.BILLS, false, RecurrenceRate.NO);
        expenseList.add(e1);
        expenseList.add(e2);
        expenseList.add(e3);
        expenseList.add(e4);
        expenseList.add(e5);
        expenseList.add(e6);
        HashMap<Category, ArrayList<Expense>> testMap = new HashMap<>();
        ArrayList<Expense> foodList = new ArrayList<>();
        foodList.add(e1);
        foodList.add(e2);
        foodList.add(e3);
        ArrayList<Expense> shoppingList = new ArrayList<>();
        shoppingList.add(e4);
        ArrayList<Expense> transportList = new ArrayList<>();
        transportList.add(e5);
        ArrayList<Expense> billList = new ArrayList<>();
        billList.add(e6);
        testMap.put(Category.FOOD, foodList);
        testMap.put(Category.SHOPPING, shoppingList);
        testMap.put(Category.TRANSPORT, transportList);
        testMap.put(Category.BILLS, billList);

        ViewCommand command = new ViewCommand();
        HashMap<Category, ArrayList<Expense>> categoryMap = command.getCategoryMap(expenseList, 10, 2019);
        assertTrue(testMap.equals(categoryMap));
    }

    @Test
    public void getCategoryMap_emptyExpenseList() {
        ArrayList<Expense> expenseList = new ArrayList<>();
        HashMap<Category, ArrayList<Expense>> testMap = new HashMap<>();
        ViewCommand command = new ViewCommand();
        HashMap<Category, ArrayList<Expense>> categoryMap = command.getCategoryMap(expenseList, 10, 2019);
        assertTrue(testMap.equals(categoryMap));
    }
}
