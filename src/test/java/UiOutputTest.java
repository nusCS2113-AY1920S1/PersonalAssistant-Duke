import exception.DukeException;
import org.junit.jupiter.api.Test;
import ui.ListBox;
import ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UiOutputTest {
    private Ui ui;

    @Test
    void testOutput() {
        ui = new Ui();
        ui.addToOutput("This is a test");
        assertEquals(null + "This is a test\n", ui.getOutput());
    }

    void testException() {

    }
}
