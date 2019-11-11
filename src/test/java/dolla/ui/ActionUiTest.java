package dolla.ui;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionUiTest implements UiTestExpectedOutput {
    private String actual;
    private OutputStream os = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(os);

    @Test
    public void printActionMessageTest() throws IOException {
        System.setOut(ps);
        ActionUi.printActionMessage("undo");
        ActionUi.printActionMessage("redo");
        actual = os.toString();
        assertEquals(ACTION_UI_EXPECTED_1, actual.trim());
        os.flush();
    }

    @Test
    public void printEmptyStackErrorTest() throws IOException {
        System.setOut(ps);
        ActionUi.printEmptyStackError("undo");
        ActionUi.printEmptyStackError("redo");
        actual = os.toString();
        assertEquals(ACTION_UI_EXPECTED_2, actual.trim());
        os.flush();
    }


}
