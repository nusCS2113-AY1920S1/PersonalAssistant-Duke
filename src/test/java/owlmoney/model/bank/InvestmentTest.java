package owlmoney.model.bank;

import org.junit.jupiter.api.Test;

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
