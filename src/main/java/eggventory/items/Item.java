package eggventory.items;

/**
 * An item of unique stock. Has its own index (unique among items of that stock), loan and lost status.
 */

public class Item {
    private int index;
    private boolean loaned;
    private boolean lost;


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
}

