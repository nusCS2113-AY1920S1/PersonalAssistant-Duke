package duke.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetViewTest {

    private static final int TEST_VIEW = 1;
    private static final String TEST_CATEGORY = "test category";

    @Test
    void testPositive() {
        Map<Integer, String> testBudgetViewCategory  = new HashMap<>();
        BudgetView testBudgetView = new BudgetView(testBudgetViewCategory);
        testBudgetView.setBudgetView(TEST_VIEW,TEST_CATEGORY);
        assertEquals(testBudgetView.getBudgetViewCategory(), testBudgetViewCategory);
    }
}
