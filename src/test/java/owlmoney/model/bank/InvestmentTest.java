package owlmoney.model.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
                + "Date of Purchased    "
                + "Number of Years " + NEWLINE
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------" + NEWLINE
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          " + NEWLINE
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput,outContent.toString());
        outContent.reset();
        testInvestment.investmentListBond(30,uiTest);
        String expectedListOutput = "Item No.             Bond Name                      Amount          Rate       "
                + "Date of Purchased    "
                + "Number of Years " + NEWLINE
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------" + NEWLINE
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          " + NEWLINE
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------" + NEWLINE;
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
                + "Date of Purchased    "
                + "Number of Years " + NEWLINE
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------" + NEWLINE
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          " + NEWLINE
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------" + NEWLINE;
        assertEquals(expectedMessageWhenAdding,outContent.toString());
        outContent.reset();
        String expectedMessageWhenDeleting = "Bond with the following details has been deleted: " + NEWLINE
                + "Item No.             Bond Name                      Amount          Rate       "
                + "Date of Purchased    "
                + "Number of Years " + NEWLINE
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------" + NEWLINE
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          " + NEWLINE
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------" + NEWLINE;
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
                "Expected investmentGetBond to throw, but it didn't");
        String actualMessage = thrown.getMessage();
        String expectedMessage = "The year can only be larger than: 3";
        assertEquals(expectedMessage,actualMessage);
    }
}
