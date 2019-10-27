package owlmoney.model.transfer;

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
import owlmoney.model.bank.BankList;
import owlmoney.model.bank.Investment;
import owlmoney.model.bank.Saving;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

class TransferFundTest {

    private static final String NEWLINE = System.lineSeparator();
    private static final DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");

    //all transfer function can be found in Banklist.java and Profile.java

    @Test
    void bankListIsAccountExistToTransfer_accountDoesNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList();
        Ui uiTest = new Ui();
        String nameDoNotExist = "No Such Name";
        Bank newSavingAccount = new Saving("Test Saving Account", 1000, 2000);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        try {
            bankList.bankListAddBank(newSavingAccount, uiTest);
            bankList.bankListAddBank(newInvestmentAccount, uiTest);
        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        assertEquals(2, bankList.getBankListSize());
        outContent.reset();

        BankException thrown = assertThrows(BankException.class, () ->
                        bankList.bankListIsAccountExistToTransfer(nameDoNotExist, 10),
                "Expected bankListIsAccountExistToTransfer to throw, but it didn't");
        assertEquals("Unable to transfer fund as bank the sender bank account does not exist: "
                        + nameDoNotExist,
                thrown.getMessage());

    }

    @Test
    void bankListIsAccountExistToTransfer_accountExistButInsufficientMoneyToTransfer_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList();
        Ui uiTest = new Ui();
        Bank newSavingAccount = new Saving("Test Saving Account", 1000, 2000);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        try {
            bankList.bankListAddBank(newSavingAccount, uiTest);
            bankList.bankListAddBank(newInvestmentAccount, uiTest);
        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        assertEquals(2, bankList.getBankListSize());
        outContent.reset();

        BankException thrown = assertThrows(BankException.class, () ->
                        bankList.bankListIsAccountExistToTransfer("Test Investment Account",
                                2000),
                "Expected bankListIsAccountExistToTransfer to throw, but it didn't");
        assertEquals("Insufficient amount for transfer in this bank: Test Investment Account",
                thrown.getMessage());

    }

    @Test
    void bankListIsAccountExistToTransfer_accountExistWithSufficientMoneyToTransfer_returnBankType() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList();
        Ui uiTest = new Ui();
        Bank newSavingAccount = new Saving("Test Saving Account", 1000, 2000);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        String expectedReturnType = "investment";

        try {
            bankList.bankListAddBank(newSavingAccount, uiTest);
            bankList.bankListAddBank(newInvestmentAccount, uiTest);
            outContent.reset();
            String returnType = bankList.bankListIsAccountExistToTransfer("Test Investment Account",
                    500);
            assertEquals(expectedReturnType, returnType);


        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        assertEquals(2, bankList.getBankListSize());

    }

    @Test
    void bankListIsAccountExistToReceive_accountDoesNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList();
        Ui uiTest = new Ui();
        String nameDoNotExist = "No Such Name";
        Bank newSavingAccount = new Saving("Test Saving Account", 1000, 2000);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        try {
            bankList.bankListAddBank(newSavingAccount, uiTest);
            bankList.bankListAddBank(newInvestmentAccount, uiTest);
            outContent.reset();
        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(2, bankList.getBankListSize());

        BankException thrown = assertThrows(BankException.class, () ->
                        bankList.bankListIsAccountExistToReceive(nameDoNotExist),
                "Expected bankListIsAccountExistToReceive to throw, but it didn't");
        assertEquals("Unable to transfer fund as the receiving bank account does not exist: "
                        + nameDoNotExist, thrown.getMessage());
    }

    @Test
    void bankListIsAccountExistToReceive_accountExist_returnBankType() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList();
        Ui uiTest = new Ui();
        Bank newSavingAccount = new Saving("Test Saving Account", 1000, 2000);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        String expectedReturnType = "investment";

        try {
            bankList.bankListAddBank(newSavingAccount, uiTest);
            bankList.bankListAddBank(newInvestmentAccount, uiTest);
            outContent.reset();
            String returnType = bankList.bankListIsAccountExistToReceive("Test Investment Account");
            assertEquals(expectedReturnType, returnType);

        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        assertEquals(2, bankList.getBankListSize());
    }

    @Test
    void transferFund_successfulFundTransferBetweenTwoBankAccount_deductAndAddTheCorrectAmount() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Profile profileTest = new Profile("Test User");
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

    @Test
    void transferFund_senderAccountDoesNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Profile profileTest = new Profile("Test User");
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
        assertEquals("Unable to transfer fund as bank the sender bank account does not exist: "
                        + "No Such Name", thrown.getMessage());
        outContent.reset();
    }

    @Test
    void transferFund_senderAccountExistButInsufficientMoney_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Profile profileTest = new Profile("Test User");
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
                "Expected bankListIsAccountExistToTransfer to throw, but it didn't");
        assertEquals("Insufficient amount for transfer in this bank: Test Investment Account",
                thrown.getMessage());
        outContent.reset();
    }

    @Test
    void transferFund_receiverAccountDoesNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui uiTest = new Ui();
        Profile profileTest = new Profile("Test User");
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
}
