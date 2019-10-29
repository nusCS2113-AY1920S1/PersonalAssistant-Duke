package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.model.task.bookingtasks.Booking;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AddBookingCommand extends Command<BookingList, Ui, BookingStorage> {

    private static String msg = "";

    public AddBookingCommand(String userInput) {
        this.userInput = userInput;
    }

    private static boolean isDateParsable(String bookingDate) {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(bookingDate);
            return true;
        } catch (ParseException e) {
            return false;
        }

    }

    private static boolean isAvailableDate(String bookingDate, BookingList bookingList) {
        for (Booking booking : bookingList.getBookingList()) {
            if (bookingDate.equals(booking.getBookingDate())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] temp = userInput.split("\\s", 6);
        if (userInput.trim().equals("addbooking")) {
            arrayList.add("Booking details cannot be empty!\n" +
                    "       Please enter in the following format:\n" +
                    "       addbooking <customer_name> <customer_contact> <number_of_pax> <booking_date_dd/MM/yyyy> orders/ <order_name_1>, <order_name_2>");
        } else if (userInput.trim().charAt(10) == ' ' && temp.length == 6) {
            String customerName = temp[1].trim();
            String customerContact = temp[2].trim();
            String numberOfPax = temp[3].trim();
            String bookingDate = temp[4].trim();
            String orderName = temp[5].trim();

            if (isDateParsable(bookingDate)) {
                if (isAvailableDate(bookingDate, bookingList)) {
                    if (orderName.contains("orders/")) {
                        bookingList.addBooking(customerName, customerContact, numberOfPax, bookingDate, orderName);
                        bookingStorage.saveFile(bookingList);

                        int size = bookingList.getSize();
                        if (size == 1) {
                            msg = " booking in the list.";
                        } else {
                            msg = " bookings in the list.";
                        }
                        arrayList.add("New booking added:\n" + "       " + bookingList.getBookingList().get(size - 1) + "\n" + "Now you have " + size + msg);
                    } else {
                        arrayList.add("Invalid orders entered.\n Please enter orders again in this format: \n orders/ <order_name_1>, <order_name_2>");
                    }
                } else {
                    arrayList.add("Date entered is unavailable due to existing booking:(\n Please enter another date.");
                }
            } else {
                arrayList.add("Invalid booking date entered.\n Please enter date again in this format: dd/MM/yyyy");
            }
        } else {
            arrayList.add("Incorrect Booking details.\n" +
                    "       Please enter in the following format:\n" +
                    "       addbooking <customer_name> <customer_contact> <number_of_pax> <booking_date_dd/MM/yyyy> orders/ <order_name_1>, <order_name_2>");
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }

}