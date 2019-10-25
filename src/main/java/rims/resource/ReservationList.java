package rims.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Collections;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

import rims.exception.RimsException;

// need a method to cancel reservation

public class ReservationList {
    protected ArrayList<Reservation> reservations;

    public ReservationList() {
        this.reservations = new ArrayList<Reservation>();
    }

    public ArrayList<Reservation> getReservationList() {
        return this.reservations;
    }

    public Reservation getReservationByIndex(int i) {
        return reservations.get(i);
    }

    public Reservation getReservationById(int reservationId) throws RimsException {
        for (int i = 0; i < size(); i++) {
            Reservation thisReservation = getReservationByIndex(i);
            if (thisReservation.getReservationId() == reservationId) {
                return thisReservation;
            }
        }
        throw new RimsException("No such resource ID!");
    }

    public void add(Reservation newReservation) {
        reservations.add(newReservation);
    }

    public void createReservation(int reservationId, int resourceId, int userId, Date startDate, Date endDate) throws ParseException {
        Reservation newReservation = new Reservation(reservationId, resourceId, userId, startDate, endDate);
        add(newReservation);
    }

    public void cancelReservationById(int reservationId) throws RimsException {
        boolean deleted = false;
        for (int i = 0; i < size(); i++) {
            if (reservations.get(i).getReservationId() == reservationId) {
                reservations.remove(i);
                deleted = true;
                break;
            }
        }
        if (!deleted) {
            throw new RimsException("No such reservation ID!");
        }
    }

    public int size() {
        return reservations.size();
    }

    public boolean isEmpty() {
        return reservations.isEmpty();
    }

    public boolean isCurrentlyAvailable() {
        Date currentDate = new Date(System.currentTimeMillis());
        for (int i = 0; i < size(); i++) {
            Reservation thisReservation = getReservationByIndex(i);
            if (currentDate.after(thisReservation.getStartDate()) && currentDate.before(thisReservation.getEndDate())) {
                return false;
            }
        }
        return true;
    }

    public boolean isAvailableFrom(Date startDate, Date endDate) {
        if (reservations.isEmpty()) {
            return true;
        }
        for (int i = 0; i < size(); i++) {
            Reservation thisReservation = getReservationByIndex(i);
            if ((startDate.after(thisReservation.getStartDate()) && startDate.before(thisReservation.getEndDate())) || (endDate.after(thisReservation.getStartDate()) && endDate.before(thisReservation.getEndDate()))) {
                return false;
            }
        }
        return true;
    }

    public Reservation getCurrentBooking() throws RimsException {
        Date currentDate = new Date(System.currentTimeMillis());
        for (int i = 0; i < size(); i++) {
            Reservation thisReservation = getReservationByIndex(i);
            if (currentDate.after(thisReservation.getStartDate()) && currentDate.before(thisReservation.getEndDate())) {
                return thisReservation;
            }
        }
        throw new RimsException("Item is not currently booked!");
    }

    // modify
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

}