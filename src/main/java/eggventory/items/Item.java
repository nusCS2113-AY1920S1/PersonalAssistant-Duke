package eggventory.items;

public class Item {
    private int index;
    private boolean loaned;
    private boolean lost;


    public Item(int index) {
        this.index = index;
        loaned = false;
        lost = false;
    }
}

