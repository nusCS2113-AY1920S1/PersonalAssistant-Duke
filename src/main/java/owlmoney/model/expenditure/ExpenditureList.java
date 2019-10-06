package owlmoney.model.expenditure;

import java.util.ArrayList;

import owlmoney.ui.Ui;

/**
 * The ExpenditureList class that provides a layer of abstraction for the ArrayList that stores expenditures.
 */

public class ExpenditureList {

    private ArrayList<Expenditure> expLists;

    /**
     * Constructor that creates an arrayList of expenditures.
     */
    public ExpenditureList() {
        expLists = new ArrayList<Expenditure>();
    }

    /**
     * Lists the expenditure in the expenditureList.
     *
     * @param ui required for printing.
     */
    public void listExpenditure(Ui ui) {
        if (expLists.size() <= 0) {
            ui.printError("no expenditure found!");
        } else {
            for (int i = 0; i < expLists.size(); i++) {
                ui.printMessage((i + 1) + ":\n" + expLists.get(i).getDetails());
            }
        }
    }

    /**
     * Adds an expenditure to the expenditureList.
     *
     * @param exp an instance of an expenditure.
     * @param ui  required for printing.
     */
    public void addToList(Expenditure exp, Ui ui) {
        expLists.add(exp);
        ui.printMessage("Added expenditure:\n" + exp.getDetails());
    }

    /**
     * Deletes an expenditure to the expenditureList.
     *
     * @param index index of the expenditure in the expenditureList.
     * @param ui    required for printing.
     */
    //magic int used. change next time
    public void deleteFromList(int index, Ui ui) {
        if (expLists.size() <= 0) {
            ui.printError("There are no expenditures in this bank");
            return;
        }
        if ((index - 1) >= 0 && (index - 1) < expLists.size()) {
            Expenditure temp = expLists.get(index - 1);
            expLists.remove(index - 1);
            ui.printMessage("Expenditure deleted:" + temp.getDetails());
        } else {
            ui.printError("Out of expenditure list range");
        }
    }
}
