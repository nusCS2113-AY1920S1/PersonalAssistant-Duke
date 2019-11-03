package duke.model.list.bookinglist;

import duke.model.task.bookingtasks.Booking;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static duke.common.BookingMessages.*;
import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;


public class BookingList {

    private ArrayList<Booking> bookingList;

    public BookingList(ArrayList<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public void addBooking(String customerName, String customerContact, String numberOfPax, String bookingDate, String orderName) throws ParseException {
        bookingList.add(new Booking(customerName, customerContact, numberOfPax, bookingDate, orderName));
    }

    public void deleteBooking(int i) {
        bookingList.remove(bookingList.get(i));
    }

    public ArrayList<String> listAllBooking() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add("     " + displayIndex + ". " + bookingList.get(i));
        }
        if(arrList.isEmpty()) {
            arrList.add(MESSAGE_NO_EXISTING_BOOKING);
        }
        return arrList;
    }

    public ArrayList<String> findBooking(String customerName) {
        ArrayList<String> arrFind = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            if (bookingList.get(i).getCustomerName().toLowerCase().equals(customerName)) {
                final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
                arrFind.add("     " + displayIndex + ". " + bookingList.get(i).toString());
            }
        }
        if (arrFind.isEmpty()) {
            arrFind.add(MESSAGE_NO_BOOKING_FOR + customerName + MESSAGE_IS_FOUND);
        }
        return arrFind;
    }

    public ArrayList<String> viewOrders(String customerName) {
        ArrayList<String> arrOrders = new ArrayList<>();

        for (int i = 0; i < getSize(); i++) {
            if (bookingList.get(i).getCustomerName().toLowerCase().equals(customerName)) {
                String orderOutput = "     " + Arrays.toString( bookingList.get(i).getOrders()) + " on " + bookingList.get(i).getBookingDateFormatted();
                arrOrders.addAll(Collections.singletonList(orderOutput));
            }
        }
        if (arrOrders.isEmpty()) {
            arrOrders.add(MESSAGE_NO_ORDER_FOR + customerName + MESSAGE_IS_FOUND);
        }
        return arrOrders;
    }

    public int getSize() {
        return bookingList.size();
    }

    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }
}
