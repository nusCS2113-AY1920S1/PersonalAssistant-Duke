package owlmoney.model.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.transaction.Deposit;
import owlmoney.model.transaction.Expenditure;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

class SavingTest {
    private static final String NEWLINE = System.lineSeparator();

    @Test
    void addInExpenditure_notAddingToSavingAccount_throwsException() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testExpenditure = new Expenditure("test", 20, new Date("1/1/2019"), "test");
        BankException thrown = assertThrows(BankException.class, () ->
                testSaving.addInExpenditure(testExpenditure, uiTest, "bond"));
        assertEquals("Bonds cannot be added to this account", thrown.toString());
    }

    @Test
    void addInExpenditure_expenditureAmountExceedBankAmount_throwsException() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testExpenditure = new Expenditure("test", 2000, new Date("1/1/2019"), "test");
        BankException thrown = assertThrows(BankException.class, () ->
                testSaving.addInExpenditure(testExpenditure, uiTest, "bank"));
        assertEquals("Bank account cannot have a negative amount", thrown.toString());
    }

    @Test
    void addInExpenditure_succeedAdding_bankAmountDecrease() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testExpenditure = new Expenditure("test", 200, new Date("1/1/2019"), "test");
        try {
            testSaving.addInExpenditure(testExpenditure, uiTest, "bank");
        } catch (BankException errorMessage) {
            System.out.println("Expected no error, but error was thrown");
        }
        assertEquals(1000.00, testSaving.getCurrentAmount());
    }

    @Test
    void deleteExpenditure_succeedDeleting_bankAmountIncrease() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testExpenditure = new Expenditure("test", 200, new Date("1/1/2019"), "test");
        try {
            testSaving.addInExpenditure(testExpenditure, uiTest, "bank");
        } catch (BankException errorMessage) {
            System.out.println("Expected no error, but error was thrown");
        }
        assertEquals(1000.00, testSaving.getCurrentAmount());
        try {
            testSaving.deleteExpenditure(1, uiTest, false);
        } catch (TransactionException | BankException errorMessage) {
            System.out.println("Expected no error, but error was thrown");
        }
        assertEquals(1200.00, testSaving.getCurrentAmount());
    }

    @Test
    void editExpenditureDetails_newAmountExceedBankAmount_throwsException() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testExpenditure = new Expenditure("test", 200, new Date("1/1/2019"), "test");
        try {
            testSaving.addInExpenditure(testExpenditure, uiTest, "bank");
        } catch (BankException errorMessage) {
            System.out.println("Expected no error, but error was thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
            testSaving.editExpenditureDetails(1, "", "4000", "", "", uiTest));
        assertEquals("Bank account cannot have a negative amount", thrown.toString());
    }

    @Test
    void editExpenditureDetails_succeedChangeNewAmount_bankAmountChanged() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testExpenditure = new Expenditure("test", 200, new Date("1/1/2019"), "test");
        try {
            testSaving.addInExpenditure(testExpenditure, uiTest, "bank");
        } catch (BankException errorMessage) {
            System.out.println("Expected no error, but error was thrown");
        }
        assertEquals(1000.00, testSaving.getCurrentAmount());
        try {
            testSaving.editExpenditureDetails(1, "", "1200", "", "", uiTest);
        } catch (TransactionException | BankException errorMessage) {
            System.out.println("Expected no error, but error was thrown");
        }
        assertEquals(0.00, testSaving.getCurrentAmount());
    }

    @Test
    void addDepositTransaction_notBankAccountType_throwsException() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testDeposit = new Deposit("test", 20, new Date("1/1/2019"), "test");
        BankException thrown = assertThrows(BankException.class, () ->
                testSaving.addDepositTransaction(testDeposit, uiTest, "bond"));
        assertEquals("This account does not support investment account deposits", thrown.toString());
    }

    @Test
    void addDepositTransaction_succeedAdding_bankAmountIncrease() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testDeposit = new Deposit("test", 20, new Date("1/1/2019"), "test");
        try {
            testSaving.addDepositTransaction(testDeposit, uiTest, "bank");
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        assertEquals(1220.00, testSaving.getCurrentAmount());
    }

    @Test
    void deleteDepositTransaction_newBankAmountNegative_throwsException() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testDeposit = new Deposit("test", 20, new Date("1/1/2019"), "test");
        Transaction testExpenditure = new Expenditure("test", 1201, new Date("1/1/2019"), "test");
        try {
            testSaving.addDepositTransaction(testDeposit, uiTest, "bank");
            assertEquals(1220.00, testSaving.getCurrentAmount());
            testSaving.addInExpenditure(testExpenditure, uiTest, "bank");
            assertEquals(19.00, testSaving.getCurrentAmount());
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                testSaving.deleteDepositTransaction(1, uiTest, false));
        assertEquals("Bank account cannot have a negative amount", thrown.toString());
    }

    @Test
    void deleteDepositTransaction_succeedDeleting_bankAmountDecrease() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testDeposit = new Deposit("test", 20, new Date("1/1/2019"), "test");
        try {
            testSaving.addDepositTransaction(testDeposit, uiTest, "bank");
            assertEquals(1220.00, testSaving.getCurrentAmount());
            testSaving.deleteDepositTransaction(1, uiTest, false);
        } catch (BankException | TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        assertEquals(1200.00, testSaving.getCurrentAmount());
    }

    @Test
    void editDepositDetails_newBankAmountNegative_throwsException() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testDeposit = new Deposit("test", 20, new Date("1/1/2019"), "test");
        Transaction testExpenditure = new Expenditure("test", 1215, new Date("1/1/2019"), "test");
        try {
            testSaving.addDepositTransaction(testDeposit, uiTest, "bank");
            assertEquals(1220.00, testSaving.getCurrentAmount());
            testSaving.addInExpenditure(testExpenditure, uiTest, "bank");
            assertEquals(5.00, testSaving.getCurrentAmount());
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                testSaving.editDepositDetails(1, "", "10", "", uiTest));
        assertEquals("Bank account cannot have a negative amount", thrown.toString());
    }

    @Test
    void editDepositTransaction_succeedChanging_bankAmountChanged() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("testBank", 1200, 1000);
        Transaction testDeposit = new Deposit("test", 20, new Date("1/1/2019"), "test");
        try {
            testSaving.addDepositTransaction(testDeposit, uiTest, "bank");
            assertEquals(1220.00, testSaving.getCurrentAmount());
            testSaving.editDepositDetails(1, "", "10", "", uiTest);
        } catch (BankException | TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        assertEquals(1210.00, testSaving.getCurrentAmount());
    }

    @Test
    void savingAddRecurringExpenditure_recurringExpenditureAdded_newRecurringExpenditureInList() {
        Ui testUi = new Ui();
        Saving savingTest = new Saving("test", 100, 100);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(new Date("10/28/2019"));
        calendar.add(Calendar.MONTH, -1);
        Expenditure testExpenditure = new Expenditure("testExpenditure", 10, calendar.getTime(), "testExpenditure");
        try {
            savingTest.savingAddRecurringExpenditure(testExpenditure, testUi);

        } catch (TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            savingTest.savingListRecurringExpenditure(testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        String outputMessage = "Transaction No.      Description                                             "
                + "Amount          Next Expense Date    Category             " + NEWLINE + "-----------------"
                + "------------------------------------------------------------------------------------------"
                + "----------------------" + NEWLINE + "1                    testExpenditure                 "
                + "                        [-] $10.00      28 September 2019    testExpenditure      "
                + NEWLINE + "--------------------------------------------------------------------------------"
                + "-------------------------------------------------" + NEWLINE;
        assertEquals(outputMessage, outContent.toString());
    }

    @Test
    void savingDeleteRecurringExpenditure_recurringExpenditureDeleted_noRecurringExpenditureInList() {
        Ui testUi = new Ui();
        Saving savingTest = new Saving("test", 100, 100);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(new Date("10/28/2019"));
        calendar.add(Calendar.MONTH, -1);
        Expenditure testExpenditure = new Expenditure("testExpenditure", 10, calendar.getTime(), "testExpenditure");
        try {
            savingTest.savingAddRecurringExpenditure(testExpenditure, testUi);

        } catch (TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            savingTest.savingListRecurringExpenditure(testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        String outputMessage = "Transaction No.      Description                                             "
                + "Amount          Next Expense Date    Category             " + NEWLINE + "-----------------"
                + "------------------------------------------------------------------------------------------"
                + "----------------------" + NEWLINE + "1                    testExpenditure                 "
                + "                        [-] $10.00      28 September 2019    testExpenditure      "
                + NEWLINE + "--------------------------------------------------------------------------------"
                + "-------------------------------------------------" + NEWLINE;
        assertEquals(outputMessage, outContent.toString());
        try {
            savingTest.savingDeleteRecurringExpenditure(1, testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        TransactionException thrown = assertThrows(TransactionException.class, () ->
                        savingTest.savingListRecurringExpenditure(testUi),
                "Expected deleteRecurringExpenditure to throw, but it didn't");
        assertEquals("There are no recurring expenditures in this account", thrown.toString());
    }

    @Test
    void savingEditRecurringExpenditure_recurringExpenditureEdited_newDetailsShown() {
        Ui testUi = new Ui();
        Saving savingTest = new Saving("test", 100, 100);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(new Date("10/28/2019"));
        calendar.add(Calendar.MONTH, -1);
        Expenditure testExpenditure = new Expenditure("testExpenditure", 10, calendar.getTime(), "testExpenditure");
        try {
            savingTest.savingAddRecurringExpenditure(testExpenditure, testUi);

        } catch (TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        try {
            savingTest.savingListRecurringExpenditure(testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        try {
            savingTest.savingEditRecurringExpenditure(1, "editExpenditure", "25.25", "editExpenditure", testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            savingTest.savingListRecurringExpenditure(testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        String outputMessage = "Transaction No.      Description                                             "
                + "Amount          Next Expense Date    Category             " + NEWLINE + "-----------------"
                + "------------------------------------------------------------------------------------------"
                + "----------------------" + NEWLINE + "1                    editExpenditure                 "
                + "                        [-] $25.25      28 September 2019    editExpenditure      "
                + NEWLINE + "--------------------------------------------------------------------------------"
                + "-------------------------------------------------" + NEWLINE;
        assertEquals(outputMessage, outContent.toString());
    }

    @Test
    void savingUpdateRecurringTransactions_expenditureUpdated_bankAmountChanged() {
        Ui testUi = new Ui();
        Saving savingTest = new Saving("test", 100, 100);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        try {
            savingTest.savingAddRecurringExpenditure(
                    new Expenditure("testExpenditure", 10, calendar.getTime(), "testExpenditure"), testUi);
        } catch (TransactionException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        try {
            savingTest.updateRecurringTransactions(testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        assertEquals(80, savingTest.getCurrentAmount());
    }

    //Tests function for find feature.
    @Test
    void findBondInInvestment_useOnSavingAccount_throwsException() {
        Ui uiTest = new Ui();
        Bank testSaving = new Saving("Test Saving Bank", 1200, 1000);
        BankException thrown = assertThrows(BankException.class, () ->
                        testSaving.findBondInInvestment("Bond Name", uiTest),
                "Expected transferFund to throw, but it didn't");
        assertEquals("This account does not support this feature", thrown.toString());
    }
}
