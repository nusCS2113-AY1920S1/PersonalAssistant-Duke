package eggventory.model.items;

//@@author cyanoei-unused

//Unused because UniqueStock has been pushed back to v2.0.

/**
 * An item of unique stock. Has its own index (unique among items of that stock), loan and lost status.
 */

public class Item {
    private int index;
    private boolean loaned;
    private boolean lost;

    /**
     * Item constructor. By default the item is neither on loan nor lost.
     * @param index The unique index of the item.
     */
    public Item(int index) {
        this.index = index;
        loaned = false;
        lost = false;
    }

    /**
     * Gets the unique index of the item.
     * @return The index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns true if the item is on loan, false if it is not.
     * @return The loan status.
     */
    public boolean isLoaned() {
        return loaned;
    }

    /**
     * Updates the loan status of the item.
     * @param loaned Whether the item is on loan.
     */
    public void setLoaned(boolean loaned) {
        this.loaned = loaned;
    }

    /**
     * Returns true if the item is lost, false if it is not.
     * @return The lost status.
     */
    public boolean isLost() {
        return lost;
    }

    /**
     * Updates the lost status of the item.
     * @param lost Whether the item is lost.
     */
    public void setLost(boolean lost) {
        this.lost = lost;
    }

    /**
     * Returns the status of the item if on loan.
     * @return a string (for printing) indicating if it is on loan.
     */
    private String loanedString() {
        if (isLoaned()) {
            return "on loan";
        } else {
            return "-";
        }
    }

    /**
     * Returns the status of the item if lost.
     * @return a string (for printing) indicating if it is lost.
     */
    private String lostString() {
        if (isLost()) {
            return "lost";
        } else {
            return "-";
        }
    }

    /**
     * Returns the status of the item if on loan.
     * @return a string (for printing) indicating if it is on loan.
     */
    private String loanedSaveString() {
        if (isLoaned()) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * Returns the status of the item if lost.
     * @return a string (for printing) indicating if it is lost.
     */
    private String lostSaveString() {
        if (isLost()) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * Formats item details appropriately for Cli output. (Only for output of a single UniqueStock.)
     * @return the item details string.
     */
    @Override
    public String toString() {
        return index + " | " + loanedString() + " | " + lostString();
    }

    /**
     * Formats all item details appropriately to be saved to file. Less detailed than the printing string.
     * @return the string to save.
     */
    public String saveDetailsString() {
        return index + " | " + loanedSaveString() + " | " + lostSaveString();
    }
}

