package duke.dukeobject;

import duke.exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class BudgetTest {
    @TempDir
    File userDirectory;

    @Test
    public void testBasicOperations() throws IOException, DukeException {
        Budget budget = new Budget(userDirectory);
        budget.setMonthlyBudget(BigDecimal.TEN);
        Assertions.assertEquals("$10.00", budget.getMonthlyBudgetString());
    }

}
