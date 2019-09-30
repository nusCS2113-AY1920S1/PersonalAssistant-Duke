package owlmoney.model.bank;

import java.util.ArrayList;

import owlmoney.model.expenditure.Expenditure;
import owlmoney.ui.Ui;

public class BankList {
    private ArrayList<Bank> bankLists;

    public BankList() {
        bankLists = new ArrayList<Bank>();
    }

    //for testing in case
    public String getBankName(int bankListIndex) {
        return bankLists.get(bankListIndex).getAccountName();
    }

    //to list all banks
    public void listBanks() {
        if(bankLists.size() <=0) {
            System.out.println("No bank account");
        } else {
            for (int i = 0; i < bankLists.size(); i++) {
                bankLists.get(i).getDescription();
            }
        }
    }

    public void addBank(Bank newBank) {
        bankLists.add(newBank);
    }

    //need to SLAP
    public void deleteBank(String bankName) {
        if(bankLists.size() <=0) {
            System.out.println("No bank account");
        } else {
            for (int i = 0; i < bankLists.size(); i++) {
                if(bankLists.get(i).getAccountName().equals(bankName)) {
                    bankLists.remove(i);
                    break;
                }
            }
        }
    }

    public void addExpenditure(String accName, Expenditure exp) {
        for (int i = 0; i < bankLists.size(); i++)
        {
            if(bankLists.get(i).getAccountName().equals(accName)) {
                bankLists.get(i).addInExpenditure(exp);
                break;
            }
        }
    }

    public void listBankExpenditure() {
        for(int i = 0; i < bankLists.size(); i++) {
            bankLists.get(i).listAllExpenditure();
        }
    }
}
