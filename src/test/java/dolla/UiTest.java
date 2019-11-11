package dolla;

import dolla.model.Entry;
import dolla.model.Limit;
import dolla.model.Record;
import dolla.parser.ParserStringList;
import dolla.ui.Ui;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UiTest implements UiTestExpected, ModeStringList, ParserStringList {

    private String actualOutput;
    private OutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream printStream = new PrintStream(outputStream);

    @Test
    public void showWelcomeTest() throws IOException {
        System.setOut(printStream);
        Ui.showWelcome();
        actualOutput = outputStream.toString();
        assertEquals(EXPECTED_WELCOME_MSG.trim(), actualOutput.trim());
        outputStream.flush();
    }

    @Test
    public void validUpdatedDollaModePrinterTest() throws IOException {
        System.setOut(printStream);
        Ui.updatedModePrinter(MODE_DOLLA);
        actualOutput = outputStream.toString();
        assertEquals(EXPECTED_UPDATED_DOLLA_MODE_MSG.trim(), actualOutput.trim());
        outputStream.flush();
    }

    @Test
    public void validUpdatedDebtModePrinterTest() throws IOException {
        System.setOut(printStream);
        Ui.updatedModePrinter(MODE_DEBT);
        actualOutput = outputStream.toString();
        assertEquals(EXPECTED_UPDATED_DEBT_MODE_MSG.trim(), actualOutput.trim());
        outputStream.flush();
    }

    @Test
    public void validUpdatedEntryModePrinterTest() throws IOException {
        System.setOut(printStream);
        Ui.updatedModePrinter(MODE_ENTRY);
        actualOutput = outputStream.toString();
        assertEquals(EXPECTED_UPDATED_ENTRY_MODE_MSG.trim(), actualOutput.trim());
        outputStream.flush();
    }

    @Test
    public void validUpdatedLimitModePrinterTest() throws IOException {
        System.setOut(printStream);
        Ui.updatedModePrinter(MODE_LIMIT);
        actualOutput = outputStream.toString();
        assertEquals(EXPECTED_UPDATED_LIMIT_MODE_MSG.trim(), actualOutput.trim());
        outputStream.flush();

    }

    @Test
    public void validUpdatedShortcutModePrinterTest() throws IOException {
        System.setOut(printStream);
        Ui.updatedModePrinter(MODE_SHORTCUT);
        actualOutput = outputStream.toString();
        assertEquals(EXPECTED_UPDATED_SHORTCUT_MODE_MSG.trim(), actualOutput.trim());
        outputStream.flush();
    }

    @Test
    public void echoAddRecordTest() throws IOException {
        System.setOut(printStream);
        Record record = new Entry(ENTRY_TYPE_E, 500, "1111 sales", LocalDate.parse("2019-11-11"));
        Ui.echoAddRecord(record);
        actualOutput = outputStream.toString();
        assertEquals(EXPECTED_ECHO_RECORD_MSG.trim(), actualOutput.trim());
        outputStream.flush();
    }

    @Test
    public void existingRecordPrinterTest() throws IOException {
        System.setOut(printStream);
        Record record = new Limit(LIMIT_TYPE_B, 10, LIMIT_DURATION_W);
        Ui.existingRecordPrinter(record, MODE_LIMIT);
        actualOutput = outputStream.toString();
        assertEquals(EXPECTED_EXISTING_RECORD_MSG.trim(), actualOutput.trim());
        outputStream.flush();
    }
}
