package rims.resource;

import rims.exception.RimsException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.Comparator;
import java.util.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

//@@author rabhijit
/**
 * This class represents an instance of a Resource. Contains the name of the Resource, its resource ID, type,
 * and a list of Reservation objects representing the reservations made for the Resource.
 */
public abstract class Resource {
    protected String name;
    protected int resourceId;
    protected String type;
    protected ReservationList reservations;

    /**
     * Constructor for a newly created Resource, with no Reservations made yet.
     * @param resourceId the resourceId generated for the new Resource.
     * @param name the name of the new Resource.
     */
    public Resource(int resourceId, String name) {
        this.name = name;
        this.resourceId = resourceId;
        this.reservations = new ReservationList();
    }

    /**
     * Constructor for an existing Resource that has been loaded from the data file,
     * with existing Reservations in a ReservationList.
     * @param resourceId the resourceId of the Resource.
     * @param name the name of the Resource.
     * @param reservations the list of Reservations made for the existing Resource.
     */
    public Resource(int resourceId, String name, ReservationList reservations) {
        this.name = name;
        this.resourceId = resourceId;
        this.reservations = reservations;
    }

    /**
     * Converts the stored Resource to a readable String for output to the CLI.
     * @return a String version of the attributes of the Resource.
     */
    @Override
    public String toString() {
        return "[" + getType() + "] " + getName();
    }

    /**
     * Converts the stored Resource into a compact String to be stored in a data file.
     * @return a compact String version of the attributes of the Resource.
     */
    public String toDataFormat() {
        return resourceId + "," + getType() + "," + name;
    }

    /**
     * Returns the name of the Resource.
     * @return the name of the Resource.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the resource ID of the Resource.
     * @return the resource ID of the Resource.
     */
    public int getResourceId() {
        return resourceId;
    }

    /**
     * Returns the type (Item or Room) of the Resource.
     * @return the type (Item or Room) of the Resource.
     */
    public String getType() {
        if (getClass().getSimpleName().equals("Item")) {
            return "I";
        }
        return "R";
    }

    /**
     * Returns the list of Reservations made for this Resource.
     * @return the list of Reservations made for this Resource.
     */
    public ReservationList getReservations() {
        return reservations;
    }

    /**
     * Checks if this Resource is currently available for booking.
     * @return a boolean indicating whether this Resource is currently not booked.
     */
    public boolean isCurrentlyAvailable() {
        return reservations.isCurrentlyAvailable();
    }

    //@@author aarushisingh1
    /**
     * Checks if this Resource is currently available for booking on a certain date.
     * @return a boolean indicating whether this Resource is currently not booked.
     */
    public boolean isAvailableOnDate(Date checkedDate) {
        return reservations.isAvailableOnDate(checkedDate);
    }

    /**
     * Checks if this Resource is free to be booked between two dates.
     * @param dateFrom the date from which this Resource is to be booked.
     * @param dateTill the date till which this Resource is to be booked.
     * @return a boolean indicating whether this Resource is free to be booked between the two given dates.
     */
    public boolean isAvailableFrom(Date dateFrom, Date dateTill) {
        return reservations.isAvailableFrom(dateFrom, dateTill);
    }

    /**
     * Creates a new Reservation object for this Resource between two given dates, indicating
     * that this Resource has been booked between those two dates.
     * @param reservationId the newly generated reservation ID for the new Reservation object.
     * @param userId the ID of the user who made the reservation.
     * @param startDate the date from which this Resource has been booked.
     * @param endDate the date till which this Resource has been booked.
     * @throws ParseException if the dates are in an invalid format.
     * @throws RimsException if the date of return is before the date of borrowing.
     */
    public void book(int reservationId, int userId, Date startDate, Date endDate) throws RimsException {
        reservations.createReservation(reservationId, resourceId, userId, startDate, endDate);
    }

    /**
     * Gets the list of Reservations that a certain user has made for this particular Resource.
     * @param userId the ID of the user whose Reservations for this Resource are being obtained.
     * @return a list containing the Reservations made by the user for this object.
     */
    public ReservationList getUserReservations(int userId) {
        return reservations.getUserReservations(userId);
    }

    /**
     * Returns the list of currently active Reservations, including overdue Reservations, which are expiring
     * in a given number of days.
     * @param daysDue the number of days within which Reservations which are expiring should be returned.
     * @return a list of all Reservations that have expired, or are expiring within the given number of days.
     */
    public ReservationList getDueReservations(int daysDue) {
        return reservations.getDueReservations(daysDue);
    }

}