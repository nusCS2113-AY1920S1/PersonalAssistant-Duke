package owlmoney.model.profile;

import owlmoney.model.bank.Bank;
import owlmoney.model.bank.BankList;
import owlmoney.model.expenditure.Expenditure;

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

    public void addNewBank(Bank newBank) {
        bankList.addBank(newBank);
    }

    public void deleteBank(String bankName) {
        bankList.deleteBank(bankName);
    }

    public void addNewExpenditure(String accName, Expenditure exp) {
        bankList.addExpenditure(accName, exp);
    }

    public void listMyExpenditure() {
        bankList.listBankExpenditure();
    }
}
