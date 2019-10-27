package owlmoney.model.find;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import owlmoney.model.bank.Bank;
import owlmoney.model.bank.BankList;
import owlmoney.model.bank.Investment;
import owlmoney.model.bank.Saving;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.transaction.Deposit;
import owlmoney.model.transaction.Expenditure;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.storage.Storage;
import owlmoney.ui.Ui;

class FindTest {
    private static final String NEWLINE = System.lineSeparator();
    private static final DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
    private static final String FILE_PATH = "data/";
    private static final Storage storage = new Storage(FILE_PATH);

    @Test
    void findBankAccount_AccountNameMatch_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList(storage);
        Ui uiTest = new Ui();
        Bank newSavingAccountOne = new Saving("Test Saving Account 1", 1000, 2000);
        Bank newSavingAccountTwo = new Saving("Test Saving Account 2", 1000, 2000);

        try {
            bankList.bankListAddBank(newSavingAccountOne, uiTest);
            bankList.bankListAddBank(newSavingAccountTwo, uiTest);
            assertEquals(2, bankList.getBankListSize());
            outContent.reset();
            bankList.findBankAccount("Test", "saving", uiTest);
            String expectedOutput = "Item No.             Account Name                        "
                    + "Account Type    Current Amount  Income          " + NEWLINE
                    + "--------------------------------------------------------------------------------"
                    + "-------------------------------------------------" + NEWLINE
                    + "1                    Test Saving Account 1               saving          "
                    + "$1000.00        $2000.00        " + NEWLINE
                    + "2                    Test Saving Account 2               saving          $1000.00"
                    + "        $2000.00        " + NEWLINE
                    + "------------------------------------------------------------------------"
                    + "---------------------------------------------------------" + NEWLINE;
            assertEquals(expectedOutput, outContent.toString());


        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }
    }

    @Test
    void findBankAccount_savingsAccountNameDoNotMatch_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList(storage);
        Ui uiTest = new Ui();
        Bank newSavingAccountOne = new Saving("Test Saving Account 1", 1000, 2000);
        Bank newSavingAccountTwo = new Saving("Test Saving Account 2", 1000, 2000);

        try {
            bankList.bankListAddBank(newSavingAccountOne, uiTest);
            bankList.bankListAddBank(newSavingAccountTwo, uiTest);
            assertEquals(2, bankList.getBankListSize());
            outContent.reset();
        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                bankList.findBankAccount("testing", "saving", uiTest),
                "Expected findBankAccount to throw, but it didn't");
        assertEquals("Savings account with the following keyword could not be found: testing",
                thrown.getMessage());
        outContent.reset();
    }

    @Test
    void findBankAccount_investmentAccountNameDoNotMatch_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList(storage);
        Ui uiTest = new Ui();
        Bank newInvestmentAccountOne = new Investment("Test Investment Account 1", 1000);
        Bank newInvestmentAccountTwo = new Investment("Test Investment Account 2", 2000);

        try {
            bankList.bankListAddBank(newInvestmentAccountOne, uiTest);
            bankList.bankListAddBank(newInvestmentAccountTwo, uiTest);
            assertEquals(2, bankList.getBankListSize());
            outContent.reset();
        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                        bankList.findBankAccount("testing", "investment", uiTest),
                "Expected findBankAccount to throw, but it didn't");
        assertEquals("Investment account with the following keyword could not be found: testing",
                thrown.getMessage());
        outContent.reset();
    }

    @Test
    void findMatchingTransaction_MatchingDateRange_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        TransactionList transactionListTemp = new TransactionList();
        try {
            Transaction expenditureTestOne = new Expenditure("Chicken Rice", 15,
                    (temp.parse("10/6/2019")), "Food");
            Transaction expenditureTestTwo = new Expenditure("Bubble Tea", 10,
                    (temp.parse("10/7/2019")), "Food");
            Transaction depositTest = new Deposit("Fund Received", 100,
                    (temp.parse("11/9/2019")), "Deposit");

            transactionListTemp.addExpenditureToList(expenditureTestOne, uiTest, "saving");
            transactionListTemp.addExpenditureToList(expenditureTestTwo, uiTest, "saving");
            transactionListTemp.addExpenditureToList(depositTest, uiTest, "saving");
        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        try {
            outContent.reset();
            transactionListTemp.findMatchingTransaction("10/7/2019",
                    "19/9/2019", "", "", uiTest);
           String expectedOutput = "Find by: date range" + NEWLINE
                   + "Transaction No.      Description                                             "
                   + "Amount          Date                 Category             " + NEWLINE
                   +"-----------------------------------------------------------------------------"
                   + "----------------------------------------------------" + NEWLINE
                   + "2                    Bubble Tea                                              "
                   + "[-] $10.00      10 July 2019         Food                 " + NEWLINE
                   + "3                    Fund Received                                           "
                   + "[+] $100.00     11 September 2019    Deposit              " + NEWLINE
                   + "----------------------------------------------------------------------------"
                   + "-----------------------------------------------------" + NEWLINE;
            assertEquals(expectedOutput,outContent.toString());
            outContent.reset();
        } catch (TransactionException error) {
            System.out.println("Expected no throw, but error thrown");
        }

    }

    @Test
    void findMatchingTransaction_DateRangeMissMatch_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        TransactionList transactionListTemp = new TransactionList();
        try {
            Transaction expenditureTestOne = new Expenditure("Chicken Rice", 15,
                    (temp.parse("10/6/2019")), "Food");
            Transaction expenditureTestTwo = new Expenditure("Bubble Tea", 10,
                    (temp.parse("10/7/2019")), "Food");
            Transaction depositTest = new Deposit("Fund Received", 100,
                    (temp.parse("11/9/2019")), "Deposit");

            transactionListTemp.addExpenditureToList(expenditureTestOne, uiTest, "saving");
            transactionListTemp.addExpenditureToList(expenditureTestTwo, uiTest, "saving");
            transactionListTemp.addExpenditureToList(depositTest, uiTest, "saving");
        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        try {
            outContent.reset();
            transactionListTemp.findMatchingTransaction("1/2/2019",
                    "3/4/2019", "", "", uiTest);
            String expectedOutput = "No matches for the date range specified: 1/2/2019 to 3/4/2019" + NEWLINE;
            assertEquals(expectedOutput,outContent.toString());
            outContent.reset();
        } catch (TransactionException error) {
            System.out.println("Expected no throw, but error thrown");
        }

    }

    @Test
    void findMatchingTransaction_MatchingCategory_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        TransactionList transactionListTemp = new TransactionList();
        try {
            Transaction expenditureTestOne = new Expenditure("Chicken Rice", 15,
                    (temp.parse("10/6/2019")), "Food");
            Transaction expenditureTestTwo = new Expenditure("Bubble Tea", 10,
                    (temp.parse("10/7/2019")), "Food");
            Transaction depositTest = new Deposit("Fund Received", 100,
                    (temp.parse("11/9/2019")), "Deposit");

            transactionListTemp.addExpenditureToList(expenditureTestOne, uiTest, "saving");
            transactionListTemp.addExpenditureToList(expenditureTestTwo, uiTest, "saving");
            transactionListTemp.addExpenditureToList(depositTest, uiTest, "saving");

        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        try {
            outContent.reset();
            transactionListTemp.findMatchingTransaction("",
                    "", "", "deposit", uiTest);
            String expectedOutput = "Find by: category" + NEWLINE
                    + "Transaction No.      Description                                             "
                    + "Amount          Date                 Category             " + NEWLINE
                    + "-------------------------------------------------------------------------------"
                    + "--------------------------------------------------" + NEWLINE
                    + "3                    Fund Received                                           "
                    + "[+] $100.00     11 September 2019    Deposit              " + NEWLINE
                    + "--------------------------------------------------------------------------"
                    + "-------------------------------------------------------" + NEWLINE;
            assertEquals(expectedOutput,outContent.toString());
            outContent.reset();
        } catch (TransactionException error) {
            System.out.println("Expected no throw, but error thrown");
        }

    }

    @Test
    void findMatchingTransaction_CategoryMissMatch_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        TransactionList transactionListTemp = new TransactionList();
        try {
            Transaction expenditureTestOne = new Expenditure("Chicken Rice", 15,
                    (temp.parse("10/6/2019")), "Food");
            Transaction expenditureTestTwo = new Expenditure("Bubble Tea", 10,
                    (temp.parse("10/7/2019")), "Food");
            Transaction depositTest = new Deposit("Fund Received", 100,
                    (temp.parse("11/9/2019")), "Deposit");

            transactionListTemp.addExpenditureToList(expenditureTestOne, uiTest, "saving");
            transactionListTemp.addExpenditureToList(expenditureTestTwo, uiTest, "saving");
            transactionListTemp.addExpenditureToList(depositTest, uiTest, "saving");

        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        try {
            outContent.reset();
            transactionListTemp.findMatchingTransaction("",
                    "", "", "transport", uiTest);
            String expectedOutput = "No matches for the category keyword: transport" + NEWLINE;
            assertEquals(expectedOutput,outContent.toString());
            outContent.reset();
        } catch (TransactionException error) {
            System.out.println("Expected no throw, but error thrown");
        }

    }

    @Test
    void findMatchingTransaction_MatchingDescription_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        TransactionList transactionListTemp = new TransactionList();
        try {
            Transaction expenditureTestOne = new Expenditure("Chicken Rice", 15,
                    (temp.parse("10/6/2019")), "Food");
            Transaction expenditureTestTwo = new Expenditure("Bubble Tea", 10,
                    (temp.parse("10/7/2019")), "Food");
            Transaction depositTest = new Deposit("Fund Received", 100,
                    (temp.parse("11/9/2019")), "Deposit");

            transactionListTemp.addExpenditureToList(expenditureTestOne, uiTest, "saving");
            transactionListTemp.addExpenditureToList(expenditureTestTwo, uiTest, "saving");
            transactionListTemp.addExpenditureToList(depositTest, uiTest, "saving");

        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        try {
            outContent.reset();
            transactionListTemp.findMatchingTransaction("",
                    "", "rice", "", uiTest);
            String expectedOutput = "Find by: description" + NEWLINE
                    + "Transaction No.      Description                                             "
                    + "Amount          Date                 Category             " + NEWLINE
                    + "-------------------------------------------------------------------------------"
                    + "--------------------------------------------------" + NEWLINE
                    + "1                    Chicken Rice                                            "
                    + "[-] $15.00      10 June 2019         Food                 " + NEWLINE
                    + "--------------------------------------------------------------------------"
                    + "-------------------------------------------------------" + NEWLINE;
            assertEquals(expectedOutput,outContent.toString());
            outContent.reset();
        } catch (TransactionException error) {
            System.out.println("Expected no throw, but error thrown");
        }

    }

    @Test
    void findMatchingTransaction_DescriptionMissMatch_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        TransactionList transactionListTemp = new TransactionList();
        try {
            Transaction expenditureTestOne = new Expenditure("Chicken Rice", 15,
                    (temp.parse("10/6/2019")), "Food");
            Transaction expenditureTestTwo = new Expenditure("Bubble Tea", 10,
                    (temp.parse("10/7/2019")), "Food");
            Transaction depositTest = new Deposit("Fund Received", 100,
                    (temp.parse("11/9/2019")), "Deposit");

            transactionListTemp.addExpenditureToList(expenditureTestOne, uiTest, "saving");
            transactionListTemp.addExpenditureToList(expenditureTestTwo, uiTest, "saving");
            transactionListTemp.addExpenditureToList(depositTest, uiTest, "saving");

        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        try {
            outContent.reset();
            transactionListTemp.findMatchingTransaction("",
                    "", "soup", "", uiTest);
            String expectedOutput = "No matches for the description keyword: soup" + NEWLINE;
            assertEquals(expectedOutput,outContent.toString());
            outContent.reset();
        } catch (TransactionException error) {
            System.out.println("Expected no throw, but error thrown");
        }

    }
}
