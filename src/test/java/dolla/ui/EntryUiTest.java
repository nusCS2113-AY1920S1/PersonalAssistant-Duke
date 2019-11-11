package dolla.ui;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntryUiTest implements UiTestExpectedOutput {
    private String actual;
    private OutputStream os = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(os);

    @Test
    public void printInvalidEntryTypeTest() throws IOException {
        System.setOut(ps);
        EntryUi.printInvalidEntryType();
        actual = os.toString();
        assertEquals(ENTRY_UI_EXPECTED_1,actual.trim());
        os.flush();
    }

    @Test
    public void printInvalidEntryFormatErrorTest() throws IOException {
        System.setOut(ps);
        EntryUi.printInvalidEntryFormatError();
        actual = os.toString();
        assertEquals(ENTRY_UI_EXPECTED_2,actual.trim());
        os.flush();
    }
}
