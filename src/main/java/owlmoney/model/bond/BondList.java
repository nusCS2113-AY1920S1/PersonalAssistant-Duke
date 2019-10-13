package owlmoney.model.bond;

import java.util.ArrayList;

import owlmoney.ui.Ui;

public class BondList {
    private ArrayList<Bond> bondLists;

    /**
     * Creates an arrayList of bonds.
     */
    public BondList() {
        bondLists = new ArrayList<Bond>();
    }

    /**
     * Lists the bonds in the bondList.
     * @param ui required for display.
     * @param displayNum bond number.
     */
    public void listBond(Ui ui, int displayNum) {
        if (bondLists.size() <= 0) {
            ui.printError("There are no bonds");
        } else {
            for (int i = bondLists.size() - 1; i >= 0; i--) {
                ui.printMessage((i + 1) + bondLists.get(i).getName() + "\n");
            }
        }
    }

    /**
     * Add bond to list.
     * @param bond bond object.
     * @param ui required for printing.
     */
    public void addBondToList(Bond bond, Ui ui) {
        bondLists.add(bond);
        ui.printMessage("Added bond:\n" + bond.getName());
    }

    /*public void editBond() {

    }

    public void deleteBondFromList() {

    }*/

}
