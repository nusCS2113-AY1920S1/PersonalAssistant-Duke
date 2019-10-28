package owlmoney.model.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import owlmoney.model.bank.exception.BankException;
import owlmoney.storage.Storage;
import owlmoney.ui.Ui;

class BankListTest {

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
}
