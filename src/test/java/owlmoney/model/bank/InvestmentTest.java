package owlmoney.model.bank;

import org.junit.jupiter.api.Test;

import owlmoney.ui.Ui;

class InvestmentTest {
    @Test
    void investment_normalInvestmentAccount_success() {
        Ui uiTest = new Ui();
        Bank testInvestment = new Investment("DBB VICKERS", 10000);
    }

    @Test
    void addInExpenditure_normalExpenditureBankNotInvestment_success() {

    }
}
