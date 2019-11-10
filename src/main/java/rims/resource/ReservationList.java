package rims.resource;

import rims.exception.RimsException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

//@@author isbobby
/**
 * This class contains all the Reservations made for a particular Resource. This
 * ReservationList is stored as an attribute in its corresponding Resource, for
 * which it stores its reservations. Contains functions to create, delete, get
 * Reservations and to check the availability of its Resource.
 */
public class ReservationList {
    protected ArrayList<Reservation> reservations;

    /**
     * Constructor for a ReservationList.
     */
    public ReservationList() {
        this.reservations = new ArrayList<Reservation>();
    }

    /**
     * Returns the ReservationList itself.
     *
     * @return the array of Reservations.
     */
    public ArrayList<Reservation> getReservationList() {
        return this.reservations;
    }

    /**
     * Returns a Reservation in the Reservation array by its index number in the
     * array.
     *
     * @param indexNo the index number of the desired Reservation.
     * @return the Reservation itself.
     */
    public Reservation getReservationByIndex(int indexNo) {
        return reservations.get(indexNo);
    }

    /**
     * Returns a Reservation in the Reservation array by its reservation ID.
     *
     * @param reservationId the reservation ID of the desired Reservation.
     * @return the Reservation itself.
     * @throws RimsException if no reservation has such an ID.
     */
    public Reservation getReservationById(int reservationId) throws RimsException {
        for (int i = 0; i < size(); i++) {
            Reservation thisReservation = getReservationByIndex(i);
            if (thisReservation.getReservationId() == reservationId) {
                return thisReservation;
            }
        }
        throw new RimsException("Reservation not found for given reservation ID!");
    }

    /**
     * Adds a new Reservation to the ReservationList.
     *
     * @param newReservation the newly created Reservation.
     */
    public void add(Reservation newReservation) {
        reservations.add(newReservation);
    }

    /**
     * Creates a new Reservation object and adds it to the ReservationList, given
     * the parameters of the new Reservation.
     *
     * @param reservationId the newly generated reservation ID of the Reservation to
     *                      be created.
     * @param resourceId    the ID of the Resource for which this Reservation is
     *                      being created.
     * @param userId        the ID of the user creating this Reservation.
     * @param startDate     the date from which this Reservation takes effect.
     * @param endDate       the date till which this Reservation will be in effect.if the dates given are in an invalid format.
     * @throws RimsException  if date is invalid, or if the date of return is before the date of borrowing.
     */
    public void createReservation(int reservationId, int resourceId, int userId, Date startDate, Date endDate)
            throws RimsException {
        Date currentDate = new Date(System.currentTimeMillis());
        if (startDate.after(endDate)) {
            throw new RimsException("Your date of return must be after your date of borrowing!");
        }
        if (currentDate.after(endDate)) {
            throw new RimsException("Your date of return must be a date in the future!");
        }
        Reservation newReservation = new Reservation(reservationId, resourceId, userId, startDate, endDate);
        add(newReservation);
    }

    /**
     * Deletes a Reservation object, making that reservation cancelled and no longer
     * valid.
     *
     * @param reservationId the reservation ID of the reservation to be cancelled.
     * @throws RimsException if no such reservation has that ID.
     */
    public void cancelReservationById(int reservationId) throws RimsException {
        boolean deleted = false;
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId() == reservationId) {
                reservations.remove(i);
                deleted = true;
                break;
            }
        }
        if (!deleted) {
            throw new RimsException("Reservation not found for given reservation ID!");
        }
    }

    // @@author rabhijit
    /**
     * Returns the number of Reservations made for this particular Resource.
     *
     * @return the size of the ReservationList.
     */
    public int size() {
        return reservations.size();
    }

    /**
     * Checks if the ReservationList is empty.
     *
     * @return a boolean: true if no Reservations have been made, false otherwise.
     */
    public boolean isEmpty() {
        return reservations.isEmpty();
    }

    /**
     * Checks if this Resource is currently booked under a Reservation, or is
     * overdue from a previous Reservation.
     *
     * @return a boolean indicating whether this Resource is currently not booked.
     */
    public boolean isCurrentlyAvailable() {
        Date currentDate = new Date(System.currentTimeMillis());
        for (int i = 0; i < size(); i++) {
            Reservation thisReservation = getReservationByIndex(i);
            boolean isCurrentlyBooked = (currentDate.after(thisReservation.getStartDate())
                    && currentDate.before(thisReservation.getEndDate()));
            boolean isOverdue = thisReservation.isOverdue();
            if (isCurrentlyBooked || isOverdue) {
                return false;
            }
        }
        return true;
    }

    // @@author aarushisingh1
    /**
     * Checks if this Resource is currently booked under a Reservation, or is
     * overdue from a previous Reservation on the date entered by the user.
     *
     * @param checkedDate The date entered by the user that is being checked
     * @return a boolean indicating whether this Resource is currently not booked on
     *         that date.
     */
    public boolean isAvailableOnDate(Date checkedDate) {
        for (int i = 0; i < size(); i++) {
            Reservation thisReservation = getReservationByIndex(i);
            boolean isUnavailable = (checkedDate.after(thisReservation.getStartDate())
                    && checkedDate.before(thisReservation.getEndDate()));
            boolean isOverdue = thisReservation.isOverdue();
            if (isUnavailable || isOverdue) {
                return false;
            }
        }
        return true;
    }

    //@@author rabhijit
    /**
     * Checks if this Resource is booked between two given dates, or currently
     * overdue from a previous Reservation.
     *
     * @param startDate the date from which this Resource is being queried.
     * @param endDate   the date till which this Resource is being queried.
     * @return a boolean indicating whether this Resource has been booked at any
     *         point between those two dates.
     */
    public boolean isAvailableFrom(Date startDate, Date endDate) {
        if (reservations.isEmpty()) {
            return true;
        }
        for (int i = 0; i < size(); i++) {
            Reservation thisReservation = getReservationByIndex(i);
            boolean startDateIsBetween = (startDate.after(thisReservation.getStartDate()) && startDate.before(thisReservation.getEndDate()));
            boolean endDateIsBetween = (endDate.after(thisReservation.getStartDate()) && endDate.before(thisReservation.getEndDate()));
            boolean isBetweenDates = (startDateIsBetween || endDateIsBetween);
            boolean isOverdue = thisReservation.isOverdue();
            boolean startDatesAlign = startDate.equals(thisReservation.getStartDate());
            boolean endDatesAlign = endDate.equals(thisReservation.getEndDate());
            if (isBetweenDates || isOverdue || startDatesAlign || endDatesAlign) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the current Reservation object under which this Resource is currently
     * loaned out, or still overdue.
     *
     * @return the Reservation object under which this Resource is currently booked.
     * @throws RimsException if this Resource is not currently booked.
     */
    public Reservation getCurrentBooking() throws RimsException {
        Date currentDate = new Date(System.currentTimeMillis());
        for (int i = 0; i < size(); i++) {
            Reservation thisReservation = getReservationByIndex(i);
            boolean isCurrentlyBooked = (currentDate.after(thisReservation.getStartDate()) && currentDate.before(thisReservation.getEndDate()));
            boolean isOverdue = thisReservation.isOverdue();
            if (isCurrentlyBooked || isOverdue) {
                return thisReservation;
            }
        }
        throw new RimsException("Item is not currently booked!");
    }

    // @@author isbobby
    /**
     * Gets the list of Reservations that a certain user has made for this
     * particular Resource.
     *
     * @param userId the ID of the user whose Reservations for this Resource are
     *               being obtained.
     * @return a list containing the Reservations made by the user for this object.
     */
    public ReservationList getUserReservations(int userId) {
        ReservationList userReservations = new ReservationList();
        for (int i = 0; i < size(); i++) {
            Reservation thisReservation = getReservationByIndex(i);
            if (thisReservation.getUserId() == userId) {
                userReservations.add(thisReservation);
            }
        }
        return userReservations;
    }

    // @@author rabhijit
    /**
     * Returns the list of currently active Reservations, including overdue
     * Reservations, which are expiring in a given number of days.
     *
     * @param daysDue the number of days within which Reservations which are
     *                expiring should be returned.
     * @return a list of all Reservations that have expired, or are expiring within
     *         the given number of days.
     */
    public ReservationList getDueReservations(int daysDue) {
        ReservationList reservationsDueSoon = new ReservationList();
        Date currentDate = new Date(System.currentTimeMillis());
        for (int i = 0; i < size(); i++) {
            Reservation thisReservation = getReservationByIndex(i);
            if (thisReservation.isDueInDays(daysDue) && currentDate.after(thisReservation.getStartDate())) {
                reservationsDueSoon.add(thisReservation);
            }
        }
        return reservationsDueSoon;
    }

}