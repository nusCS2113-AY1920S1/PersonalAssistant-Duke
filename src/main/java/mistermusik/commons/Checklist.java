package mistermusik.commons;

import java.util.ArrayList;

public class Checklist {
    private ArrayList<String> checklist;

    /**
     * Creates a new checklist.
     * (Usually checklists starts from nothing, so no original input required
     */
    public Checklist() {
        this.checklist = new ArrayList<>();
    }

    /**
     * Adding a new item into the checklist.
     *
     * @param newItem String of new item.
     */
    public void addItem(String newItem) {
        this.checklist.add(newItem);
    }

    /**
     * Deleting a specific item.
     *
     * @param itemIndex Index of the item to be removed.
     */
    public void deleteItem(int itemIndex) {
        this.checklist.remove(itemIndex);
    }

    /**
     * Editing an item.
     *
     * @param itemIndex Index of the item.
     * @param newItem New item content.
     */
    public void editItem(int itemIndex, String newItem) {
        this.checklist.set(itemIndex, newItem);
    }

    public ArrayList<String> getChecklist() {
        return checklist;
    }
}
