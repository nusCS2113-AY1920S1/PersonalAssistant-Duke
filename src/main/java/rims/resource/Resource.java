package rims.resource;

import rims.core.Ui;

public abstract class Resource {
    // resource specific attributes
    protected String name;
    protected int resource_id;
    protected String type;
    protected ReservationList reservations;
    // utility classes
    protected Ui ui;

    public Resource() {
        ;
    }

    /**
     * Takes in id, type and name to create new resource. No reservation is created
     * for a newly added resource
     * 
     * @param id   integer
     * @param type string
     * @param name string
     * 
     */
    public Resource(int resource_id, String type, String name) {
        this.resource_id = resource_id;
        this.type = type;
        this.name = name;
    }

    /**
     * Takes in id, type and resource name as string tokens from the text file, and
     * its reservation (if any) into the resourceList. The resource_id is casted to
     * integer in this constructor
     * 
     * @param resource_id  string
     * @param type         string
     * @param name         string
     * @param reservations string
     */
    public Resource(String resource_id, String type, String name, ReservationList reservations) {
        this.resource_id = Integer.parseInt(resource_id);
        this.type = type;
        this.name = name;
        this.reservations = reservations;
    }

    /**
     * Takes in id, type and resource name as string tokens from the text file, and
     * its reservation (if any) into the resourceList.
     * 
     * @param resource_id  integer
     * @param type         string
     * @param name         string
     * @param reservations string
     */
    public Resource(int resource_id, String type, String name, ReservationList reservations) {
        this.resource_id = resource_id;
        this.type = type;
        this.name = name;
        this.reservations = reservations;
    }

    /**
     * This method formats the resource information into a easy-to-read string
     * 
     * @return string
     */
    public String toString() {
        String s;
        s = "ID: " + resource_id + " [" + type + "]" + name;
        return s;
    }

    /**
     * This method formats the resource information into a string suitable to store
     * in text file Different attributes are separated by ',' The attributes being
     * stored are: id, type, name
     * 
     * @return string
     */
    public String toDataString() {
        String s;
        s = resource_id + "," + type + "," + name;
        return s;
    }

    /**
     * Get method
     * 
     * @return resource_id
     */
    public int getResourceId() {
        return this.resource_id;
    }

    /**
     * Get method
     * 
     * @return resource name
     */
    public String getResourceName() {
        return this.name;
    }

    /**
     * Get method
     * 
     * @return type name
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get method
     * 
     * @return Reservationlist that belongs the this object
     */
    public ReservationList getReservations() {
        return this.reservations;
    }

    /**
     * Remove the single reservation by a reservation id. The exception catching is
     * not yet done.
     * 
     * @exception invalidReservationId A reservation cannot be found under using
     *                                 this reservation id
     * @param reservation_id
     */
    public void removeReservationByReservationId(int reservation_id) {
        int index_to_remove = -1;
        for (int i = 0; i < this.reservations.size(); i++) {
            Reservation thisReservation = reservations.getReservationByIndex(i);
            if (thisReservation.getReservationId() == reservation_id) {
                index_to_remove = i;
            }
        }

        if (index_to_remove != -1) {
            reservations.deleteReservationByReservationID(index_to_remove);
        }
    }
}