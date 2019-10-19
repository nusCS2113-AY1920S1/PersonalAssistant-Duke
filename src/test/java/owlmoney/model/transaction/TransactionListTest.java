package owlmoney.model.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

class TransactionListTest {
    @Test
    void checkListExpenditure_noTransactions_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                transListTest.listExpenditure(uiTest, 30),
                "Expected listExpenditure to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void checkListExpenditure_noExpenditure_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addDepositToList(new Deposit(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");

        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.listExpenditure(uiTest, 30),
                "Expected listExpenditure to throw, but it didn't");
        assertEquals("No expenditures found", thrown.getMessage());
    }

    @Test
    void checkListExpenditure_expenditureExists_printExpenditureDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addExpenditureToList(new Expenditure(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        try {
            transListTest.listExpenditure(uiTest, 30);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals("Expenditure exists", outContent.toString());
    }

    @Test
    void checkListExpenditureDisplayNum_multipleExpenditureExists_printOneExpenditureDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addExpenditureToList(new Expenditure(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        transListTest.addExpenditureToList(new Expenditure(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        try {
            transListTest.listExpenditure(uiTest, 1);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals("Expenditure exists", outContent.toString());
    }

    @Test
    void checkListDeposit_noTransactions_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.listDeposit(uiTest, 30),
                "Expected listDeposit to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void checkListDeposit_noDeposit_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addExpenditureToList(new Expenditure(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");

        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.listDeposit(uiTest, 30),
                "Expected listDeposit to throw, but it didn't");
        assertEquals("No deposits found", thrown.getMessage());
    }

    @Test
    void checkListDeposit_depositExists_printDepositDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addDepositToList(new Deposit(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        try {
            transListTest.listDeposit(uiTest, 30);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals("Deposit exists", outContent.toString());
    }

    @Test
    void checkListDepositDisplayNum_multipleDepositExists_printOneDepositDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addDepositToList(new Deposit(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        transListTest.addDepositToList(new Deposit(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        try {
            transListTest.listDeposit(uiTest, 1);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals("Deposit exists", outContent.toString());
    }

    @Test
    void checkDeleteExpenditure_noTransactions_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.deleteExpenditureFromList(1, uiTest),
                "Expected deleteExpenditureFromList to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void checkDeleteExpenditure_notExpenditure_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addDepositToList(new Deposit(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.deleteExpenditureFromList(1, uiTest),
                "Expected listExpenditure to throw, but it didn't");
        assertEquals("The transaction is a deposit", thrown.getMessage());
    }

    @Test
    void checkDeleteExpenditure_indexOutOfRange_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addExpenditureToList(new Expenditure(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.deleteExpenditureFromList(3, uiTest),
                "Expected listExpenditure to throw, but it didn't");
        assertEquals("Index is out of transaction list range", thrown.getMessage());
    }

    @Test
    void checkDeleteExpenditure_isExpenditure_deleteExpenditure() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        transListTest.addExpenditureToList(new Expenditure(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        try {
            transListTest.deleteExpenditureFromList(1, uiTest);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals("Expenditure removed", outContent.toString());
    }

    @Test
    void checkEditEx_changedAmount_amountChanged() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        transListTest.addExpenditureToList(new Expenditure(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        try {
            transListTest.editEx(1, "", "5", "", "", uiTest);
            assertEquals("New amount: 5.0", outContent.toString());
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
    }

    @Test
    void checkEditDep_changedDescription_descriptionChanged() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        transListTest.addDepositToList(new Deposit(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        try {
            transListTest.editDep(1, "gg", "", "", uiTest);
            assertEquals("New description: gg", outContent.toString());
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
    }

    @Test
    void checkGetExpenditureAmount_noTransactions_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.getExpenditureAmount(1),
                "Expected listExpenditure to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void checkGetExpenditureAmount_indexOutOfRange_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addExpenditureToList(new Expenditure(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.getExpenditureAmount(4),
                "Expected listExpenditure to throw, but it didn't");
        assertEquals("Index is out of transaction list range", thrown.getMessage());
    }

    @Test
    void checkGetExpenditureAmount_depositTransaction_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addDepositToList(new Deposit(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.getExpenditureAmount(1),
                "Expected listExpenditure to throw, but it didn't");
        assertEquals("The transaction is a deposit", thrown.getMessage());
    }

    @Test
    void checkGetExpenditureAmount_expenditureTransaction_returnExpenditureAmount() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addExpenditureToList(new Expenditure(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        try {
            assertEquals(1.0, transListTest.getExpenditureAmount(1));
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
    }

    @Test
    void checkGetDepositAmount_noTransactions_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.getDepositValue(1),
                "Expected listExpenditure to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void checkGetDepositAmount_expenditureTransaction_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addExpenditureToList(new Expenditure(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.getDepositValue(1),
                "Expected listExpenditure to throw, but it didn't");
        assertEquals("The transaction is not a deposit", thrown.getMessage());
    }

    @Test
    void checkGetDepositAmount_indexOutOfRange_throwsException() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addDepositToList(new Deposit(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        transListTest.getDepositValue(4),
                "Expected listExpenditure to throw, but it didn't");
        assertEquals("Index is out of transaction list range", thrown.getMessage());
    }

    @Test
    void checkGetDepositAmount_depositTransaction_returnDepositAmount() {
        TransactionList transListTest = new TransactionListStub();
        Ui uiTest = new Ui();
        transListTest.addDepositToList(new Deposit(
                "test", 1, new Date("1/1/2019"),
                "test"), uiTest, "bank");
        try {
            assertEquals(1.0, transListTest.getDepositValue(1));
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
    }
}
