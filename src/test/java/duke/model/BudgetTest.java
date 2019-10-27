package duke.model;

import duke.exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

public class BudgetTest {
    @TempDir
    File userDirectory;

    @Test
    public void testBasicOperations() throws IOException, DukeException {
        Budget budget = new Budget(BigDecimal.ZERO, new HashMap<>());
        budget.setMonthlyBudget(BigDecimal.TEN);
        Assertions.assertEquals("10", budget.getMonthlyBudgetString());
    }

}
