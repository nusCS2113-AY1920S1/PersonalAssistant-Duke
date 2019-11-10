package rims.resource;

import rims.exception.RimsException;

//@@author rabhijit
/**
 * This class represents an instance of a Room. Contains the name of the Room, its resource ID, type,
 * and a list of Reservation objects representing the reservations made for the Room.
 */
public class Room extends Resource {

    /**
     * Constructor for a newly created Room, with no Reservations made yet.
     * @param resourceId the resourceId generated for the new Room.
     * @param name the name of the new Room.
     */
    public Room(int resourceId, String name) {
        super(resourceId, name);
    }

    /**
     * Constructor for an existing Room that has been loaded from the data file,
     * with existing Reservations in a ReservationList.
     * @param resourceId the resourceId of the Room.
     * @param name the name of the Room.
     * @param reservations the list of Reservations made for the existing Room.
     */
    public Room(int resourceId, String name, ReservationList reservations) {
        super(resourceId, name, reservations);
    }

}