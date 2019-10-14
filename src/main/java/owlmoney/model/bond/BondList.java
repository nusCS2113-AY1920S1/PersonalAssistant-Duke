package owlmoney.model.bond;

import java.util.ArrayList;

import owlmoney.model.bond.exception.BondException;
import owlmoney.ui.Ui;

/**
 * BondList  provides a layer of abstraction for the ArrayList.
 * The ArrayList will store elements of type Bond.
 */

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
     *
     * @param displayNum bond number.
     * @param ui         required for display.
     * @throws BondException if there are no bonds.
     */
    public void listBond(int displayNum, Ui ui) throws BondException {
        if (bondLists.size() <= 0) {
            throw new BondException("There are no bonds");
        } else {
            int counter = displayNum;
            for (int i = bondLists.size() - 1; i >= 0; i--) {
                ui.printMessage((i + 1) + ":\n" + bondLists.get(i).getBondDescription() + "\n");
                counter--;
                if (counter <= 0) {
                    break;
                }
            }
        }
    }

    /**
     * Add bond to list.
     *
     * @param bond bond object.
     * @param ui   required for printing.
     */
    public void addBondToList(Bond bond, Ui ui) {
        bondLists.add(bond);
        ui.printMessage("\nAdded bond: ");
        ui.printMessage(bond.getBondDescription());
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
     * @param bond the bond object that the user is expecting to add.
     * @throws BondException If duplicate bond name is found.
     */
    public void bondExist(Bond bond) throws BondException {
        for (int i = 0; i < getSize(); i++) {
            if (bond.getName().equals(bondLists.get(i).getName())) {
                throw new BondException("Bond with the name: " + bond.getName() + " already exists");
            }
        }
    }

    /**
     * Removes the bond from the bondList.
     *
     * @param bondName the name of the bond.
     * @param ui required for printing.
     */
    public void removeBondFromList(String bondName, Ui ui) {
        for (int i = 0; i < getSize(); i++) {
            if (bondName.equals(bondLists.get(i).getName())) {
                bondLists.remove(i);
                return;
            }
        }
    }

    /**
     * Gets the bond object from the bondList.
     *
     * @param bondName the name of the bond to retrieve.
     * @return the bond object.
     * @throws BondException if the bond does not exist.
     */
    public Bond getBond(String bondName) throws BondException {
        for (int i = 0; i < getSize(); i++) {
            if (bondName.equals(bondLists.get(i).getName())) {
                return bondLists.get(i);
            }
        }
        throw new BondException("There are no bonds with the name: " + bondName);
    }

    /**
     * Edits the bond details specifically.
     *
     * @param bondName the name of the bond to retrieve.
     * @param year the new year of the bond
     * @param rate the new rate of the bond
     * @param ui required for printing.
     * @throws BondException If the bond does not exist or the year is smaller than the original.
     */
    public void editBond(String bondName, String year, String rate, Ui ui) throws BondException {
        for (int i = 0; i < getSize(); i++) {
            if (bondName.equals(bondLists.get(i).getName())) {
                editBondYear(year, i);
                editBondRate(rate, i);
                ui.printMessage(bondLists.get(i).getBondDescription());
                return;
            }
        }
        throw new BondException("There are no bonds with the name: " + bondName);
    }

    /**
     * Edits the bond rate specifically to a new rate.
     *
     * @param rate the new interest rate of the bond.
     * @param i position of the bond in the bondList.
     */
    private void editBondRate(String rate, int i) {
        if (!(rate.isEmpty() || rate.isBlank())) {
            bondLists.get(i).setRate(Double.parseDouble(rate));
        }
    }

    /**
     * Edits the year of the bond.
     *
     * @param year the new year of the bond.
     * @param i position of the bond in the bondList.
     * @throws BondException if the year is smaller than the original year.
     */
    private void editBondYear(String year, int i) throws BondException {
        if (!(year.isEmpty() || year.isBlank())) {
            int originalYear = bondLists.get(i).getYear();
            if (Integer.parseInt(year) < originalYear) {
                throw new BondException("The year can only be larger than: " + originalYear);
            }
            bondLists.get(i).setYear(Integer.parseInt(year));
        }
    }
}
