package owlmoney.model.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

class TransactionListTest {
    private static final String NEWLINE = System.lineSeparator();

    @Test
    void addExpenditureToList_successfulAdd_newExpenditureAdded() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        String printedMessage = "Added expenditure with the following details:" + NEWLINE +
                "Item No.             Description                                             Amount          Date   " +
                "              Category             " + NEWLINE +
                "-------------------------------------------------------------------------------------------------" +
                "--------------------------------" + NEWLINE + "1                    test                         " +
                "                           [-] $1.00       " + temp.format(newDate) + "      test                 " +
                NEWLINE + "-----------------------------------------------------------------------------------------" +
                "----------------------------------------" + NEWLINE;
        Transaction testExpenditure = new Expenditure("test", 1, newDate, "test");
        testList.addExpenditureToList(testExpenditure, testUi, "bank");
        assertEquals(printedMessage, outContent.toString());
    }

    @Test
    void addExpenditureToList_addedOverLimit_deleteEarliestTransaction() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        for (int i = 0; i < 2001; i++) {
            Transaction testExpenditure = new Expenditure("test", i, new Date(), "test");
            testList.addExpenditureToList(testExpenditure, testUi, "bank");
        }
        double amount = -1;
        try {
            amount = testList.getExpenditureAmount(1);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, amount);
    }

    @Test
    void addDepositToList_successfulAdd_newExpenditureAdded() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        String printedMessage = "Added deposit with the following details:" + NEWLINE +
                "Item No.             Description                                             Amount          Date   " +
                "              Category             " + NEWLINE +
                "-------------------------------------------------------------------------------------------------" +
                "--------------------------------" + NEWLINE + "1                    test                         " +
                "                           [+] $1.00       " + temp.format(newDate) + "      test                 " +
                NEWLINE + "-----------------------------------------------------------------------------------------" +
                "----------------------------------------" + NEWLINE;
        Transaction testDeposit = new Deposit("test", 1, newDate, "test");
        testList.addDepositToList(testDeposit, testUi, "bank");
        assertEquals(printedMessage, outContent.toString());
    }

    @Test
    void addDepositToList_addedOverLimit_deleteEarliestTransaction() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        for (int i = 0; i < 2001; i++) {
            Deposit testDeposit = new Deposit("test", i, new Date(), "test");
            testList.addDepositToList(testDeposit, testUi, "bank");
        }
        double amount = -1;
        try {
            amount = testList.getDepositValue(1);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, amount);
    }

    @Test
    void deleteExpenditure_noExpenditures_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.deleteExpenditureFromList(1, testUi),
                "Expected deleteExpenditureFromList to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void deleteExpenditure_indexOutOfBounds_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Transaction testExpenditure = new Expenditure("test", 1, new Date(), "test");
        testList.addExpenditureToList(testExpenditure, testUi, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.deleteExpenditureFromList(2, testUi),
                "Expected deleteExpenditureFromList to throw, but it didn't");
        assertEquals("Index is out of transaction list range", thrown.getMessage());
    }

    @Test
    void deleteExpenditure_depositTransaction_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Transaction testExpenditure = new Deposit("test", 1, new Date(), "test");
        testList.addDepositToList(testExpenditure, testUi, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.deleteExpenditureFromList(1, testUi),
                "Expected deleteExpenditureFromList to throw, but it didn't");
        assertEquals("The transaction is a deposit", thrown.getMessage());
    }

    @Test
    void deleteExpenditure_successfulDelete_expenditureDeleted() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");;
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        Transaction testExpenditure = new Expenditure("test", 1, newDate, "test");
        String deletedMessage = "Details of deleted Expenditure:" + NEWLINE +
                "Item No.             Description                                             Amount          Date   " +
                "              Category             " + NEWLINE +
                "----------------------------------------------------------------------------------------------------" +
                "-----------------------------" + NEWLINE + "1                    test                             " +
                "                       [-] $1.00       " + temp.format(newDate) + "      test                 " +
                NEWLINE + "-----------------------------------------------------------------------------------------" +
                "----------------------------------------" + NEWLINE;
        testList.addExpenditureToList(testExpenditure, testUi, "bank");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            testList.deleteExpenditureFromList(1, testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(deletedMessage, outContent.toString());
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.deleteExpenditureFromList(1, testUi),
                "Expected deleteExpenditureFromList to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void getDepositValue_noDeposits_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.getDepositValue(1),
                "Expected deleteDepositFromList to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void getDepositValue_indexOutOfBounds_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Transaction testDeposit = new Deposit("test", 1, new Date(), "test");
        testList.addDepositToList(testDeposit, testUi, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.getDepositValue(7),
                "Expected deleteDepositFromList to throw, but it didn't");
        assertEquals("Index is out of transaction list range", thrown.getMessage());
    }

    @Test
    void getDepositValue_expenditureTransaction_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Transaction testExpenditure = new Expenditure("test", 1, new Date(), "test");
        testList.addDepositToList(testExpenditure, testUi, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.getDepositValue(1),
                "Expected deleteDepositFromList to throw, but it didn't");
        assertEquals("The transaction is not a deposit", thrown.getMessage());
    }

    @Test
    void getDepositValue_successfulGetDeposit_returnDepositValur() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Transaction testDeposit = new Deposit("test", 1, new Date(), "test");
        testList.addDepositToList(testDeposit, testUi, "bank");
        double value = -1;
        try {
            value = testList.getDepositValue(1);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, value);
    }

    @Test
    void getExpenditureAmount_noDeposits_throwsException() {
        TransactionList testList = new TransactionList();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.getExpenditureAmount(1),
                "Expected deleteDepositFromList to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void getExpenditureAmount_indexOutOfBounds_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Transaction testExpenditure = new Expenditure("test", 1, new Date(), "test");
        testList.addDepositToList(testExpenditure, testUi, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.getExpenditureAmount(7),
                "Expected deleteDepositFromList to throw, but it didn't");
        assertEquals("Index is out of transaction list range", thrown.getMessage());
    }

    @Test
    void getExpenditureAmount_expenditureTransaction_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Transaction testDeposit = new Deposit("test", 1, new Date(), "test");
        testList.addDepositToList(testDeposit, testUi, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.getExpenditureAmount(1),
                "Expected deleteDepositFromList to throw, but it didn't");
        assertEquals("The transaction is a deposit", thrown.getMessage());
    }

    @Test
    void getExpenditureAmount_successfulGetDeposit_returnExpenditureAmount() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Transaction testExpenditure = new Expenditure("test", 1, new Date(), "test");
        testList.addDepositToList(testExpenditure, testUi, "bank");
        double value = -1;
        try {
            value = testList.getExpenditureAmount(1);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, value);
    }

    @Test
    void deleteDeposit_successfulDelete_expenditureDeleted() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");;
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        Transaction testDeposit = new Deposit("test", 1, newDate, "test");
        String deletedMessage = "Details of deleted deposit:" + NEWLINE +
                "Item No.             Description                                             Amount          Date   " +
                "              Category             " + NEWLINE +
                "----------------------------------------------------------------------------------------------------" +
                "-----------------------------" + NEWLINE + "1                    test                             " +
                "                       [+] $1.00       " + temp.format(newDate) + "      test                 " +
                NEWLINE + "-----------------------------------------------------------------------------------------" +
                "----------------------------------------" + NEWLINE;
        testList.addDepositToList(testDeposit, testUi, "bank");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        testList.deleteDepositFromList(1, testUi);
        assertEquals(deletedMessage, outContent.toString());
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.getDepositValue(1),
                "Expected deleteDeposit to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void listDeposit_noTransactions_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.listDeposit(testUi, 10),
                "Expected deleteDepositFromList to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void listDeposit_noDeposits_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Transaction testExpenditure = new Expenditure("test", 1, new Date(), "test");
        testList.addExpenditureToList(testExpenditure, testUi, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.listDeposit(testUi, 10),
                "Expected deleteDepositFromList to throw, but it didn't");
        assertEquals("No deposits found", thrown.getMessage());
    }

    @Test
    void listDeposit_twoDepositsListOne_listOneDeposit() {
        TransactionList testList = new TransactionList();
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");
        Transaction testDeposit = new Deposit("test", 1, newDate, "test");
        testList.addDepositToList(testDeposit, testUi, "bank");
        Transaction testDeposit2 = new Deposit("test", 2, newDate, "test");
        testList.addDepositToList(testDeposit2, testUi, "bank");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String outputMessage = "Transaction No.      Description                                             Amount  " +
                "        Date                 Category             " + NEWLINE + "----------------------------------" +
                "-----------------------------------------------------------------------------------------------" +
                NEWLINE + "2                    test                                                    [+] $2.00    " +
                "   " + temp.format(newDate) +"      test                 " + NEWLINE + "---------------------------" +
                "---------------------------------------------------------------------------------------------------" +
                "---" + NEWLINE;
        try {
            testList.listDeposit(testUi, 1);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(outputMessage, outContent.toString());
    }

    @Test
    void listExpenditure_noTransactions_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.listExpenditure(testUi, 10),
                "Expected deleteDepositFromList to throw, but it didn't");
        assertEquals("There are no transactions in this bank account", thrown.getMessage());
    }

    @Test
    void listExpenditure_noExpenditures_throwsException() {
        TransactionList testList = new TransactionList();
        Ui testUi = new Ui();
        Transaction testDeposit = new Deposit("test", 1, new Date(), "test");
        testList.addDepositToList(testDeposit, testUi, "bank");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.listExpenditure(testUi, 1),
                "Expected deleteDepositFromList to throw, but it didn't");
        assertEquals("No expenditures found", thrown.getMessage());
    }

    @Test
    void listExpenditure_twoExpendituresListOne_listOneExpenditure() {
        TransactionList testList = new TransactionList();
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");
        Transaction testExpenditure = new Expenditure("test", 1, newDate, "test");
        testList.addExpenditureToList(testExpenditure, testUi, "bank");
        Transaction testExpenditure2 = new Expenditure("test", 2, newDate, "test");
        testList.addExpenditureToList(testExpenditure2, testUi, "bank");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String outputMessage = "Transaction No.      Description                                             Amount  " +
                "        Date                 Category             " + NEWLINE + "----------------------------------" +
                "-----------------------------------------------------------------------------------------------" +
                NEWLINE + "2                    test                                                    [-] $2.00    " +
                "   " + temp.format(newDate) +"      test                 " + NEWLINE + "----------------------------" +
                "----------------------------------------------" +
                "-------------------------------------------------------" + NEWLINE;
        try {
            testList.listExpenditure(testUi, 1);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(outputMessage, outContent.toString());
    }

    @Test
    void editExpenditure_successfulEdit_expenditureDetailsUpdated() {
        TransactionList testList = new TransactionList();
        DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat temp2 = new SimpleDateFormat("dd MMMM yyyy");
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");
        Date editedDate = new Date("10/25/2019");
        Transaction testExpenditure = new Expenditure("test", 1, newDate, "test");
        testList.addExpenditureToList(testExpenditure, testUi, "bank");
        try {
            testList.editExpenditure(
                    1, "edit", "5.35", temp.format(editedDate), "edit", testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String outputMessage = "Transaction No.      Description                                             Amount  " +
                "        Date                 Category             " + NEWLINE + "----------------------------------" +
                "-----------------------------------------------------------------------------------------------" +
                NEWLINE + "1                    edit                                                    [-] $5.35    " +
                "   " + temp2.format(editedDate) +"      edit                 " + NEWLINE + "------------------------" +
                "---------------------------------------------------------------------------------------------------" +
                "------" + NEWLINE;
        try {
            testList.listExpenditure(testUi, 1);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(outputMessage, outContent.toString());
    }

    @Test
    void editDeposit_successfulEdit_depositDetailsUpdated() {
        TransactionList testList = new TransactionList();
        DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat temp2 = new SimpleDateFormat("dd MMMM yyyy");
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");
        Date editedDate = new Date("10/25/2019");
        Transaction testDeposit = new Deposit("test", 1, newDate, "test");
        testList.addDepositToList(testDeposit, testUi, "bank");
        try {
            testList.editDeposit(
                    1, "edit", "5.35", temp.format(editedDate), testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String outputMessage = "Transaction No.      Description                                             Amount  " +
                "        Date                 Category             " + NEWLINE + "----------------------------------" +
                "-----------------------------------------------------------------------------------------------" +
                NEWLINE + "1                    edit                                                    [+] $5.35    " +
                "   " + temp2.format(editedDate) +"      test                 " + NEWLINE + "------------------------" +
                "---------------------------------------------------------------------------------------------------" +
                "------" + NEWLINE;
        try {
            testList.listDeposit(testUi, 1);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(outputMessage, outContent.toString());
    }
}
