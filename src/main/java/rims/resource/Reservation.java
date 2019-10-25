package rims.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private int reservationId;
    private int userId;
    private int resourceId;
    private Date dateFrom;
    private Date dateTill;

    /**
     * This builds an reservation from given user input
     * 
     * @param reservationId
     * @param resourceId
     * @param userId
     * @param from
     * @param until
     * @throws ParseException
     */

    // for creating brand new Reservation
    public Reservation(int reservationId, int resourceId, int userId, Date dateFrom, Date dateTill) throws ParseException {
        this.reservationId = reservationId;
        this.resourceId = resourceId;
        this.userId = userId;
        this.dateFrom = dateFrom;
        this.dateTill = dateTill;
    }

    // for loading from Storage
    public Reservation(int reservationId, int resourceId, int userId, String dateFrom, String dateTill) throws ParseException {
        this.reservationId = reservationId;
        this.resourceId = resourceId;
        this.userId = userId;
        this.dateFrom = stringToDate(dateFrom);
        this.dateTill = stringToDate(dateTill);
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getUserId() {
        return userId;
    }

    public Date getStartDate() {
        return dateFrom;
    }

    public Date getEndDate() {
        return dateTill;
    }

    public boolean isStillValid() {
        Date currentDate = new Date(System.currentTimeMillis());
        if (currentDate.after(dateFrom) && currentDate.after(dateTill)) {
            return false;
        }
        return true;
    }

    // to change
    public String toString() {
        return "[" + reservationId + "]" + " borrowed by user: " + userId + " from " + getDateToPrint(dateFrom) + " till " + getDateToPrint(dateTill);
    }

    public String toDataFormat() {
        return reservationId + "," + resourceId + "," + userId + "," + dateToString(dateFrom) + "," + dateToString(dateTill);
    }

    public Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date dateValue = formatter.parse(stringDate);
        return dateValue;
    }

    public String dateToString(Date thisDate) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");
        String stringDate = format.format(thisDate);
        return stringDate;
    }

    public String getDateToPrint(Date date) {
        DateFormat dayFormat = new SimpleDateFormat("d");
        int day = Integer.parseInt(dayFormat.format(date)) % 10;
        String suffix = day == 1 ? "st" : (day == 2 ? "nd" : (day == 3 ? "rd" : "th"));
        String stringDate = (new SimpleDateFormat("EEEEE, ")).format(date) + (dayFormat.format(date)) + suffix + " of " + (new SimpleDateFormat("MMMMM yyyy, hh:mm aaa")).format(date);
        return stringDate;
    }
}