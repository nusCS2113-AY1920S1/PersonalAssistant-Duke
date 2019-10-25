package rims.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public abstract class Resource {
    protected String name;
    protected int resourceId;
    protected String type;
    protected ReservationList reservations;

    // for newly created Resource with no Reservations yet
    public Resource(int resourceId, String name) {
        this.name = name;
        this.resourceId = resourceId;
        this.reservations = new ReservationList();
    }

    // for creating existing Resource from data file
    public Resource(int resourceId, String name, ReservationList reservations) {
        this.name = name;
        this.resourceId = resourceId;
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "[" + getType() + "] " + getName();
    }

    public String toDataFormat() {
        return resourceId + "," + type + "," + name;
    }

    public String getName() {
        return name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getType() {
        if (getClass().getSimpleName().equals("Item")) {
            return "I";
        }
        return "R";
    }

    public ReservationList getReservations() {
        return reservations;
    }

    public boolean isCurrentlyAvailable() {
        return reservations.isCurrentlyAvailable();
    }

    public boolean isAvailableFrom(Date dateFrom, Date dateTill) {
        return reservations.isAvailableFrom(dateFrom, dateTill);
    }

    public void book(int reservationId, int userId, Date startDate, Date endDate) throws ParseException {
        reservations.createReservation(reservationId, resourceId, userId, startDate, endDate);
    }

    public ReservationList getUserReservations(int userId) {
        return reservations.getUserReservations(userId);
    }
}