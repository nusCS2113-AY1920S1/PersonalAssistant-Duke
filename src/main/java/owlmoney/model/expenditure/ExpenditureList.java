package owlmoney.model.expenditure;

import java.util.ArrayList;

import owlmoney.ui.Ui;

public class ExpenditureList {

    private ArrayList<Expenditure> expLists;

    public ExpenditureList() {
        expLists = new ArrayList<Expenditure>();
    }

    public void listExpenditure(Ui ui) {
        if(expLists.size() <= 0) {
            ui.printError("no expenditure found!");
        } else {
            for (int i = 0; i < expLists.size(); i++) {
                ui.printMessage( (i + 1) + ":\n" + expLists.get(i).getDetails());
            }
        }
    }

    public void addToList(Expenditure exp, Ui ui) {
        expLists.add(exp);
        ui.printMessage("Added expenditure:\n" + exp.getDetails());
    }

    //magic int used. change next time
    public void deleteFromList(int index, Ui ui) {
        if(expLists.size() <= 0) {
            ui.printError("There are no expenditures in this bank");
            return;
        }
        if((index - 1) >=0 && (index - 1) < expLists.size()) {
            Expenditure temp = expLists.get(index-1);
            expLists.remove(index-1);
            ui.printMessage("Expenditure deleted:" + temp.getDetails());
        } else {
            ui.printError("Out of expenditure list range");
        }
    }
}
