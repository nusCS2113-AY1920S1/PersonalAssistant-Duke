package inventory;

public class Item {

    private String name;
    private int quantity;

    public Item(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return (this.name + " | " + this.quantity);
    }

    public String toWriteFile() {
        return this.name + " | " + this.quantity;
    }
}
