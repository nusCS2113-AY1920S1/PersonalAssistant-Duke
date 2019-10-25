package rims.resource;

public class Room extends Resource {
    //constructing new Room instance using string data from resource.txt
    public Room(int resourceId, String name){
        super(resourceId, name);
    }
    public Room(int resourceId, String name, ReservationList reservations) {
        super(resourceId, name, reservations);
    }
}