package menu;

import duke.task.Item;

import java.util.ArrayList;

/**
 * This class is to add categories for the respective menus
 */

public class CategoryList extends MyMenu {

    private ArrayList<Item> list = new ArrayList<>();

    public void setList(ArrayList<Item> list) {
        this.list = list;
    }

    public ArrayList<Item> getList() {
        return this.list;
    }



    public CategoryList(String subMenuDescription, String menu) {
        super(subMenuDescription, menu);
    }




}
