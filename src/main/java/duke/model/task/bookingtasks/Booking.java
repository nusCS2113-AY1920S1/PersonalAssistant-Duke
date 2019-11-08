package duke.model.task.bookingtasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Booking class use to create booking object.
 */
public class Booking {
    protected String customerName;
    protected String customerContact;
    protected String numberOfPax;
    protected String bookingDate;
    protected Date date;
    protected SimpleDateFormat dateFormatter;
    protected String orderName;

    /**
     * Constructor for booking object.
     * Initialises booking object with specific details in string.
     *
     * @param customerName    Name of the customer who made this booking.
     * @param customerContact Contact number of the customer who made this booking.
     * @param numberOfPax     Number of pax coming.
     * @param bookingDate     Date of the booking.
     * @param orderName       The orders made by this booking.
     * @throws ParseException if input booking date is not parsable.
     */
    public Booking(String customerName, String customerContact, String numberOfPax, String bookingDate, String orderName) throws ParseException {
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.numberOfPax = numberOfPax;
        this.bookingDate = bookingDate;
        date = new SimpleDateFormat("dd/MM/yyyy").parse(bookingDate);
        dateFormatter = new SimpleDateFormat("d MMMM yyyy");
        this.orderName = orderName;

    }

    /**
     * Converts user input command to a standardized format to store in file.
     *
     * @return String containing the standardized format of a booking.
     */
    public String toSaveString() {
        return "booking" + " | " + customerName + " | " + customerContact + " | " + numberOfPax + " | " + bookingDate + " | " + orderName;
    }

    /**
     * Converts user input command to a standardized format in bookinglist.
     *
     * @return String containing the standardized format of a booking.
     */
    public String toString() {
        return "[Customer name: " + customerName + "] " + "[Contact No.: " + customerContact + "] "
                + "[No. of pax: " + numberOfPax + "] " + "[Booking on: " + dateFormatter.format(date) + "] "
                + "[Orders: " + orderName + "]";
    }

    /**
     * Get the customer name of the booking.
     *
     * @return a string containing name of customer.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Get the orders of the booking
     *
     * @return an array of string containing the orders for the booking.
     */
    public String[] getOrders() {
        String[] orders = orderName.split(",");
        return orders;
    }

    /**
     * Get the date of the booking.
     *
     * @return a string containing the booking date.
     */
    public String getBookingDate() {
        return bookingDate;
    }

    /**
     * Get the standardized date of the booking.
     *
     * @return a date object which represents the booking date.
     */
    public Date getDateTime() {
        return date;
    }

    /**
     * Get the formatted date of the booking.
     *
     * @return a date object which represents the formatted booking date.
     */
    public String getBookingDateFormatted() {
        return dateFormatter.format(date);
    }

}
