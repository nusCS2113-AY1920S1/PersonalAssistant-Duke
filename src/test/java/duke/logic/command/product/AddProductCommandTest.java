package duke.logic.command.product;

import org.junit.jupiter.api.Test;

import static duke.testutil.Assert.assertThrows;

public class AddProductCommandTest {

    @Test
    public void constructor_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProductCommand(null));
    }
}
