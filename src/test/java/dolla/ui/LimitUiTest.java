package dolla.ui;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LimitUiTest implements UiTestExpectedOutput {
    private String actual;
    private OutputStream os = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(os);

    @Test
    public void invalidLimitTypePrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.invalidLimitTypePrinter();
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_1,actual.trim());
        os.flush();
    }

    @Test
    public void invalidLimitDurationPrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.invalidLimitDurationPrinter();
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_2,actual.trim());
        os.flush();
    }

    @Test
    public void invalidSetCommandPrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.invalidSetCommandPrinter();
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_3,actual.trim());
        os.flush();
    }

    @Test
    public void invalidShowRemainingLimitPrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.invalidShowRemainingLimitPrinter();
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_4,actual.trim());
        os.flush();
    }

    @Test
    public void noExistingLimitPrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.noExistingLimitPrinter("budget");
        LimitUi.noExistingLimitPrinter("saving");
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_5,actual.trim());
        os.flush();
    }

    @Test
    public void exceededBudgetPrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.exceededBudgetPrinter(10,"daily");
        LimitUi.exceededBudgetPrinter(20,"weekly");
        LimitUi.exceededBudgetPrinter(30,"monthly");
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_6,actual.trim());
        os.flush();
    }

    @Test
    public void reachedBudgetPrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.reachedBudgetPrinter("weekly");
        LimitUi.reachedBudgetPrinter("daily");
        LimitUi.reachedBudgetPrinter("monthly");
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_7,actual.trim());
        os.flush();
    }

    @Test
    public void noSavingsPrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.noSavingsPrinter();
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_8,actual.trim());
        os.flush();
    }

    @Test
    public void reachedSavingPrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.reachedSavingPrinter("daily");
        LimitUi.reachedSavingPrinter("weekly");
        LimitUi.reachedSavingPrinter("monthly");
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_9,actual.trim());
        os.flush();
    }

    @Test
    public void exceededSavingPrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.exceededSavingPrinter("daily", 45);
        LimitUi.exceededSavingPrinter("weekly",188);
        LimitUi.exceededSavingPrinter("monthly",288);
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_10,actual.trim());
        os.flush();
    }

    @Test
    public void remainingBudgetPrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.remainingBudgetPrinter(25,40,10,"weekly");
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_11,actual.trim());
        os.flush();
    }

    @Test
    public void remainingSavingPrinterTest() throws IOException {
        System.setOut(ps);
        LimitUi.remainingSavingPrinter(100,40,"weekly");
        actual = os.toString();
        assertEquals(LIMIT_UI_EXPECTED_12,actual.trim());
        os.flush();
    }
}
