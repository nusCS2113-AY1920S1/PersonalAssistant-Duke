package owlmoney.model.bond;

import java.util.ArrayList;

import owlmoney.model.bond.exception.BondException;
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

    /**
     * Gets the size of the bondList.
     *
     * @return the size of the bondList.
     */
    private int getSize() {
        return bondLists.size();
    }

    /**
     * Checks if the bond exists.
     *
     * @param bond the bond object that the user is expecting to add
     * @return     whether the bond name already exists.
     */
    public void bondExist(Bond bond) throws BondException {
        for(int i = 0; i < getSize(); i++) {
            if(bond.getName().equals(bondLists.get(i).getName())) {
                throw new BondException("Bond with the name: " + bond.getName() + " already exists");
            }
        }
    }
    /*public void editBond() {

    }

    public void deleteBondFromList() {

    }*/

}
