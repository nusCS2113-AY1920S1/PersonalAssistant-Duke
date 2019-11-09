package duke.logic.command.sale;

import org.junit.Test;
import org.junit.jupiter.api.Test;

import static duke.testutil.Assert.assertThrows;

public class AddSaleCommandTest {

    @Test
    public void constructor_nullSale_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSaleCommand(null));
    }
}
