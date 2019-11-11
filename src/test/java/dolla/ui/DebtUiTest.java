package dolla.ui;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

//@@author yetong1895
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebtUiTest implements UiTestExpectedOutput {
    private String actual;
    private OutputStream os = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(os);

    private ArrayList<String> createNameList() {
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("tata");
        nameList.add("yuyu");
        return nameList;
    }

    @Test
    public void printInvalidDebtFormatErrorTest() throws IOException {
        System.setOut(ps);
        DebtUi.printInvalidDebtFormatError();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_1,actual.trim());
        os.flush();
    }

    @Test
    public void printAverageAmountTest() throws IOException {
        ArrayList<String> nameList = createNameList();
        System.setOut(ps);
        DebtUi.printAverageAmount(2,10, nameList);
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_2, actual.trim());
        os.flush();
    }

    @Test
    public void printInvalidBillFormatErrorTest() throws IOException {
        System.setOut(ps);
        DebtUi.printInvalidBillFormatError();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_3, actual.trim());
        os.flush();
    }

    @Test
    public void printRemoveNameMessageTest() throws IOException {
        System.setOut(ps);
        ArrayList<String> nameList = createNameList();
        DebtUi.printRemoveNameMessage("yaya",nameList);
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_4, actual.trim());
        os.flush();
    }

    @Test
    public void printInvalidPaidFormatErrorTest() throws IOException {
        System.setOut(ps);
        DebtUi.printInvalidPaidFormatError();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_5, actual.trim());
        os.flush();
    }

    @Test
    public void printInvalidBillNumberErrorTest() throws IOException {
        System.setOut(ps);
        DebtUi.printInvalidBillNumberError();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_6, actual.trim());
        os.flush();
    }

    @Test
    public void printFinishMessageTest() throws IOException {
        System.setOut(ps);
        DebtUi.printFinishMessage();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_7, actual.trim());
        os.flush();
    }

    @Test
    public void printEmptyBillMessageTest() throws IOException {
        System.setOut(ps);
        DebtUi.printEmptyBillMessage();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_8, actual.trim());
        os.flush();
    }

    @Test
    public void printWrongPeopleNumberMessageTest() throws IOException {
        System.setOut(ps);
        DebtUi.printWrongPeopleNumberMessage(5);
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_9, actual.trim());
        os.flush();
    }

    @Test
    public void printInvalidNameMessageTest() throws IOException {
        System.setOut(ps);
        DebtUi.printInvalidNameMessage();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_10, actual.trim());
        os.flush();
    }

    @Test
    public void printNameNotFoundTest() throws IOException {
        System.setOut(ps);
        DebtUi.printNameNotFound();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_11, actual.trim());
        os.flush();
    }

    @Test
    public void printBillFeatureTest() throws IOException {
        System.setOut(ps);
        DebtUi.printBillFeature();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_12, actual.trim());
        os.flush();
    }

    @Test
    public void printRemoveBillMessageTest() throws IOException {
        System.setOut(ps);
        DebtUi.printRemoveBillMessage();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_13, actual.trim());
        os.flush();
    }

    @Test
    public void printRemoveBillFormatErrorTest() throws IOException {
        System.setOut(ps);
        DebtUi.printRemoveBillFormatError();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_14, actual.trim());
        os.flush();
    }

    @Test
    public void printInvalidNameNumberErrorTest() throws IOException {
        System.setOut(ps);
        DebtUi.printInvalidNameNumberError(3);
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_15, actual.trim());
        os.flush();
    }

    @Test
    public void printInvalidDebtTypeTest() throws IOException {
        System.setOut(ps);
        DebtUi.printInvalidDebtType();
        actual = os.toString();
        assertEquals(DEBT_UI_EXPECTED_16, actual.trim());
        os.flush();
    }


}
