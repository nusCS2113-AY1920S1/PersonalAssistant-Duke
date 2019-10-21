package rims.reserve;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private int reservation_id;
    private int resource_id;
    private int user_id;
    private int qty;
    private Date date_from;
    private Date date_until;

    // For internal use
    public Reservation(int reservation_id, int resource_id, int uid, int qty, String from, String until)
            throws ParseException {
        this.reservation_id = reservation_id;
        this.resource_id = resource_id;
        this.user_id = uid;
        this.qty = qty;
        this.date_from = stringToDate(from);
        this.date_until = stringToDate(until);
    }

    // For storage purposes
    public Reservation(String reservation_id, String resource_id, String uid, String qty, String from, String until)
            throws ParseException {
        this.reservation_id = Integer.parseInt(reservation_id);
        this.resource_id = Integer.parseInt(resource_id);
        this.user_id = Integer.parseInt(uid);
        this.qty = Integer.parseInt(qty);
        this.date_from = stringToDate(from);
        this.date_until = stringToDate(until);
    }

    /**
     * This section contains all methods containing READ operations
     */
    public int getUid() {
        return this.user_id;
    }

    public int getResourceId() {
        return this.resource_id;
    }

    public int getReservationId() {
        return this.reservation_id;
    }

    public int getQty() {
        return this.qty;
    }

    public Date getStartDate() {
        return this.date_from;
    }

    public Date getEndDate() {
        return this.date_until;
    }

    /**
     * This method returns the current reserve detail, formatted in a user friendly
     * string
     * 
     * @return String
     */
    public String toString() {
        String s;
        s = "Reservation ID[" + reservation_id + "]\n" + " \tResource id: " + resource_id + " (Quantity: " + qty + ") "
                + " User: " + user_id + "\n\tFrom " + date_from + " to " + date_until + '\n';
        return s;
    }

    /**
     * This method returns the current reserve detail, formatted in a string usable
     * by storage class
     * 
     * @return String
     */
    public String toDataString() {
        String s;
        s = reservation_id + "," + resource_id + "," + user_id + "," + qty + "," + dateToString(date_from) + ","
                + dateToString(date_until);
        return s;
    }

    /**
     * This section contains all utiliy methods
     */
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
}