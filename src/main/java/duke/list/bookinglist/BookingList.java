package duke.list.bookinglist;

import duke.exception.DukeException;
import duke.task.bookingtasks.Booking;

import java.text.ParseException;
import java.util.ArrayList;

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
        return arrList;
    }

    public ArrayList<String> findBooking(String customerName) throws DukeException {
        ArrayList<String> arrFind = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            if (bookingList.get(i).getCustomerName().toLowerCase().contains(customerName)) {
                arrFind.add("      " + bookingList.get(i).toString());
            }
        }
        if (arrFind.isEmpty()) {
            throw new DukeException(ERROR_MESSAGE_NOTFOUND);
        } else {
            return arrFind;
        }
    }

    public int getSize() {
        return bookingList.size();
    }

    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }
}
