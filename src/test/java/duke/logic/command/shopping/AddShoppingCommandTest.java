package duke.logic.command.shopping;

import org.junit.jupiter.api.Test;

import static duke.testutil.Assert.assertThrows;

public class AddShoppingCommandTest {

    @Test
    public void constructor_nullIngredient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddShoppingCommand(null));
    }
}
