package rims.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private int reservation_id;
    private int user_id;
    private int resource_id;
    private Date date_from;
    private Date date_until;

    /**
     * This builds an reservation from given user input
     * 
     * @param reservation_id
     * @param resource_id
     * @param user_id
     * @param from
     * @param until
     * @throws ParseException
     */
    public Reservation(int reservation_id, int resource_id, int user_id, String from, String until) throws ParseException {
        this.reservation_id = reservation_id;
        this.resource_id = resource_id;
        this.user_id = user_id;
        this.date_from = stringToDate(from);
        this.date_until = stringToDate(until);
    }

    /**
     * This overloaded constructor is built for data retrieval, where all data are
     * in the form of strings. The string parameters are later type castedd into
     * suitable data types like integer.
     */
    public Reservation(String reservation_id, String resource_id, String user_id, String from, String until)
            throws ParseException {
        this.reservation_id = Integer.parseInt(reservation_id);
        this.resource_id = Integer.parseInt(resource_id);
        this.user_id = Integer.parseInt(user_id);
        this.date_from = stringToDate(from);
        this.date_until = stringToDate(until);
    }

    /**
     * get method to return the user id who made this reservation
     * 
     * @return this.user_id
     */
    public int getUid() {
        return this.user_id;
    }

    /**
     * get method to return the user id who made this reservation
     * 
     * @return this.user_id
     */
    public int getReservationId() {
        return this.reservation_id;
    }

    /**
     * get method to return the user id who made this reservation
     * 
     * @return this.user_id
     */
    public Date getStartDate() {
        return this.date_from;
    }

    /**
     * get method to return the user id who made this reservation
     * 
     * @return this.user_id
     */
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
        s = "Reservation ID[" + reservation_id + "]" + " Borrowed By User: " + user_id + "\n" + "\n\tFrom " + date_from
                + " to " + date_until + '\n';
        return s;
    }

    /**
     * This toString method formats the string output slightly differently for
     * better UI.
     * 
     * @return String
     */
    public String toString(String input) {
        String s = "";
        if (input.equals("return")) {
            s = "Resource ID[" + resource_id + "] From " + date_from + " to " + date_until + '\n';
        }
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
        s = reservation_id + "," + resource_id + "," + user_id + "," + dateToString(date_from) + ","
                + dateToString(date_until);
        return s;
    }

    /**
     * This method casts strings to date, used for data retreival from txt file.
     */
    public Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date dateValue = formatter.parse(stringDate);
        return dateValue;
    }

    /**
     * This method casts dates to string
     */
    public String dateToString(Date thisDate) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");
        String stringDate = format.format(thisDate);
        return stringDate;
    }
}