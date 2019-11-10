package rims.resource;

import rims.exception.RimsException;

//@@author rabhijit
/**
 * This class represents an instance of an Item. Contains the name of the Item, its resource ID, type,
 * and a list of Reservation objects representing the reservations made for the Item.
 */
public class Item extends Resource {
    /**
     * Constructor for a newly created Item, with no Reservations made yet.
     * @param resourceId the resourceId generated for the new Item.
     * @param name the name of the new Item.
     */
    public Item(int resourceId, String name) {
        super(resourceId, name);
    }

    /**
     * Constructor for an existing Item that has been loaded from the data file,
     * with existing Reservations in a ReservationList.
     * @param resourceId the resourceId of the Item.
     * @param name the name of the Item.
     * @param reservations the list of Reservations made for the existing Item.
     */
    public Item(int resourceId, String name, ReservationList reservations) {
        super(resourceId, name, reservations);
    }

}