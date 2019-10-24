package duke.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoAfterTest {
    @Test
    public void toString_DoAfterTask_DoAfterString() {
        assertEquals("[A][x] Read book (after: Task 1)", new DoAfter("Read book",
                1, 0).toString());
        assertEquals("[A][x] Celebrate My Birthday (after: Task 2)", new DoAfter("Celebrate My Birthday",
                2, 1).toString());
    }
}
