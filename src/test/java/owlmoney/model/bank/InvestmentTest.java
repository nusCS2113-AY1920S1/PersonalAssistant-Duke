package owlmoney.model.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.transaction.Deposit;
import owlmoney.model.transaction.Expenditure;
import owlmoney.ui.Ui;

class InvestmentTest {
    private static final String NEWLINE = System.lineSeparator();

    @Test
    void investmentListBond_noBond_throwsBondException() {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 10000);
        BondException thrown = assertThrows(BondException.class, () ->
                        testInvestment.investmentListBond(10,uiTest),
                "There are no bonds");
        String expectedMessage = "There are no bonds";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void investmentListBond_bondPresent_success() throws BankException, BondException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 10000);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        testInvestment.addBondToInvestmentAccount(testBond,uiTest);
        String expectedOutput = "Bond with the following details has been added: " + NEWLINE
                + "Item No.             Bond Name                      Amount          Rate       "
                + "Date of Purchase     "
                + "Number of Years " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------------------" + NEWLINE
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput,outContent.toString());
        outContent.reset();
        testInvestment.investmentListBond(30,uiTest);
        String expectedListOutput = "Item No.             Bond Name                      Amount          Rate       "
                + "Date of Purchase     "
                + "Number of Years " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------------------" + NEWLINE
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------------------" + NEWLINE;
        assertEquals(expectedListOutput,outContent.toString());
    }

    @Test
    void investmentAddInExpenditure_noMoneyInInvestmentAccount_throwsBankException() {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 100);
        Expenditure testExpenditure = new Expenditure("test bond expenditure 1",
                150, new Date("1/3/2019"),"bonds");
        BankException thrown = assertThrows(BankException.class, () ->
                        testInvestment.addInExpenditure(testExpenditure,uiTest,"bonds"),
                "Expected AddInExpenditure to throw, but it didn't");
        String expectedMessage = "Bank account cannot have a negative amount";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void investmentAddInExpenditure_gotMoneyInInvestmentAccount_success() throws BankException {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 150);
        Expenditure testExpenditure = new Expenditure("test bond expenditure 1",
                150, new Date("1/3/2019"),"bonds");
        testInvestment.addInExpenditure(testExpenditure,uiTest,"bonds");
        double expectedAmountInInvestmentAccount = 0;
        double actualAmountInInvestmentAccount = testInvestment.getCurrentAmount();
        assertEquals(expectedAmountInInvestmentAccount,actualAmountInInvestmentAccount);
    }

    @Test
    void investmentAddInExpenditure_gotMoneyInInvestmentAccountButNotBondsCategory_throwsBankException()
            throws BankException {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 150);
        Expenditure testExpenditure = new Expenditure("test dining expenditure 1",
                150, new Date("1/3/2019"),"dining");
        BankException thrown = assertThrows(BankException.class, () ->
                testInvestment.addInExpenditure(testExpenditure,uiTest,"dining"),
                "Expected AddInExpenditure to throw, but it didn't");
        String expectedMessage = "This account does not support savings expenditures";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void addDepositTransaction_ButNotBondsCategory_throwsBankException() {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 150);
        Deposit testDeposit = new Deposit("test deposit 1",150,
                new Date("1/3/2019"),"dining");
        BankException thrown = assertThrows(BankException.class, () ->
                        testInvestment.addDepositTransaction(testDeposit,uiTest,"dining"),
                "Expected addDepositTransaction to throw, but it didn't");
        String expectedMessage = "This account does not support this feature";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void addDepositTransaction_bondsDeposit_success() throws BankException {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 150);
        Deposit testDeposit = new Deposit("test deposit 1",150,
                new Date("1/3/2019"),"bonds");
        testInvestment.addDepositTransaction(testDeposit,uiTest,"bonds");
        double expectedAmountInInvestmentAccount = 300;
        double actualAmountInInvestmentAccount = testInvestment.getCurrentAmount();
        assertEquals(expectedAmountInInvestmentAccount,actualAmountInInvestmentAccount);
    }

    @Test
    void investmentCheckBondExist_bondExist_throwsBondException() throws BankException {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 100);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        Bond testBondTwo = new Bond("TEST BOND 0", 500, 2.0,
                new Date("1/1/2019"), 3);
        testInvestment.addBondToInvestmentAccount(testBond,uiTest);
        BondException thrown = assertThrows(BondException.class, () ->
                        testInvestment.investmentCheckBondExist(testBondTwo),
                "Expected CheckBondExist to throw, but it didn't");
        String expectedMessage = "Bond with the name: TEST BOND 0 already exists";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void investmentDeleteBond_noBond_throwsBondException() throws BankException {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 100);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        testInvestment.addBondToInvestmentAccount(testBond,uiTest);
        BondException thrown = assertThrows(BondException.class, () ->
                        testInvestment.investmentDeleteBond("TEST BOND 1",uiTest),
                "Expected investmentDeleteBond to throw, but it didn't");
        String expectedMessage = "There are no bonds with the name: TEST BOND 1";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void investmentDeleteBond_bondExist_success() throws BankException, BondException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 100);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        testInvestment.addBondToInvestmentAccount(testBond,uiTest);
        String expectedMessageWhenAdding = "Bond with the following details has been added: " + NEWLINE
                + "Item No.             Bond Name                      Amount          Rate       "
                + "Date of Purchase     "
                + "Number of Years " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------------------" + NEWLINE
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------------------" + NEWLINE;
        assertEquals(expectedMessageWhenAdding,outContent.toString());
        outContent.reset();
        String expectedMessageWhenDeleting = "Bond with the following details has been deleted: " + NEWLINE
                + "Item No.             Bond Name                      Amount          Rate       "
                + "Date of Purchase     "
                + "Number of Years " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------------------" + NEWLINE
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------------------" + NEWLINE;
        testInvestment.investmentDeleteBond("TEST BOND 0",uiTest);
        assertEquals(expectedMessageWhenDeleting,outContent.toString());
    }

    @Test
    void investmentGetBond_bondExists_success() throws BankException, BondException {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 10000);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        testInvestment.addBondToInvestmentAccount(testBond,uiTest);
        Bond actualBond = testInvestment.investmentGetBond("TEST BOND 0");
        String actualBondName = actualBond.getName();
        String expectedBondName = "TEST BOND 0";
        assertEquals(expectedBondName,actualBondName);
    }

    @Test
    void investmentGetBond_bondDoesNotExist_throwsBondException() throws BankException {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 10000);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        testInvestment.addBondToInvestmentAccount(testBond,uiTest);
        BondException thrown = assertThrows(BondException.class, () ->
                        testInvestment.investmentGetBond("TEST BOND 1"),
                "Expected investmentGetBond to throw, but it didn't");
        String actualMessage = thrown.getMessage();
        String expectedMessage = "There are no bonds with the name: TEST BOND 1";
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void investmentEditBond_bondExist_success() throws BankException, BondException {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 10000);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        testInvestment.addBondToInvestmentAccount(testBond,uiTest);
        testInvestment.investmentEditBond("TEST BOND 0","4","2.0",uiTest);
        Bond actualBond = testInvestment.investmentGetBond("TEST BOND 0");
        int actualYear = actualBond.getYear();
        int expectedYear = 4;
        double actualRate = actualBond.getYearlyCouponRate();
        double expectedRate = 2.0;
        assertEquals(expectedYear,actualYear);
        assertEquals(expectedRate,actualRate);
    }

    @Test
    void investmentEditBond_bondExistLowerYearThanOriginal_throwsBondException()
            throws BankException, BondException {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 10000);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        testInvestment.addBondToInvestmentAccount(testBond,uiTest);
        BondException thrown = assertThrows(BondException.class, () ->
                        testInvestment.investmentEditBond("TEST BOND 0","2","2.0",uiTest),
                "Expected investmentEditBond to throw, but it didn't");
        String actualMessage = thrown.getMessage();
        String expectedMessage = "The year can only be larger than: 3";
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void updateRecurringTransaction_bondExistNoMature_successMoneyCredited() throws BankException {
        Ui uiTest = new Ui();
        Calendar calendarTestDate = Calendar.getInstance();
        calendarTestDate.add(Calendar.YEAR,-1);
        Date testDate = calendarTestDate.getTime();
        Bank testInvestment = new Investment("DBB VICKERS", 10000);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.0,
                testDate, 3);
        testInvestment.addBondToInvestmentAccount(testBond,uiTest);
        testInvestment.updateRecurringTransactions(uiTest);
        double actualAmount = testInvestment.getCurrentAmount();
        double expectedAmount = 10010;
        assertEquals(actualAmount,expectedAmount);
    }

    @Test
    void updateRecurringTransaction_bondExistMature_throwsBondException() throws BankException {
        Ui uiTest = new Ui();
        Calendar calendarTestDate = Calendar.getInstance();
        calendarTestDate.add(Calendar.YEAR,-3);
        Date testDate = calendarTestDate.getTime();
        Bank testInvestment = new Investment("DBB VICKERS", 10000);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.0,
                testDate, 3);
        testInvestment.addBondToInvestmentAccount(testBond,uiTest);
        testInvestment.updateRecurringTransactions(uiTest);
        double actualAmount = testInvestment.getCurrentAmount();
        double expectedAmount = 11030;
        assertEquals(actualAmount,expectedAmount);
        BondException thrown = assertThrows(BondException.class, () ->
                        testInvestment.investmentGetBond("TEST BOND 0"),
                "Expected investmentGetBond to throw after bond matures and deleted, but it didn't");
        String actualMessage = thrown.getMessage();
        String expectedMessage = "There are no bonds with the name: TEST BOND 0";
        assertEquals(actualMessage,expectedMessage);
    }

    //Tests function for find feature.
    @Test
    void findBondInInvestment_bondNameThatExist_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("Test Investment Account", 10000);
        Bond testBondOne = new Bond("TEST BOND 1", 1000, 1.8,
                new Date("1/3/2019"), 3);
        Bond testBondTwo = new Bond("TEST BOND 2", 1000, 1.8,
                new Date("1/3/2019"), 3);
        try {
            testInvestment.addBondToInvestmentAccount(testBondOne, uiTest);
            testInvestment.addBondToInvestmentAccount(testBondTwo, uiTest);
            outContent.reset();

            testInvestment.findBondInInvestment("2", uiTest);

            String expectedOutput = "Item No.             Bond Name                      "
                    + "Amount          Rate       Date of Purchase     Number of Years " + NEWLINE
                    + "-----------------------------------------------------------------------------"
                    + "----------------------------------------------------------------" + NEWLINE
                    + "1                    TEST BOND 2                    $1000.00        "
                    + "1.80       03 January 2019      3          " + NEWLINE
                    + "-----------------------------------------------------------------------------"
                    + "----------------------------------------------------------------" + NEWLINE;
            assertEquals(expectedOutput,outContent.toString());


        } catch (BankException | BondException error) {
            System.out.println("Expected no throw, but error thrown");
        }
    }

    //Tests function for find feature.
    @Test
    void findBondInInvestment_bondNameDoesNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("Test Investment Account", 10000);
        Bond testBondOne = new Bond("TEST BOND 1", 1000, 1.8,
                new Date("1/3/2019"), 3);
        Bond testBondTwo = new Bond("TEST BOND 2", 1000, 1.8,
                new Date("1/3/2019"), 3);
        try {
            testInvestment.addBondToInvestmentAccount(testBondOne, uiTest);
            testInvestment.addBondToInvestmentAccount(testBondTwo, uiTest);
            outContent.reset();

        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        BondException thrown = assertThrows(BondException.class, () ->
                        testInvestment.findBondInInvestment("No Such Bond", uiTest),
                "Expected findBondInInvestment to throw after bond matures and deleted, but it didn't");
        assertEquals("Bond with the following keyword could not be found: No Such Bond",
                thrown.getMessage());
    }
}
