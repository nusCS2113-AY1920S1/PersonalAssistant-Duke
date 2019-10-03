package owlmoney.model.profile;

import owlmoney.logic.parser.exception.ParserException;
import owlmoney.model.bank.Bank;
import owlmoney.model.bank.BankList;
import owlmoney.model.expenditure.Expenditure;
import owlmoney.ui.Ui;

public class Profile {
    String username;
    BankList bankList;

    public Profile(String newUserName) {
        this.username = newUserName;
        this.bankList = new BankList();
    }

    public String getUsername() {
        return username;
    }

    /*public void listBanks() {
        bankList.listBanks();
    }
    */

    public void addNewBank(Bank newBank, Ui ui) {
        bankList.addBank(newBank, ui);
    }

    public void deleteBank(String bankName, Ui ui) {
        bankList.deleteBank(bankName, ui);
    }

    public void listBanks(Ui ui) {
        bankList.listBankAccount(ui);
    }

    public void addNewExpenditure(String accName, Expenditure exp, Ui ui){
        bankList.addExpenditure(accName, exp, ui);
    }

    public void deleteExpenditure(int expIndex, String bankName, Ui ui) {
        bankList.deleteExp(expIndex, bankName, ui);
    }

    public void listExpenditure(String listedBank, Ui ui) {
        bankList.listBankExpenditure(listedBank, ui);
    }
}
