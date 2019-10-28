import dolla.task.Debt;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author tatayu
public class DebtTest {

    private Debt createNewDebt() {
        return new Debt("owe", "tata", 20, "food", LocalDate.parse("2019-01-01"));
    }

    @Test
    void getName() {
        Debt newDebt = createNewDebt();
        assertEquals("tata", newDebt.getName());
    }

    @Test
    void amountToMoney() {
        Debt newDebt = createNewDebt();
        assertEquals("$20.0", newDebt.amountToMoney());
    }

    @Test
    void getDescription() {
        Debt newDebt = createNewDebt();
        assertEquals("food", newDebt.getDescription());
    }

    @Test
    void getDate() {
        Debt newDebt = createNewDebt();
        assertEquals(LocalDate.parse("2019-01-01"), newDebt.getDate());
    }

    @Test
    void getUserInput() {
        Debt newDebt = createNewDebt();
        assertEquals("owe tata 20.0 food /due 01/01/2019", createNewDebt().getUserInput());
    }

    @Test
    void formatSave() {
        Debt newDebt = createNewDebt();
        assertEquals("O | tata | 20.0 | food | 01/01/2019", createNewDebt().formatSave());
    }

    @Test
    void getRecordDetail() {
        Debt newDebt = createNewDebt();
        assertEquals("[owe] [tata] [$20.0] [food] [/due 01/01/2019]", createNewDebt().getRecordDetail());
    }
}
