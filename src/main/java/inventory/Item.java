package inventory;

public class Item {

    private String name;
    private int quantity;

    Item(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return (this.name + " | " + this.quantity);
    }
}
