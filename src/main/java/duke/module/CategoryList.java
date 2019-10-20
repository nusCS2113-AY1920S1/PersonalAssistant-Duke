package duke.module;

import duke.task.Item;

import java.util.ArrayList;

/**
 * This class is to add categories for the respective menus.
 */

public final class CategoryList extends MyMenu {

    /**
     * Represents a list of categories.
     */
    private ArrayList<Item> list = new ArrayList<>();

    /**
     * Setter function for the list of categories.
     * @param myList The new list of categories to set as.
     */
    public void setList(final ArrayList<Item> myList) {
        this.list = myList;
    }

    /**
     * Getter function to obtain the list of categories.
     * @return A list of categories.
     */
    public ArrayList<Item> getList() {
        return this.list;
    }

    /**
     * Constructor for CategoryList.
     * @param subMenuDescription The description for the sub menu.
     * @param menu The name of the menu
     */
    public CategoryList(final String subMenuDescription, final String menu) {
        super(subMenuDescription, menu);
    }




}
