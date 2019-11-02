package owlmoney.model.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import owlmoney.model.bank.Bank;
import owlmoney.model.bank.Investment;
import owlmoney.model.bank.Saving;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.exception.BondException;
import owlmoney.ui.Ui;

class ProfileTest {
    private static final String NEWLINE = System.lineSeparator();
    private static final DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");

    //Tests function for transfer feature.
    @Test
    void transferFund_successfulFundTransferBetweenTwoBankAccount_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Profile profileTest = new Profile("Test User", uiTest);
        Bank newSavingAccount = new Saving("Test Saving Account", 1000, 2000);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        Date newDate = new Date();
        try {
            newDate = temp.parse("10/2/2019");
        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        try {
            profileTest.profileAddNewBank(newSavingAccount, uiTest);
            profileTest.profileAddNewBank(newInvestmentAccount, uiTest);
            profileTest.transferFund("Test Saving Account", "Test Investment Account",
                    100, newDate, uiTest);
            assertEquals(900, newSavingAccount.getCurrentAmount());
            assertEquals(1100, newInvestmentAccount.getCurrentAmount());
        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        outContent.reset();
    }

    //Tests function for transfer feature.
    @Test
    void transferFund_senderAccountDoesNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Profile profileTest = new Profile("Test User", uiTest);
        Bank newSavingAccount = new Saving("Test Saving Account", 1000, 2000);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        Date newDate = new Date();
        try {
            newDate = temp.parse("10/2/2019");
        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        try {
            profileTest.profileAddNewBank(newSavingAccount, uiTest);
            profileTest.profileAddNewBank(newInvestmentAccount, uiTest);
        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }


        Date finalNewDate = newDate;
        BankException thrown = assertThrows(BankException.class, () -> profileTest.transferFund(
                "No Such Name", "Test Investment Account",
                100, finalNewDate, uiTest),
                "Expected transferFund to throw, but it didn't");
        assertEquals("Unable to transfer fund as the sender bank account does not exist: "
                + "No Such Name", thrown.getMessage());
        outContent.reset();
    }

    //Tests function for transfer feature.
    @Test
    void transferFund_senderAccountExistButInsufficientMoney_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Profile profileTest = new Profile("Test User", uiTest);
        Bank newSavingAccount = new Saving("Test Saving Account", 1000, 2000);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        Date newDate = new Date();
        try {
            newDate = temp.parse("10/2/2019");
        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        try {
            profileTest.profileAddNewBank(newSavingAccount, uiTest);
            profileTest.profileAddNewBank(newInvestmentAccount, uiTest);
        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        Date finalNewDate = newDate;
        BankException thrown = assertThrows(BankException.class, () ->
                        profileTest.transferFund(
                                "Test Investment Account", "Test Saving Account",
                                2000, finalNewDate, uiTest),
                "Expected transferFund to throw, but it didn't");
        assertEquals("Insufficient amount for transfer in this bank: Test Investment Account",
                thrown.getMessage());
        outContent.reset();
    }

    //Tests function for transfer feature.
    @Test
    void transferFund_receiverAccountDoesNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Profile profileTest = new Profile("Test User", uiTest);
        Bank newSavingAccount = new Saving("Test Saving Account", 1000, 2000);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        Date newDate = new Date();
        try {
            newDate = temp.parse("10/2/2019");
        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        try {
            profileTest.profileAddNewBank(newSavingAccount, uiTest);
            profileTest.profileAddNewBank(newInvestmentAccount, uiTest);
        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }


        Date finalNewDate = newDate;
        BankException thrown = assertThrows(BankException.class, () -> profileTest.transferFund(
                "Test Saving Account", "No Such Name",
                100, finalNewDate, uiTest),
                "Expected transferFund to throw, but it didn't");
        assertEquals("Unable to transfer fund as the receiving bank account does not exist: "
                + "No Such Name", thrown.getMessage());
        outContent.reset();
    }

    //Tests function for find feature.
    @Test
    void findBond_investmentAccountDoesNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Profile profileTest = new Profile("Test User", uiTest);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        Date newDate = new Date();
        try {
            newDate = temp.parse("10/2/2019");
        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        try {
            profileTest.profileAddNewBank(newInvestmentAccount, uiTest);
        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        BankException thrown = assertThrows(BankException.class, () ->
                        profileTest.findBond("bond", "No Such Investment Account", uiTest),
                "Expected findBond to throw, but it didn't");
        assertEquals("Investment account with the following name does not exist for search: "
                + "No Such Investment Account", thrown.getMessage());
        outContent.reset();
    }

    //Tests function for find feature.
    @Test
    void findBond_BondExist_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Profile profileTest = new Profile("Test User", uiTest);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        Bond testBondOne = new Bond("TEST BOND 1",1000,1.8,new Date("1/3/2019"),
                3);
        Bond testBondTwo = new Bond("TEST BOND 2",1000,1.8,new Date("1/3/2019"),
                3);


        Date newDate = new Date();
        try {
            newDate = temp.parse("10/2/2019");
        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        try {
            profileTest.profileAddNewBank(newInvestmentAccount, uiTest);
            profileTest.profileAddNewBond("Test Investment Account", testBondOne, uiTest);
            profileTest.profileAddNewBond("Test Investment Account", testBondTwo, uiTest);
            outContent.reset();

            profileTest.findBond("2", "Test Investment Account", uiTest);

            String expectedOutput = "Item No.             Bond Name                      "
                    + "Amount          Rate       Date of Purchase     Number of Years " + NEWLINE
                    + "-------------------------------------------------------------------------------"
                    + "--------------------------------------------------" + NEWLINE
                    + "1                    TEST BOND 2                    $1000.00        "
                    + "1.80       03 January 2019      3          " + NEWLINE
                    + "-------------------------------------------------------------------------------"
                    + "--------------------------------------------------" + NEWLINE;
            assertEquals(expectedOutput,outContent.toString());

        } catch (BankException | BondException error) {
            System.out.println("Expected no throw, but error thrown");
        }

    }

    //Tests function for find feature.
    @Test
    void findBond_BondDoesNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Profile profileTest = new Profile("Test User", uiTest);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        Bond testBondOne = new Bond("TEST BOND 1",1000,1.8,new Date("1/3/2019"),
                3);
        Bond testBondTwo = new Bond("TEST BOND 2",1000,1.8,new Date("1/3/2019"),
                3);


        Date newDate = new Date();
        try {
            newDate = temp.parse("10/2/2019");
        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        try {
            profileTest.profileAddNewBank(newInvestmentAccount, uiTest);
            profileTest.profileAddNewBond("Test Investment Account", testBondOne, uiTest);
            profileTest.profileAddNewBond("Test Investment Account", testBondTwo, uiTest);
            outContent.reset();

        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        BondException thrown = assertThrows(BondException.class, () ->
                        profileTest.findBond("No Such Bond",
                                "Test Investment Account", uiTest),
                "Expected findBond to throw error, but it didn't");
        assertEquals("Bond with the following keyword could not be found: No Such Bond",
                thrown.getMessage());
    }
}
