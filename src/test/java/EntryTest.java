import dolla.task.Entry;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author omupenguin
public class EntryTest {

    private Entry createNewEntry() {
        return new Entry("expense", 100, "Expense Description",
                LocalDate.parse("2001-12-03"));
    }

    @Test
    void amountToMoney() {
        Entry newEntry = createNewEntry();
        assertEquals("$100.0", newEntry.amountToMoney());
    }

    @Test
    void getLogText() {
        Entry newEntry = createNewEntry();
        assertEquals("[expense] [$100.0] [Expense Description] [/on 03/12/2001]",
                newEntry.getLogText());
    }

    @Test
    void formatSave() {
        Entry newEntry = createNewEntry();
        assertEquals("E | 100.0 | Expense Description | 03/12/2001", newEntry.formatSave());
    }

    @Test
    void getDescription() {
        Entry newEntry = createNewEntry();
        assertEquals("Expense Description", newEntry.getDescription());
    }

    @Test
    void getUserInput() {
        Entry newEntry = createNewEntry();
        assertEquals("expense 100.0 Expense Description /on 03/12/2001", newEntry.getUserInput());
    }
}
