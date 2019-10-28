package duke.model.list.bookinglist;

import duke.exception.DukeException;
import duke.model.task.bookingtasks.Booking;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;
import static duke.common.Messages.ERROR_MESSAGE_NOTFOUND;


public class BookingList {

    private ArrayList<Booking> bookingList;
    private static String msg = "";

    public BookingList() {
        this.bookingList = new ArrayList<Booking>();
    }

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
            arrList.add("      Sorry, no existing booking.");
        }
        return arrList;
    }

    public ArrayList<String> findBooking(String customerName) {
        ArrayList<String> arrFind = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            if (bookingList.get(i).getCustomerName().toLowerCase().equals(customerName)) {
                arrFind.add("      " + bookingList.get(i).toString());
            }
        }
        if (arrFind.isEmpty()) {
            arrFind.add("      Sorry, no booking for " + customerName + " is found.");
        }
        return arrFind;
    }

    public ArrayList<String> viewOrders(String customerName) {
        ArrayList<String> arrOrders = new ArrayList<>();

        for (int i = 0; i < getSize(); i++) {
            if (bookingList.get(i).getCustomerName().toLowerCase().equals(customerName)) {
                arrOrders.addAll(Arrays.asList(bookingList.get(i).getOrders()));
            }
        }
        if (arrOrders.isEmpty()) {
            arrOrders.add("      Sorry, no order for " + customerName + " is found.");
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
