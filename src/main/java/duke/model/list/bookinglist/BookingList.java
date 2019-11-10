package duke.model.list.bookinglist;

import duke.model.task.bookingtasks.Booking;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static duke.common.BookingMessages.*;
import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;

/**
 * Creates a list to store booking objects for processing.
 */
public class BookingList {

    private ArrayList<Booking> bookingList;

    /**
     * Constructor for bookinglist object.
     *
     * @param bookingList an array list of bookings.
     */
    public BookingList(ArrayList<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    /**
     * adds a new element to the booking list with parameters specified.
     *
     * @param customerName    Name of the customer who made this booking.
     * @param customerContact Contact number of the customer who made this booking.
     * @param numberOfPax     Number of pax coming.
     * @param bookingDate     Date of the booking.
     * @param orderName       The orders made by this booking.
     * @throws ParseException if input booking date is not parsable.
     */
    public void addBooking(String customerName, String customerContact, String numberOfPax, String bookingDate, String orderName) throws ParseException {
        bookingList.add(new Booking(customerName, customerContact, numberOfPax, bookingDate, orderName));
    }

    /**
     * removes an element from the booking list.
     *
     * @param i the specific index of the element to be removed.
     */
    public void deleteBooking(int i) {
        bookingList.remove(bookingList.get(i));
    }

    /**
     * Generates all elements from the booking list to be displayed.
     *
     * @return an array list consist of the formatted bookings to be displayed.
     */
    public ArrayList<String> listAllBooking() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add("     " + displayIndex + ". " + bookingList.get(i));
        }
        if (arrList.isEmpty()) {
            arrList.add(MESSAGE_NO_EXISTING_BOOKING);
        }
        return arrList;
    }

    /**
     * Filters elements out from the booking list by customer name.
     *
     * @param customerName Name of the customer who matches the booking.
     * @return an array list consist of the filtered bookings.
     */
    public ArrayList<String> findBooking(String customerName) {
        ArrayList<String> arrFind = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            if (bookingList.get(i).getCustomerName().toLowerCase().contains(customerName)) {
                final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
                arrFind.add("     " + displayIndex + ". " + bookingList.get(i).toString());
            }
        }
        if (arrFind.isEmpty()) {
            arrFind.add(MESSAGE_NO_BOOKING_FOR + customerName + MESSAGE_IS_FOUND);
        }
        return arrFind;
    }

    /**
     * Extracts the orders out from booking matched by customer name.
     *
     * @param customerName Name of the customer who matches the booking.
     * @return an array list consist of orders from specific customer.
     */
    public ArrayList<String> viewOrders(String customerName) {
        ArrayList<String> arrOrders = new ArrayList<>();

        for (int i = 0; i < getSize(); i++) {
            if (bookingList.get(i).getCustomerName().toLowerCase().equals(customerName)) {
                String orderOutput = "     " + Arrays.toString(bookingList.get(i).getOrders()) + " on " + bookingList.get(i).getBookingDateFormatted();
                arrOrders.addAll(Collections.singletonList(orderOutput));
            }
        }
        if (arrOrders.isEmpty()) {
            arrOrders.add(MESSAGE_NO_ORDER_FOR + customerName + MESSAGE_IS_FOUND);
        }
        return arrOrders;
    }

    /**
     * Get the number of elements in the booking list.
     *
     * @return size of the list.
     */
    public int getSize() {
        return bookingList.size();
    }

    /**
     * Get the entire booking list.
     *
     * @return booking list.
     */
    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }
}
