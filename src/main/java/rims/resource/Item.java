package rims.resource;

// have a list of all possible objects / all objects currently in inventory [Frisbee, Basketball]
// return /item frisbee /qty 3 /user id1

public class Item extends Resource {
    // Constructing new item class from resource.txt file
    public Item(String id, String type, String name, String qty) {
        super(id, type, name, qty);
    }

    public Item(int id, String type, String name, int qty){
        super(id, type, name, qty);
    }
    // public Item(String name, int id) {
    // super(name, id);
    // this.type = 'I';
    // }

    // public Item(String name, int id, int loanId, String stringDateFrom, String
    // stringDateTill) throws ParseException {
    // super(name, id, loanId, stringDateFrom, stringDateTill);
    // this.type = 'I';
    // }
}