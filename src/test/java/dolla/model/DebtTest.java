package dolla.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author tatayu
public class DebtTest {

    private Debt createNewDebt() {
        return new Debt("owe", "tata", 20, "food",
                LocalDate.parse("2019-01-01"), "");
    }

    @Test
    public void getName() {
        Debt newDebt = createNewDebt();
        assertEquals("tata", newDebt.getName());
    }

    @Test
    public void amountToMoney() {
        Debt newDebt = createNewDebt();
        assertEquals("$20.0", newDebt.amountToMoney());
    }

    @Test
    public void getDescription() {
        Debt newDebt = createNewDebt();
        assertEquals("food", newDebt.getDescription());
    }

    @Test
    public void getDate() {
        Debt newDebt = createNewDebt();
        assertEquals(LocalDate.parse("2019-01-01"), newDebt.getDate());
    }

    @Test
    public void getUserInput() {
        Debt newDebt = createNewDebt();
        assertEquals("owe tata 20.0 food /due 01/01/2019 ", newDebt.getUserInput());
    }

    @Test
    public void formatSave() {
        Debt newDebt = createNewDebt();
        assertEquals("O | tata | 20.0 | food | 01/01/2019 | ", newDebt.formatSave());
    }

    @Test
    public void getRecordDetail() {
        Debt newDebt = createNewDebt();
        assertEquals("[owe] [tata] [$20.0] [food] [/due 01/01/2019] {Tag: }", newDebt.getRecordDetail());
    }
}
