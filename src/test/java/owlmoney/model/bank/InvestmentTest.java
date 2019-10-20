package owlmoney.model.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.BondStub;
import owlmoney.model.transaction.Expenditure;
import owlmoney.model.transaction.Transaction;
import owlmoney.ui.Ui;

/**
 * Tests the constructor of Investment
 */
class InvestmentTest {
    @Test
    void Investment_normalInvestmentAccount_success() {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 10000);
    }
}
