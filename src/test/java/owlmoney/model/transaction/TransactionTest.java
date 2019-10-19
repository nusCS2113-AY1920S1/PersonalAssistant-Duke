package owlmoney.model.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

class TransactionTest {
    @Test
    void testCheckDebitCredit_expenditure_showMinus() {
        Transaction expenditure = new Expenditure("test", 1.0, new Date("1/1/2019"), "test");
        assertEquals("[-] $", expenditure.checkDebitCredit());
    }

    @Test
    void testCheckDebitCredit_deposit_showPlus() {
        Transaction deposit = new Deposit("test", 1.0, new Date("1/1/2019"), "test");
        assertEquals("[+] $", deposit.checkDebitCredit());
    }
}
