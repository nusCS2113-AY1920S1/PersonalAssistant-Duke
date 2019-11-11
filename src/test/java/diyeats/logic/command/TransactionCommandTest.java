package diyeats.logic.command;

import diyeats.logic.commands.AddTransactionCommand;
import diyeats.logic.commands.Command;
import diyeats.logic.dummy.DummyMealList;
import diyeats.logic.dummy.DummyStorage;
import diyeats.logic.dummy.DummyUser;
import diyeats.logic.dummy.DummyWallet;
import diyeats.model.meal.ExerciseList;
import diyeats.model.wallet.Deposit;
import diyeats.model.wallet.Payment;
import diyeats.model.undo.Undo;
import org.junit.jupiter.api.Test;

import static diyeats.model.user.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionCommandTest {
    private static LocalDate currDate = LocalDate.now();

    /**
     * Test for normal transaction.
     */
    @Test
    public void executeTest() {
        DummyWallet dummyWallet = new DummyWallet();
        dummyWallet.setAccountBalance("300");
        Deposit depositTest = new Deposit("500", currDate);
        Payment paymentTest = new Payment("300", currDate);
        BigDecimal correctAmount = new BigDecimal("500");
        dummyWallet.updateAccountBalance(depositTest);
        dummyWallet.updateAccountBalance(paymentTest);
        assertEquals(dummyWallet.getAccountBalance(), correctAmount);
    }

    /**
     * test to make sure the account is not deducted if payment amount is too large.
     */
    @Test
    public void executeInvalidTest() {
        DummyMealList dummyMealList = new DummyMealList(new ExerciseList());
        DummyWallet dummyWallet = new DummyWallet();
        DummyStorage dummyStorage = new DummyStorage();
        DummyUser dummyUser = new DummyUser("Hashir", 22, 175, MALE,
                3, 70, null);
        Undo undo = new Undo();
        dummyWallet.setAccountBalance("200");
        Payment paymentTest = new Payment("300", currDate);

        try {
            Command command = new AddTransactionCommand(paymentTest);
            command.execute(dummyMealList, dummyStorage, dummyUser, dummyWallet, undo);
        } catch (Exception e) {
            fail();
        }
        assertEquals(dummyWallet.getAccountBalance(), new BigDecimal("200"));
    }
}
