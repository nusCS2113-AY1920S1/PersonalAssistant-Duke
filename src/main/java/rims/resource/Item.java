package rims.resource;

public class Item extends Resource {
    public Item(int resourceId, String name) {
        super(resourceId, name);
    }
    public Item(int resourceId, String name, ReservationList reservations) {
        super(resourceId, name, reservations);
    }
}