package rims.resource;

public class Room extends Resource {
    //constructing new Room instance using string data from resource.txt
    public Room(String id, String type, String name, String qty){
        super(id,type,name,qty);
    }
    public Room(int id, String type, String name, int qty){
        super(id, type, name, qty);
    }
    // public Room(String name, int id) {
    // super(name, id);
    // this.type = 'R';
    // }

    // public Room(String name, int id, int loanId, String stringDateFrom, String
    // stringDateTill) throws ParseException {
    // super(name, id, loanId, stringDateFrom, stringDateTill);
    // this.type = 'R';
    // }
}