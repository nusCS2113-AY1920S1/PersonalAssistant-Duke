package owlmoney.model.bank;

import java.util.ArrayList;

import owlmoney.logic.parser.exception.ParserException;
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

    public void addBank(Bank newBank, Ui ui) {
        bankLists.add(newBank);
        ui.printMessage("Added new bank: ");
        ui.printMessage(newBank.getDescription());
    }

    //need to SLAP
    public void deleteBank(String bankName, Ui ui) {
        if(bankLists.size() <=0) {
            ui.printError("There are 0 bank accounts in your profile");
        } else {
            for (int i = 0; i < bankLists.size(); i++) {
                if(bankLists.get(i).getAccountName().equals(bankName)) {
                    ui.printMessage("Removing " + bankLists.get(i).getAccountName());
                    bankLists.remove(i);
                    break;
                }
            }
        }
    }

    //need change exception class in the future for this
    public void addExpenditure(String accName, Expenditure exp, Ui ui){
        for (int i = 0; i < bankLists.size(); i++)
        {
            if(bankLists.get(i).getAccountName().equals(accName)) {
                bankLists.get(i).addInExpenditure(exp, ui);
                return;
            }
        }
        ui.printError("There are no account named :" + accName);
    }

    public void listBankAccount(Ui ui) {
        if(bankLists.size() <= 0) {
            ui.printError("There are 0 saving accounts.");
        }
        for(int i = 0; i < bankLists.size(); i++) {
            ui.printMessage((i + 1) + ".\n" + bankLists.get(i).getDescription());
        }
    }

    public void listBankExpenditure(String bankToList, Ui ui) {
        for(int i = 0; i < bankLists.size(); i++) {
            if(bankToList.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).listAllExpenditure(ui);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + bankToList);
    }

    public void deleteExp(int expNum, String deleteFromBank, Ui ui) {
        for(int i = 0; i < bankLists.size(); i++) {
            if(deleteFromBank.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).deleteExpend(expNum, ui);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + deleteFromBank);
    }
}
