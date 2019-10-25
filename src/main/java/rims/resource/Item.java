package rims.resource;

// have a list of all possible objects / all objects currently in inventory [Frisbee, Basketball]
// return /item frisbee /qty 3 /user id1

public class Item extends Resource {

    public Item(){
        ;
    }
    
    /**
     * Takes in id, type and name to create new resource
     * 
     * @param id
     * @param type
     * @param name
     */
    public Item(String id, String type, String name, ReservationList reservations) {
        super(id, type, name, reservations);
    }

    public Item(int id, String type, String name, ReservationList reservations) {
        super(id, type, name, reservations);
    }
}