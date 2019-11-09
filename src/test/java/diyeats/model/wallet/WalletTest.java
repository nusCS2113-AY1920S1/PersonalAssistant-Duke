package diyeats.model.wallet;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WalletTest {
    private Wallet wallet = new Wallet();
    private LocalDate localDate = LocalDate.of(2019, 1, 1);

    final BigDecimal expectedAmountNormal = new BigDecimal("100");

    //test for valid transaction
    @Test
    public void walletNormalTest() {
        wallet.setAccountBalance("0");
        Payment normalPayment = new Payment("100", localDate);
        Deposit normalDeposit = new Deposit("200", localDate);
        wallet.updateAccountBalance(normalDeposit);
        assertTrue(wallet.addPaymentTransaction(normalPayment));
        assertEquals(wallet.getAccountBalance().compareTo(expectedAmountNormal), 0);
    }

    //test for transaction that exceeds the available amount
    @Test
    public void walletNotEnoughTest() {
        wallet.setAccountBalance("0");
        Deposit notEnoughDeposit = new Deposit("100", localDate);
        Payment notEnoughPayment = new Payment("200", localDate);
        wallet.updateAccountBalance(notEnoughDeposit);
        assertFalse(wallet.addPaymentTransaction(notEnoughPayment));
    }

}
