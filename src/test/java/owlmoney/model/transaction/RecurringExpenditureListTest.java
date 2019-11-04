package owlmoney.model.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

class RecurringExpenditureListTest {
    private static final String NEWLINE = System.lineSeparator();

    @Test
    void addRecurringExpenditure_successfulAddingExpenditure_newExpenditureAdded() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");
        Transaction testExpenditure = new Expenditure("test", 1, newDate, "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        String printedMessage = "Added expenditure with the following details:" + NEWLINE + "Item No.        "
                + "     Description                                             Amount          Next Expense "
                + "Date    Category             " + NEWLINE + "----------------------------------------------"
                + "-----------------------------------------------------------------------------------"
                + NEWLINE + "1                    test                                                    [-]"
                + " $1.00       " + temp.format(newDate) + "      test                 " + NEWLINE + "-------"
                + "------------------------------------------------------------------------------------------"
                + "--------------------------------" + NEWLINE;
        assertEquals(printedMessage, outContent.toString());
    }

    @Test
    void addRecurringExpenditure_listFull_throwsException() {
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");
        for (int i = 0; i < 100; i++) {
            Transaction testExpenditure = new Expenditure("test" + i, 1, newDate, "test");
            try {
                testList.addRecurringExpenditure(testExpenditure, testUi);
            } catch (TransactionException errorMessage) {
                System.out.println("Expected no throw, but error thrown");
            }
        }
        Transaction testExpenditure = new Expenditure("finaltest", 1, newDate, "test");
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                testList.addRecurringExpenditure(testExpenditure, testUi),
                "Expected deleteRecurringExpenditure to throw, but it didn't");
        assertEquals("The list has reach a max size of 100", thrown.getMessage());
    }

    @Test
    void deleteRecurringExpenditure_noExpenditures_throwsException() {
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.deleteRecurringExpenditure(1, testUi),
                "Expected deleteRecurringExpenditure to throw, but it didn't");
        assertEquals("There are no recurring expenditures in this bank account", thrown.getMessage());
    }

    @Test
    void deleteRecurringExpenditure_indexOutOfBounds_throwsException() {
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        Transaction testExpenditure = new Expenditure("test", 1, new Date(), "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.deleteRecurringExpenditure(2, testUi),
                "Expected deleteRecurringExpenditure to throw, but it didn't");
        assertEquals("Index is out of transaction list range", thrown.getMessage());
    }

    @Test
    void deleteRecurringExpenditure_successfulDelete_recurringExpenditureDeleted() {
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");
        Transaction testExpenditure = new Expenditure("test", 1, newDate, "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            testList.deleteRecurringExpenditure(1, testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        String deletedMessage = "Deleted expenditure with the following details:" + NEWLINE + "Item No.      "
                + "       Description                                             Amount          Next "
                + "Expense Date    Category             " + NEWLINE + "--------------------------------------"
                + "------------------------------------------------------------------------------------------"
                + "-" + NEWLINE + "1                    test                                                 "
                + "   [-] $1.00       " + temp.format(newDate) + "      test                 " + NEWLINE
                + "------------------------------------------------------------------------------------------"
                + "---------------------------------------" + NEWLINE;
        assertEquals(deletedMessage, outContent.toString());
        assertEquals(0, testList.getListSize());
    }

    @Test
    void listRecurringExpenditure_noExpenditure_throwsException() {
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.listRecurringExpenditure(testUi),
                "Expected deleteRecurringExpenditure to throw, but it didn't");
        assertEquals("There are no recurring expenditures in this account", thrown.toString());
    }

    @Test
    void listRecurringExpenditure_oneExpenditure_listedExpenditure() {
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        Date newDate = new Date("10/26/2019");
        Transaction testExpenditure = new Expenditure("test", 1, newDate, "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            testList.listRecurringExpenditure(testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");

        String outputMessage = "Transaction No.      Description                                             "
                + "Amount          Next Expense Date    Category             " + NEWLINE + "-----------------"
                + "------------------------------------------------------------------------------------------"
                + "----------------------" + NEWLINE + "1                    test                            "
                + "                        [-] $1.00       " + temp.format(newDate) + "      test            "
                + "     " + NEWLINE + "----------------------------------------------------------------------"
                + "-----------------------------------------------------------" + NEWLINE;
        assertEquals(outputMessage, outContent.toString());
    }

    @Test
    void editRecurringExpenditure_noExpenditures_throwsException() {
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.editRecurringExpenditure(
                                1, "test2", "400", "test2", testUi),
                "Expected editRecurringExpenditure to throw, but it didn't");
        assertEquals("There are no recurring expenditures in this account", thrown.getMessage());
    }

    @Test
    void editRecurringExpenditure_indexOutOfBounds_throwsException() {
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        Transaction testExpenditure = new Expenditure("test", 1, new Date(), "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        testList.editRecurringExpenditure(
                                6, "test2", "400", "test2", testUi),
                "Expected deleteRecurringExpenditure to throw, but it didn't");
        assertEquals("Index is out of transaction list range", thrown.getMessage());
    }

    @Test
    void editRecurringExpenditure_successfulEdit_changedExpenditureDetails() {
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        Transaction testExpenditure = new Expenditure("test", 1, new Date(), "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        try {
            testList.editRecurringExpenditure(1, "edit", "400", "edit", testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        Transaction editedTransaction = testList.getRecurringExpenditure(0);
        assertEquals(400.00, editedTransaction.getAmount());
        assertEquals("edit", editedTransaction.getCategory());
        assertEquals("edit", editedTransaction.getDescription());
    }

    @Test
    void getListSize_oneExpenditure_returnsOne() {
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        assertEquals(0, testList.getListSize());
        Transaction testExpenditure = new Expenditure("test", 1, new Date(), "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, testList.getListSize());
    }

    @Test
    void get_oneExpenditure_returnsSpecifiedExpenditure() {
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Ui testUi = new Ui();
        Transaction testExpenditure = new Expenditure("test", 1, new Date(), "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(testExpenditure, testList.get(0));
    }

    //Tests function for find feature.
    @Test
    void findMatchingRecurringExpenditure_matchingCategory_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Transaction testExpenditure = new Expenditure("Test 1", 1, new Date(), "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, uiTest);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        outContent.reset();
        testList.findMatchingRecurringExpenditure("", "s", uiTest);

        String expectedOutput = "Find by: category" + NEWLINE
                + "Transaction No.      Description                                             Amount"
                + "          Next Expense Date    Category             " + NEWLINE
                + "--------------------------------------------------------------------------------------"
                + "-------------------------------------------" + NEWLINE
                + "1                    Test 1                                                  "
                + "[-] $1.00       04 November 2019     test                 " + NEWLINE
                + "-------------------------------------------------------------------------------------"
                + "--------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput, outContent.toString());
    }

    //Tests function for find feature.
    @Test
    void findMatchingRecurringExpenditure_missMatchCategory_printErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Transaction testExpenditure = new Expenditure("Test 1", 1, new Date(), "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, uiTest);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        outContent.reset();
        testList.findMatchingRecurringExpenditure("", "Not exist", uiTest);

        String expectedOutput = "No matches for the category keyword: Not exist" + NEWLINE;
        assertEquals(expectedOutput, outContent.toString());
    }


    //Tests function for find feature.
    @Test
    void findMatchingRecurringExpenditure_matchDescription_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Transaction testExpenditure = new Expenditure("Test 1", 1, new Date(), "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, uiTest);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        outContent.reset();
        testList.findMatchingRecurringExpenditure("1", "", uiTest);

        String expectedOutput = "Find by: description" + NEWLINE
                + "Transaction No.      Description                                             Amount"
                + "          Next Expense Date    Category             " + NEWLINE
                + "--------------------------------------------------------------------------------------"
                + "-------------------------------------------" + NEWLINE
                + "1                    Test 1                                                  "
                + "[-] $1.00       04 November 2019     test                 " + NEWLINE
                + "-------------------------------------------------------------------------------------"
                + "--------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput, outContent.toString());
    }

    //Tests function for find feature.
    @Test
    void findMatchingRecurringExpenditure_missMatchDescription_printErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Transaction testExpenditure = new Expenditure("Test 1", 1, new Date(), "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, uiTest);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        outContent.reset();
        testList.findMatchingRecurringExpenditure("Not exist", "", uiTest);

        String expectedOutput = "No matches for the description keyword: Not exist" + NEWLINE;
        assertEquals(expectedOutput, outContent.toString());
    }

    //Tests function for find feature.
    @Test
    void findMatchingRecurringExpenditure_emptyRecurringList_printErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        RecurringExpenditureList testList = new RecurringExpenditureList();

        testList.findMatchingRecurringExpenditure("Not exist", "", uiTest);

        String expectedOutput = "Recurring expenditure is empty." + NEWLINE;
        assertEquals(expectedOutput, outContent.toString());
    }

    //Tests function for find feature.
    @Test
    void findMatchingRecurringExpenditure_allParameterMatch_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Transaction testExpenditure = new Expenditure("Test 1", 1, new Date(), "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, uiTest);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        outContent.reset();
        testList.findMatchingRecurringExpenditure("1", "s", uiTest);

        String expectedOutput = "Find by: description" + NEWLINE
                + "Transaction No.      Description                                             Amount"
                + "          Next Expense Date    Category             " + NEWLINE
                + "--------------------------------------------------------------------------------------"
                + "-------------------------------------------" + NEWLINE
                + "1                    Test 1                                                  "
                + "[-] $1.00       04 November 2019     test                 " + NEWLINE
                + "-------------------------------------------------------------------------------------"
                + "--------------------------------------------" + NEWLINE
                + "Find by: category" + NEWLINE
                + "Transaction No.      Description                                             Amount"
                + "          Next Expense Date    Category             " + NEWLINE
                + "--------------------------------------------------------------------------------------"
                + "-------------------------------------------" + NEWLINE
                + "1                    Test 1                                                  "
                + "[-] $1.00       04 November 2019     test                 " + NEWLINE
                + "-------------------------------------------------------------------------------------"
                + "--------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput, outContent.toString());
    }

    //Tests function for find feature.
    @Test
    void findMatchingRecurringExpenditure_allParameterMissMatch_printErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        RecurringExpenditureList testList = new RecurringExpenditureList();
        Transaction testExpenditure = new Expenditure("Test 1", 1, new Date(), "test");
        try {
            testList.addRecurringExpenditure(testExpenditure, uiTest);
        } catch (TransactionException errorMessage) {
            System.out.println("Expected no throw, but error thrown");
        }
        outContent.reset();
        testList.findMatchingRecurringExpenditure("Not exist", "Not exist", uiTest);

        String expectedOutput = "No matches for the description keyword: Not exist" + NEWLINE
                + "No matches for the category keyword: Not exist" + NEWLINE;
        assertEquals(expectedOutput, outContent.toString());
    }
}
