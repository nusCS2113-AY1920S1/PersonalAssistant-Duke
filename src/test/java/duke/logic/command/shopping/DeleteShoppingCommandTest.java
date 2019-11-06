package duke.logic.command.shopping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeleteShoppingCommandTest {

    @Test
    public void deleteIngredient_nullIndex_throwNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            DeleteShoppingCommand command = new DeleteShoppingCommand(null);
        });
    }
}
