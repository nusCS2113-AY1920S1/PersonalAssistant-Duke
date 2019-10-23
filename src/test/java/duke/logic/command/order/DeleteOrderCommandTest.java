package duke.logic.command.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeleteOrderCommandTest {

    @Test
    public void deleteOrder_nullIndex_throwNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
            {
                DeleteOrderCommand command = new DeleteOrderCommand(null);
            }
        );
    }

}
