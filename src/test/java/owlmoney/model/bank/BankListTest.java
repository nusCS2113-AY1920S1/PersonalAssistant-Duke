package owlmoney.model.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import owlmoney.model.bank.exception.BankException;
import owlmoney.storage.Storage;
import owlmoney.ui.Ui;

class BankListTest {

    private static final String NEWLINE = System.lineSeparator();
    private static final String FILE_PATH = "data/";
    private static final Storage storage = new Storage(FILE_PATH);

    @Test
    void bankListAddBank_duplicateBankName_throwsError() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        Saving newBank = new Saving("test", 123, 123);
        Saving newBank2 = new Saving("test", 456, 456);
        try {
            testList.bankListAddBank(newBank, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                        testList.bankListAddBank(newBank2, testUi),
                "Expected bankListAddBank to throw, but it didn't");
        assertEquals("There is already a bank account with the name test", thrown.toString());
    }

    @Test
    void bankListAddBank_maxLimit_throwsError() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        for (int i = 0; i < 7; i++) {
            Saving newBank = new Saving(i + "test", 123, 123);
            try {
                testList.bankListAddBank(newBank, testUi);
            } catch (BankException errorMessage) {
                System.out.println("Expects success but error was thrown");
            }
        }
        for (int i = 7; i < 10; i++) {
            Investment newBank = new Investment(i + "test", 123);
            try {
                testList.bankListAddBank(newBank, testUi);
            } catch (BankException errorMessage) {
                System.out.println("Expects success but error was thrown");
            }
        }
        Saving newBankError = new Saving("test", 123, 123);
        Investment newInvestmentError = new Investment("test", 123);
        BankException thrown = assertThrows(BankException.class, () ->
                        testList.bankListAddBank(newBankError, testUi),
                "Expected bankListAddBank to throw, but it didn't");
        assertEquals("The maximum limit of 7 savings account has been reached", thrown.toString());
        BankException thrown2 = assertThrows(BankException.class, () ->
                        testList.bankListAddBank(newInvestmentError, testUi),
                "Expected bankListAddBank to throw, but it didn't");
        assertEquals("The maximum limit of 3 investment account has been reached", thrown2.toString());
    }

    @Test
    void getBankListSize_zeroAndOneAccount_returnsZeroFirstThenOne() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        assertEquals(0, testList.getBankListSize());
        Saving newBank = new Saving("test", 123, 123);
        try {
            testList.bankListAddBank(newBank, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        assertEquals(1, testList.getBankListSize());
    }

    @Test
    void bankListGetSavingAccount_cannotFindBank_throwsException() {
        BankList testList = new BankList(new Storage("data/"));
        BankException thrown = assertThrows(BankException.class, () ->
                        testList.bankListGetSavingAccount("NOBANKFOUND"),
                "Expected bankListGetSavingAccount to throw, but it didn't");
        assertEquals("Cannot find savings account with the name: NOBANKFOUND", thrown.toString());
    }

    @Test
    void bankListGetSavingAccount_foundBank_returnsBank() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        assertEquals(0, testList.getBankListSize());
        Saving newBank = new Saving("test", 123, 123);
        try {
            testList.bankListAddBank(newBank, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        Bank foundBank = null;
        try {
            foundBank = testList.bankListGetSavingAccount("test");
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        assertEquals(newBank, foundBank);
    }

    @Test
    void bankListDeleteBank_zeroBankAccount_throwsException() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        BankException thrown = assertThrows(BankException.class, () ->
                        testList.bankListDeleteBank("test", "saving", testUi),
                "Expected bankListDeleteBank to throw, but it didn't");
        assertEquals("There are 0 bank accounts in your profile", thrown.toString());
    }

    @Test
    void bankListDeleteBank_deleteLastSavingAccount_throwsException() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        Saving newBank = new Saving("test", 123, 123);
        try {
            testList.bankListAddBank(newBank, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                        testList.bankListDeleteBank("test", "saving", testUi),
                "Expected bankListDeleteBank to throw, but it didn't");
        assertEquals("There must be at least 1 savings account", thrown.toString());
    }

    @Test
    void bankListDeleteBank_bankAccountDoesNotExist_throwsException() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        Saving newBank = new Saving("test", 123, 123);
        Saving newBank2 = new Saving("test3", 456, 456);
        try {
            testList.bankListAddBank(newBank, testUi);
            testList.bankListAddBank(newBank2, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                        testList.bankListDeleteBank("test2", "saving", testUi),
                "Expected bankListDeleteBank to throw, but it didn't");
        assertEquals("There are no bank accounts with name test2", thrown.toString());
    }

    @Test
    void bankListDeleteBank_wrongAccountType_throwsException() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        Saving newBank = new Saving("test", 123, 123);
        try {
            testList.bankListAddBank(newBank, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                        testList.bankListDeleteBank("test", "investment", testUi),
                "Expected bankListDeleteBank to throw, but it didn't");
        assertEquals("test is not of type: investment", thrown.toString());
    }

    @Test
    void bankListEditSavings_duplicateName_throwsException() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        Saving newBank = new Saving("test", 123, 123);
        Saving newBank2 = new Saving("test2", 456, 456);
        try {
            testList.bankListAddBank(newBank, testUi);
            testList.bankListAddBank(newBank2, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                        testList.bankListEditSavings("test", "test2", "", "", testUi),
                "Expected bankListEditSavings to throw, but it didn't");
        assertEquals("There is already a bank account with the name test2", thrown.toString());
    }

    @Test
    void bankListEditSavings_cannotFindBank_throwsException() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        Saving newBank = new Saving("test", 123, 123);
        try {
            testList.bankListAddBank(newBank, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                        testList.bankListEditSavings("test4", "test2", "", "", testUi),
                "Expected bankListEditSavings to throw, but it didn't");
        assertEquals("There are no bank with the name: test4", thrown.toString());
    }

    @Test
    void bankListEditInvestment_duplicateName_throwsException() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        Investment newBank = new Investment("test", 123);
        Saving newBank2 = new Saving("test2", 456, 456);
        try {
            testList.bankListAddBank(newBank, testUi);
            testList.bankListAddBank(newBank2, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                        testList.bankListEditInvestment("test", "test2", "", testUi),
                "Expected bankListEditInvestment to throw, but it didn't");
        assertEquals("There is already a bank account with the name test2", thrown.toString());
    }

    @Test
    void bankListEditInvestment_bankNotFound_throwsException() {
        BankList testList = new BankList(new Storage("data/"));
        Ui testUi = new Ui();
        Investment newBank = new Investment("test", 123);
        try {
            testList.bankListAddBank(newBank, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        BankException thrown = assertThrows(BankException.class, () ->
                        testList.bankListEditInvestment("test4", "test2", "", testUi),
                "Expected bankListEditInvestment to throw, but it didn't");
        assertEquals("There are no bank with the name: test4", thrown.toString());
    }

    //Tests function for transfer feature.
    @Test
    void bankListIsAccountExistToTransfer_accountDoesNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList(storage);
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
                        bankList.getTransferBankType("No Such Name", 10),
                "Expected bankListIsAccountExistToTransfer to throw, but it didn't");
        assertEquals("Unable to transfer fund as bank the sender bank account does not exist: "
                + "No Such Name", thrown.getMessage());

    }

    //Tests function for transfer feature.
    @Test
    void bankListIsAccountExistToTransfer_accountExistButInsufficientMoneyToTransfer_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList(storage);
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
                        bankList.getTransferBankType("Test Investment Account",
                                2000),
                "Expected bankListIsAccountExistToTransfer to throw, but it didn't");
        assertEquals("Insufficient amount for transfer in this bank: Test Investment Account",
                thrown.getMessage());

    }

    //Tests function for transfer feature.
    @Test
    void bankListIsAccountExistToTransfer_accountExistWithSufficientMoneyToTransfer_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList(storage);
        Ui uiTest = new Ui();
        Bank newSavingAccount = new Saving("Test Saving Account", 1000, 2000);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        String expectedReturnType = "investment";

        try {
            bankList.bankListAddBank(newSavingAccount, uiTest);
            bankList.bankListAddBank(newInvestmentAccount, uiTest);
            outContent.reset();
            String returnType = bankList.getTransferBankType("Test Investment Account",
                    500);
            assertEquals(expectedReturnType, returnType);


        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        assertEquals(2, bankList.getBankListSize());

    }

    //Tests function for transfer feature.
    @Test
    void bankListIsAccountExistToReceive_accountDoesNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList(storage);
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
                        bankList.getReceiveBankType(nameDoNotExist),
                "Expected bankListIsAccountExistToReceive to throw, but it didn't");
        assertEquals("Unable to transfer fund as the receiving bank account does not exist: "
                + nameDoNotExist, thrown.getMessage());
    }

    //Tests function for transfer feature.
    @Test
    void bankListIsAccountExistToReceive_accountExist_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BankList bankList = new BankList(storage);
        Ui uiTest = new Ui();
        Bank newSavingAccount = new Saving("Test Saving Account", 1000, 2000);
        Bank newInvestmentAccount = new Investment("Test Investment Account", 1000);
        String expectedReturnType = "investment";

        try {
            bankList.bankListAddBank(newSavingAccount, uiTest);
            bankList.bankListAddBank(newInvestmentAccount, uiTest);
            outContent.reset();
            String returnType = bankList.getReceiveBankType("Test Investment Account");
            assertEquals(expectedReturnType, returnType);

        } catch (BankException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        assertEquals(2, bankList.getBankListSize());
    }

    //Tests function for find feature.
    @Test
    void findBankAccount_AccountNameMatch_success() {
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

    //Tests function for find feature.
    @Test
    void findBankAccount_savingsAccountNameDoNotMatch_throwsException() {
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

    //Tests function for find feature.
    @Test
    void findBankAccount_investmentAccountNameDoNotMatch_throwsException() {
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
    void bankListAddBank_successfulAdd_printBankDetails() {
        Storage testStorage = new Storage("data/");
        testStorage.createDirectoryIfNotExist("data/");
        BankList testList = new BankList(testStorage);
        Ui testUi = new Ui();
        Saving newBank = new Saving("test", 123, 123);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            testList.bankListAddBank(newBank, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        String outputMessage = "Added new bank with following details: " + NEWLINE + "Item No.             "
                + "Account Name                        Account Type    Current Amount  Income          "
                + NEWLINE + "--------------------------------------------------------------------------------"
                + "-------------------------------------------------" + NEWLINE + "1                    test "
                + "                               saving          $123.00         $123.00         " + NEWLINE
                + "------------------------------------------------------------------------------------------"
                + "---------------------------------------" + NEWLINE;
        assertEquals(outputMessage, outContent.toString());
        outContent.reset();
        Investment newBank2 = new Investment("invest", 456);
        try {
            testList.bankListAddBank(newBank2, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        String outputMessage2 = "Added new bank with following details: " + NEWLINE + "Item No.             "
                + "Account Name                        Account Type    Current Amount  Income          "
                + NEWLINE + "--------------------------------------------------------------------------------"
                + "-------------------------------------------------" + NEWLINE + "1                    "
                + "invest                              investment      $456.00         Not Applicable  "
                + NEWLINE + "------------------------------------------------------------------------------"
                + "---------------------------------------------------" + NEWLINE;
        assertEquals(outputMessage2,outContent.toString());
    }

    @Test
    void bankListDeleteBank_successfulDelete_displayDeletedBankDetails() {
        Storage testStorage = new Storage("data/");
        testStorage.createDirectoryIfNotExist("data/");
        BankList testList = new BankList(testStorage);
        Ui testUi = new Ui();
        Saving newBank = new Saving("test", 123, 123);
        Saving newBank2 = new Saving("test2", 456, 456);
        try {
            testList.bankListAddBank(newBank, testUi);
            testList.bankListAddBank(newBank2, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        outContent.reset();
        try {
            testList.bankListDeleteBank("test2", "saving", testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        String outputMessage = "Removed bank with the following details: " + NEWLINE + "Item No.             "
                + "Account Name                        Account Type    Current Amount  Income          "
                + NEWLINE + "--------------------------------------------------------------------------------"
                + "-------------------------------------------------" + NEWLINE + "1                    test2"
                + "                               saving          $456.00         $456.00         " + NEWLINE
                + "------------------------------------------------------------------------------------------"
                + "---------------------------------------" + NEWLINE;
        assertEquals(outputMessage, outContent.toString());
    }

    @Test
    void bankListEditSavings_editedBankDetails_displayNewDetails() {
        Storage testStorage = new Storage("data/");
        testStorage.createDirectoryIfNotExist("data/");
        BankList testList = new BankList(testStorage);
        Ui testUi = new Ui();
        Saving newBank = new Saving("test", 123, 123);
        try {
            testList.bankListAddBank(newBank, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        outContent.reset();
        try {
            testList.bankListEditSavings("test", "edit", "456", "456", testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        String outputMessage = "New details of the account:" + NEWLINE + "Item No.             Account Name  "
                + "                      Account Type    Current Amount  Income          " + NEWLINE + "-----"
                + "------------------------------------------------------------------------------------------"
                + "----------------------------------" + NEWLINE + "1                    edit                "
                + "                saving          $456.00         $456.00         " + NEWLINE + "-----------"
                + "------------------------------------------------------------------------------------------"
                + "----------------------------" + NEWLINE;
        assertEquals(outputMessage, outContent.toString());
    }

    @Test
    void bankListEditInvestment_succeedEditing_displaysNewDetails() {
        Storage testStorage = new Storage("data/");
        testStorage.createDirectoryIfNotExist("data/");
        BankList testList = new BankList(testStorage);
        Ui testUi = new Ui();
        Investment newBank = new Investment("test", 123);
        try {
            testList.bankListAddBank(newBank, testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        outContent.reset();
        try {
            testList.bankListEditInvestment("test", "edit", "456", testUi);
        } catch (BankException errorMessage) {
            System.out.println("Expects success but error was thrown");
        }
        String outputMessage = "New details of the account:" + NEWLINE + "Item No.             Account Name  "
                + "                      Account Type    Current Amount  Income          " + NEWLINE + "-----"
                + "------------------------------------------------------------------------------------------"
                + "----------------------------------" + NEWLINE + "1                    edit                "
                + "                investment      $456.00         Not " + "Applicable  " + NEWLINE + "------"
                + "------------------------------------------------------------------------------------------"
                + "---------------------------------" + NEWLINE;
        assertEquals(outputMessage, outContent.toString());
    }
}
