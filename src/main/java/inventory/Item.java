package inventory;


import java.util.Comparator;

public class Item {

    private String name;
    private int quantity;
    private String roomcode;

    /**
     * Instantiate a new item object.
     * @param roomcode room code of room
     * @param name item name
     * @param quantity amount of item
     */
    public Item(String roomcode, String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.roomcode = roomcode;
    }

    public String getName() {
        return name;
    }

    public String getRoomcode() {
        return roomcode;
    }

    public int getQuantity(){
        return quantity;
    }

    /**
     * Method that sorts the whole inventory according to Roomcode.
     */
    public static class SortbyRoom implements Comparator<Item> {
        //sort in ascending order of RoomCode
        public int compare(Item item1, Item item2) {
            //return (int) (item1.getRoomcode().compareTo(item2.getRoomcode()));
            return item1.roomcode.compareTo(item2.roomcode);
        }
    }


    @Override
    public String toString() {
        return (this.roomcode + " | " + this.name + " | " + this.quantity);
    }

    public String toWriteFile() {
        return this.roomcode + " | " + this.name + " | " + this.quantity + "\n";
    }
}
